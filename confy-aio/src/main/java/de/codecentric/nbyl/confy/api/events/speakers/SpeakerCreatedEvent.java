package de.codecentric.nbyl.confy.api.events.speakers;

public class SpeakerCreatedEvent {

    private final String id;

    private final String surname;

    private final String firstName;

    public SpeakerCreatedEvent(String id, String surname, String firstName) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }
}
