package de.codecentric.nbyl.confy.web.rest;

import de.codecentric.nbyl.confy.ConfyApplication;
import de.codecentric.nbyl.confy.domain.Event;
import de.codecentric.nbyl.confy.domain.Speaker;
import de.codecentric.nbyl.confy.domain.Talk;
import de.codecentric.nbyl.confy.repository.TalkRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static de.codecentric.nbyl.confy.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TalkResource REST controller.
 *
 * @see TalkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfyApplication.class)
public class TalkResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private TalkRepository talkRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTalkMockMvc;

    private Talk talk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TalkResource talkResource = new TalkResource();
        ReflectionTestUtils.setField(talkResource, "talkRepository", talkRepository);
        this.restTalkMockMvc = MockMvcBuilders.standaloneSetup(talkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Talk createEntity(EntityManager em) {
        Talk talk = new Talk()
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION)
                .startTime(DEFAULT_START_TIME);
        // Add required entity
        Event heldAt = EventResourceIntTest.createEntity(em);
        em.persist(heldAt);
        em.flush();
        talk.setHeldAt(heldAt);
        // Add required entity
        Speaker speakers = SpeakerResourceIntTest.createEntity(em);
        em.persist(speakers);
        em.flush();
        talk.getSpeakers().add(speakers);
        return talk;
    }

    @Before
    public void initTest() {
        talk = createEntity(em);
    }

    @Test
    @Transactional
    public void createTalk() throws Exception {
        int databaseSizeBeforeCreate = talkRepository.findAll().size();

        // Create the Talk

        restTalkMockMvc.perform(post("/api/talks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talk)))
            .andExpect(status().isCreated());

        // Validate the Talk in the database
        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeCreate + 1);
        Talk testTalk = talkList.get(talkList.size() - 1);
        assertThat(testTalk.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTalk.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTalk.getStartTime()).isEqualTo(DEFAULT_START_TIME);
    }

    @Test
    @Transactional
    public void createTalkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = talkRepository.findAll().size();

        // Create the Talk with an existing ID
        Talk existingTalk = new Talk();
        existingTalk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTalkMockMvc.perform(post("/api/talks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTalk)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = talkRepository.findAll().size();
        // set the field null
        talk.setTitle(null);

        // Create the Talk, which fails.

        restTalkMockMvc.perform(post("/api/talks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talk)))
            .andExpect(status().isBadRequest());

        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTalks() throws Exception {
        // Initialize the database
        talkRepository.saveAndFlush(talk);

        // Get all the talkList
        restTalkMockMvc.perform(get("/api/talks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(talk.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))));
    }

    @Test
    @Transactional
    public void getTalk() throws Exception {
        // Initialize the database
        talkRepository.saveAndFlush(talk);

        // Get the talk
        restTalkMockMvc.perform(get("/api/talks/{id}", talk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(talk.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingTalk() throws Exception {
        // Get the talk
        restTalkMockMvc.perform(get("/api/talks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTalk() throws Exception {
        // Initialize the database
        talkRepository.saveAndFlush(talk);
        int databaseSizeBeforeUpdate = talkRepository.findAll().size();

        // Update the talk
        Talk updatedTalk = talkRepository.findOne(talk.getId());
        updatedTalk
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION)
                .startTime(UPDATED_START_TIME);

        restTalkMockMvc.perform(put("/api/talks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTalk)))
            .andExpect(status().isOk());

        // Validate the Talk in the database
        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeUpdate);
        Talk testTalk = talkList.get(talkList.size() - 1);
        assertThat(testTalk.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTalk.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTalk.getStartTime()).isEqualTo(UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTalk() throws Exception {
        int databaseSizeBeforeUpdate = talkRepository.findAll().size();

        // Create the Talk

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTalkMockMvc.perform(put("/api/talks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(talk)))
            .andExpect(status().isCreated());

        // Validate the Talk in the database
        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTalk() throws Exception {
        // Initialize the database
        talkRepository.saveAndFlush(talk);
        int databaseSizeBeforeDelete = talkRepository.findAll().size();

        // Get the talk
        restTalkMockMvc.perform(delete("/api/talks/{id}", talk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Talk> talkList = talkRepository.findAll();
        assertThat(talkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
