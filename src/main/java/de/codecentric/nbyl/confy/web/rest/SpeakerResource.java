package de.codecentric.nbyl.confy.web.rest;

import de.codecentric.nbyl.confy.domain.Speaker;
import de.codecentric.nbyl.confy.repository.SpeakerRepository;
import de.codecentric.nbyl.confy.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Speaker.
 */
@RestController
@RequestMapping("/api")
public class SpeakerResource {

    private final Logger log = LoggerFactory.getLogger(SpeakerResource.class);

    @Inject
    private SpeakerRepository speakerRepository;

    /**
     * POST  /speakers : Create a new speaker.
     *
     * @param speaker the speaker to create
     * @return the ResponseEntity with status 201 (Created) and with body the new speaker, or with status 400 (Bad Request) if the speaker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/speakers")
    public ResponseEntity<Speaker> createSpeaker(@Valid @RequestBody Speaker speaker) throws URISyntaxException {
        log.debug("REST request to save Speaker : {}", speaker);
        if (speaker.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("speaker", "idexists", "A new speaker cannot already have an ID")).body(null);
        }
        Speaker result = speakerRepository.save(speaker);
        return ResponseEntity.created(new URI("/api/speakers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("speaker", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /speakers : Updates an existing speaker.
     *
     * @param speaker the speaker to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated speaker,
     * or with status 400 (Bad Request) if the speaker is not valid,
     * or with status 500 (Internal Server Error) if the speaker couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/speakers")
    public ResponseEntity<Speaker> updateSpeaker(@Valid @RequestBody Speaker speaker) throws URISyntaxException {
        log.debug("REST request to update Speaker : {}", speaker);
        if (speaker.getId() == null) {
            return createSpeaker(speaker);
        }
        Speaker result = speakerRepository.save(speaker);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("speaker", speaker.getId().toString()))
                .body(result);
    }

    /**
     * GET  /speakers : get all the speakers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of speakers in body
     */
    @GetMapping("/speakers")
    public List<Speaker> getAllSpeakers() {
        log.debug("REST request to get all Speakers");
        List<Speaker> speakers = speakerRepository.findAll();
        return speakers;
    }

    /**
     * GET  /speakers/:id : get the "id" speaker.
     *
     * @param id the id of the speaker to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the speaker, or with status 404 (Not Found)
     */
    @GetMapping("/speakers/{id}")
    public ResponseEntity<Speaker> getSpeaker(@PathVariable Long id) {
        log.debug("REST request to get Speaker : {}", id);
        Speaker speaker = speakerRepository.findOne(id);
        return Optional.ofNullable(speaker)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /speakers/:id : delete the "id" speaker.
     *
     * @param id the id of the speaker to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/speakers/{id}")
    public ResponseEntity<Void> deleteSpeaker(@PathVariable Long id) {
        log.debug("REST request to delete Speaker : {}", id);
        speakerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("speaker", id.toString())).build();
    }

}
