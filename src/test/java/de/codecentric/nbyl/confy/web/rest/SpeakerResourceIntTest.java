package de.codecentric.nbyl.confy.web.rest;

import de.codecentric.nbyl.confy.ConfyApplication;
import de.codecentric.nbyl.confy.domain.Speaker;
import de.codecentric.nbyl.confy.repository.SpeakerRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SpeakerResource REST controller.
 *
 * @see SpeakerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfyApplication.class)
public class SpeakerResourceIntTest {

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    @Inject
    private SpeakerRepository speakerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSpeakerMockMvc;

    private Speaker speaker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpeakerResource speakerResource = new SpeakerResource();
        ReflectionTestUtils.setField(speakerResource, "speakerRepository", speakerRepository);
        this.restSpeakerMockMvc = MockMvcBuilders.standaloneSetup(speakerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speaker createEntity(EntityManager em) {
        Speaker speaker = new Speaker()
                .surname(DEFAULT_SURNAME)
                .firstName(DEFAULT_FIRST_NAME);
        return speaker;
    }

    @Before
    public void initTest() {
        speaker = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpeaker() throws Exception {
        int databaseSizeBeforeCreate = speakerRepository.findAll().size();

        // Create the Speaker

        restSpeakerMockMvc.perform(post("/api/speakers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speaker)))
            .andExpect(status().isCreated());

        // Validate the Speaker in the database
        List<Speaker> speakerList = speakerRepository.findAll();
        assertThat(speakerList).hasSize(databaseSizeBeforeCreate + 1);
        Speaker testSpeaker = speakerList.get(speakerList.size() - 1);
        assertThat(testSpeaker.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testSpeaker.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
    }

    @Test
    @Transactional
    public void createSpeakerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = speakerRepository.findAll().size();

        // Create the Speaker with an existing ID
        Speaker existingSpeaker = new Speaker();
        existingSpeaker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeakerMockMvc.perform(post("/api/speakers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSpeaker)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Speaker> speakerList = speakerRepository.findAll();
        assertThat(speakerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = speakerRepository.findAll().size();
        // set the field null
        speaker.setSurname(null);

        // Create the Speaker, which fails.

        restSpeakerMockMvc.perform(post("/api/speakers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speaker)))
            .andExpect(status().isBadRequest());

        List<Speaker> speakerList = speakerRepository.findAll();
        assertThat(speakerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpeakers() throws Exception {
        // Initialize the database
        speakerRepository.saveAndFlush(speaker);

        // Get all the speakerList
        restSpeakerMockMvc.perform(get("/api/speakers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speaker.getId().intValue())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSpeaker() throws Exception {
        // Initialize the database
        speakerRepository.saveAndFlush(speaker);

        // Get the speaker
        restSpeakerMockMvc.perform(get("/api/speakers/{id}", speaker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(speaker.getId().intValue()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSpeaker() throws Exception {
        // Get the speaker
        restSpeakerMockMvc.perform(get("/api/speakers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpeaker() throws Exception {
        // Initialize the database
        speakerRepository.saveAndFlush(speaker);
        int databaseSizeBeforeUpdate = speakerRepository.findAll().size();

        // Update the speaker
        Speaker updatedSpeaker = speakerRepository.findOne(speaker.getId());
        updatedSpeaker
                .surname(UPDATED_SURNAME)
                .firstName(UPDATED_FIRST_NAME);

        restSpeakerMockMvc.perform(put("/api/speakers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpeaker)))
            .andExpect(status().isOk());

        // Validate the Speaker in the database
        List<Speaker> speakerList = speakerRepository.findAll();
        assertThat(speakerList).hasSize(databaseSizeBeforeUpdate);
        Speaker testSpeaker = speakerList.get(speakerList.size() - 1);
        assertThat(testSpeaker.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testSpeaker.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSpeaker() throws Exception {
        int databaseSizeBeforeUpdate = speakerRepository.findAll().size();

        // Create the Speaker

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSpeakerMockMvc.perform(put("/api/speakers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speaker)))
            .andExpect(status().isCreated());

        // Validate the Speaker in the database
        List<Speaker> speakerList = speakerRepository.findAll();
        assertThat(speakerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSpeaker() throws Exception {
        // Initialize the database
        speakerRepository.saveAndFlush(speaker);
        int databaseSizeBeforeDelete = speakerRepository.findAll().size();

        // Get the speaker
        restSpeakerMockMvc.perform(delete("/api/speakers/{id}", speaker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Speaker> speakerList = speakerRepository.findAll();
        assertThat(speakerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
