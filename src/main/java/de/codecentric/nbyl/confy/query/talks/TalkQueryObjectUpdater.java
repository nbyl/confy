package de.codecentric.nbyl.confy.query.talks;

import de.codecentric.nbyl.confy.api.events.talks.TalkCreatedEvent;
import de.codecentric.nbyl.confy.api.events.talks.TalkDeletedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class TalkQueryObjectUpdater {

    private final TalkQueryObjectRepository repository;

    public TalkQueryObjectUpdater(TalkQueryObjectRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(TalkCreatedEvent event) {
        this.repository.save(new TalkQueryObject(
                event.getId(),
                event.getTitle(),
                event.getEvent(),
                event.getDateHeld(),
                event.getSpeakerId()
        ));
    }

    @EventHandler
    public void on(TalkDeletedEvent event) {
        this.repository.delete(event.getId());
    }
}
