package de.codecentric.nbyl.confy.domain.speakers;

import de.codecentric.nbyl.confy.api.commands.speakers.UpdateSpeakerCommand;
import de.codecentric.nbyl.confy.api.events.speakers.SpeakerCreatedEvent;
import de.codecentric.nbyl.confy.api.events.speakers.SpeakerDeletedEvent;
import de.codecentric.nbyl.confy.api.events.speakers.SpeakerUpdatedEvent;
import de.codecentric.nbyl.confy.api.events.talks.TalkCreatedEvent;
import de.codecentric.nbyl.confy.api.events.talks.TalkDeletedEvent;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.eventhandling.EventHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

@AggregateRoot
public class Speaker {

    @AggregateIdentifier
    private String id;

    private String surname;

    private String firstName;

    @AggregateMember
    private final List<Talk> talks = new ArrayList<>();

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

    public void delete() {
        markDeleted();
        apply(new SpeakerDeletedEvent(this.id));
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

    public void addTalk(String id, String title, String event, LocalDate dateHeld) {
        apply(new TalkCreatedEvent(
                id,
                title,
                event,
                dateHeld,
                this.id
        ));
    }

    @EventHandler
    public void on(TalkCreatedEvent event) {
        this.talks.add(new Talk(
                event.getId(),
                event.getTitle(),
                event.getEvent(),
                event.getDateHeld()
        ));
    }

    public void deleteTalk(String id) {
        apply(new TalkDeletedEvent(id, this.id));
    }
}
