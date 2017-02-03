package de.codecentric.nbyl.confy.web.rest;

import de.codecentric.nbyl.confy.domain.Talk;
import de.codecentric.nbyl.confy.repository.TalkRepository;
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
 * REST controller for managing Talk.
 */
@RestController
@RequestMapping("/api")
public class TalkResource {

    private final Logger log = LoggerFactory.getLogger(TalkResource.class);
        
    @Inject
    private TalkRepository talkRepository;

    /**
     * POST  /talks : Create a new talk.
     *
     * @param talk the talk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new talk, or with status 400 (Bad Request) if the talk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/talks")
    public ResponseEntity<Talk> createTalk(@Valid @RequestBody Talk talk) throws URISyntaxException {
        log.debug("REST request to save Talk : {}", talk);
        if (talk.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("talk", "idexists", "A new talk cannot already have an ID")).body(null);
        }
        Talk result = talkRepository.save(talk);
        return ResponseEntity.created(new URI("/api/talks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("talk", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /talks : Updates an existing talk.
     *
     * @param talk the talk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated talk,
     * or with status 400 (Bad Request) if the talk is not valid,
     * or with status 500 (Internal Server Error) if the talk couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/talks")
    public ResponseEntity<Talk> updateTalk(@Valid @RequestBody Talk talk) throws URISyntaxException {
        log.debug("REST request to update Talk : {}", talk);
        if (talk.getId() == null) {
            return createTalk(talk);
        }
        Talk result = talkRepository.save(talk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("talk", talk.getId().toString()))
            .body(result);
    }

    /**
     * GET  /talks : get all the talks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of talks in body
     */
    @GetMapping("/talks")
    public List<Talk> getAllTalks() {
        log.debug("REST request to get all Talks");
        List<Talk> talks = talkRepository.findAllWithEagerRelationships();
        return talks;
    }

    /**
     * GET  /talks/:id : get the "id" talk.
     *
     * @param id the id of the talk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the talk, or with status 404 (Not Found)
     */
    @GetMapping("/talks/{id}")
    public ResponseEntity<Talk> getTalk(@PathVariable Long id) {
        log.debug("REST request to get Talk : {}", id);
        Talk talk = talkRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(talk)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /talks/:id : delete the "id" talk.
     *
     * @param id the id of the talk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/talks/{id}")
    public ResponseEntity<Void> deleteTalk(@PathVariable Long id) {
        log.debug("REST request to delete Talk : {}", id);
        talkRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("talk", id.toString())).build();
    }

}
