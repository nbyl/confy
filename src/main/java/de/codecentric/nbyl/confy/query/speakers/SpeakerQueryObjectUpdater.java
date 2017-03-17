package de.codecentric.nbyl.confy.query.speakers;

import de.codecentric.nbyl.confy.api.events.speakers.SpeakerCreatedEvent;
import de.codecentric.nbyl.confy.api.events.speakers.SpeakerUpdatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class SpeakerQueryObjectUpdater {

    private final SpeakerQueryObjectRepository speakerQueryObjectRepository;

    public SpeakerQueryObjectUpdater(SpeakerQueryObjectRepository speakerQueryObjectRepository) {
        this.speakerQueryObjectRepository = speakerQueryObjectRepository;
    }

    @EventHandler
    public void on(SpeakerCreatedEvent event) {
        speakerQueryObjectRepository.save(new SpeakerQueryObject(event.getId(), event.getSurname(), event.getFirstName()));
    }

    @EventHandler
    public void on(SpeakerUpdatedEvent event) {
        SpeakerQueryObject speaker = speakerQueryObjectRepository.findOne(event.getId());
        speaker.setSurname(event.getSurname());
        speaker.setFirstName(event.getFirstName());
        speakerQueryObjectRepository.save(speaker);
    }
}
