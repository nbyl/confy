package de.codecentric.nbyl.confy.query.speakers;

import de.codecentric.nbyl.confy.api.events.speakers.SpeakerCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class SpeakerQueryObjectUpdater {

    private final SpeakerRepository speakerRepository;

    public SpeakerQueryObjectUpdater(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    @EventHandler
    public void on(SpeakerCreatedEvent event) {
        speakerRepository.save(new SpeakerQueryObject(event.getId(), event.getSurname(), event.getFirstName()));
    }
}
