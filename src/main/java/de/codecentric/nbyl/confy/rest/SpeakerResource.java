package de.codecentric.nbyl.confy.rest;

import de.codecentric.nbyl.confy.rest.dto.SpeakerDTO;
import de.codecentric.nbyl.confy.query.speakers.SpeakerRepository;
import de.codecentric.nbyl.confy.api.commands.speakers.CreateSpeakerCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping("/api/speakers")
@RestController
public class SpeakerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeakerResource.class);

    private final CommandGateway commandGateway;

    private final SpeakerRepository speakerRepository;

    public SpeakerResource(CommandGateway commandGateway, SpeakerRepository speakerRepository) {
        this.commandGateway = commandGateway;
        this.speakerRepository = speakerRepository;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity> createSpeaker(@RequestBody SpeakerDTO speaker) throws URISyntaxException {
        return commandGateway.send(new CreateSpeakerCommand(UUID.randomUUID().toString(), speaker.getSurname(), speaker.getFirstName()))
                .thenApply(this::createSpeakerURI)
                .thenApply(uri -> ResponseEntity.created(uri).build());
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

}
