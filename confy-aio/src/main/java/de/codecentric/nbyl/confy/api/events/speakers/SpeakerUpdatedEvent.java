package de.codecentric.nbyl.confy.api.events.speakers;

public class SpeakerUpdatedEvent extends SpeakerCreatedEvent {

    public SpeakerUpdatedEvent(String id, String surname, String firstName) {
        super(id, surname, firstName);
    }
}
