package de.codecentric.nbyl.confy.domain.speakers;

import de.codecentric.nbyl.confy.api.commands.speakers.UpdateSpeakerCommand;
import de.codecentric.nbyl.confy.api.events.speakers.SpeakerCreatedEvent;
import de.codecentric.nbyl.confy.api.events.speakers.SpeakerUpdatedEvent;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Speaker {

    @AggregateIdentifier
    private String id;

    private String surname;

    private String firstName;

    public Speaker(String id, String surname, String firstName) {
        apply(new SpeakerCreatedEvent(id,
                surname,
                firstName));
    }

    public Speaker() {
    }

    public void update(UpdateSpeakerCommand command) {
        apply(new SpeakerUpdatedEvent(command.getId(),
                command.getSurname(),
                command.getFirstName()));
    }

    @EventHandler
    public void on(SpeakerCreatedEvent event) {
        this.id = event.getId();
        this.surname = event.getSurname();
        this.firstName = event.getFirstName();
    }

    @EventHandler
    public void on(SpeakerUpdatedEvent event) {
        this.surname = event.getSurname();
        this.firstName = event.getSurname();
    }
}
