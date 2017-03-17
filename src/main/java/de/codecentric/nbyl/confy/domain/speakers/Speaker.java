package de.codecentric.nbyl.confy.domain.speakers;

import de.codecentric.nbyl.confy.api.commands.speakers.CreateSpeakerCommand;
import de.codecentric.nbyl.confy.api.events.speakers.SpeakerCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Speaker {

    @AggregateIdentifier
    private String id;

    private String surname;

    private String firstName;

    @CommandHandler
    public Speaker(CreateSpeakerCommand command) {
        this.id = command.getId();
        this.surname = command.getSurname();
        this.firstName = command.getFirstName();

        apply(new SpeakerCreatedEvent(this.id,
                this.surname,
                this.firstName));
    }

    protected Speaker() {
    }
}
