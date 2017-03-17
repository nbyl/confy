package de.codecentric.nbyl.confy.rest;

import de.codecentric.nbyl.confy.api.commands.speakers.CreateSpeakerCommand;
import de.codecentric.nbyl.confy.api.commands.speakers.DeleteSpeakerCommand;
import de.codecentric.nbyl.confy.api.commands.speakers.UpdateSpeakerCommand;
import de.codecentric.nbyl.confy.query.speakers.SpeakerQueryObjectRepository;
import de.codecentric.nbyl.confy.rest.dto.SpeakerDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping("/api/speakers")
@RestController
public class SpeakerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeakerResource.class);

    private final CommandGateway commandGateway;

    private final SpeakerQueryObjectRepository speakerRepository;

    public SpeakerResource(CommandGateway commandGateway, SpeakerQueryObjectRepository speakerRepository) {
        this.commandGateway = commandGateway;
        this.speakerRepository = speakerRepository;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity> createSpeaker(@RequestBody SpeakerDTO speaker) throws URISyntaxException {
        return commandGateway.send(new CreateSpeakerCommand(speaker.getSurname(), speaker.getFirstName()))
                .thenApply(this::createSpeakerURI)
                .thenApply(uri -> ResponseEntity.created(uri).build());
    }

    @PutMapping("/{id}")
    public CompletableFuture<Object> updateSpeaker(@PathVariable("id") String id, @RequestBody SpeakerDTO speaker) {
        return commandGateway.send(new UpdateSpeakerCommand(id, speaker.getSurname(), speaker.getFirstName()));
    }

    private <U> URI createSpeakerURI(Object id) {
        try {
            return new URI("/api/speakers/" + id);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<SpeakerDTO> getSpeakers() {
        return StreamSupport.stream(this.speakerRepository.findAll().spliterator(), false)
                .map(speakerQueryObject ->
                        new SpeakerDTO(speakerQueryObject.getId(),
                                speakerQueryObject.getSurname(),
                                speakerQueryObject.getFirstName()))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SpeakerDTO> getSpeaker(@PathVariable("id") String id) {
        return Optional.ofNullable(this.speakerRepository.findOne(id))
                .map(speaker -> ResponseEntity.ok().body(new SpeakerDTO(speaker.getId(), speaker.getSurname(), speaker.getFirstName())))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public void deleteSpeaker(@PathVariable("id") String id) {
        this.commandGateway.send(new DeleteSpeakerCommand(id));
    }

    @ExceptionHandler(AggregateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {
    }
}
