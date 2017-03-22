package de.codecentric.nbyl.confy.rest;

import de.codecentric.nbyl.confy.api.commands.talks.CreateTalkCommand;
import de.codecentric.nbyl.confy.query.talks.TalkQueryObjectRepository;
import de.codecentric.nbyl.confy.rest.dto.TalkDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/talks")
public class TalkResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TalkResource.class);

    private final CommandGateway commandGateway;

    private final TalkQueryObjectRepository talkRepository;

    public TalkResource(CommandGateway commandGateway, TalkQueryObjectRepository talkRepository) {
        this.commandGateway = commandGateway;
        this.talkRepository = talkRepository;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity> createTalk(@RequestBody TalkDTO talk) {
        LOGGER.info("Creating talk {}", talk);
        return commandGateway.send(new CreateTalkCommand(
                talk.getTitle(),
                talk.getEvent(),
                talk.getDateHeld(),
                talk.getSpeakerId()))
                .thenApply(this::createTalkURI)
                .thenApply(uri -> ResponseEntity.created(uri).build());
    }

    private <U> URI createTalkURI(Object id) {
        try {
            return new URI("/api/talks/" + id);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping
    public List<TalkDTO> getTalks() {
        return StreamSupport.stream(this.talkRepository.findAll().spliterator(), false)
                .map(talk ->
                        new TalkDTO(
                                talk.getId(),
                                talk.getTitle(),
                                talk.getEvent(),
                                talk.getDateHeld(),
                                talk.getSpeakerId()))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TalkDTO> getSpeaker(@PathVariable("id") String id) {
        return Optional.ofNullable(this.talkRepository.findOne(id))
                .map(talk -> ResponseEntity.ok().body(
                        new TalkDTO(
                                talk.getId(),
                                talk.getTitle(),
                                talk.getEvent(),
                                talk.getDateHeld(),
                                talk.getSpeakerId()
                        ))
                )
                .orElse(ResponseEntity.notFound().build());
    }
}
