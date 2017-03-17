package de.codecentric.nbyl.confy.api.commands.speakers;

public class CreateSpeakerCommand {

    private final String id;

    private final String surname;

    private final String firstName;

    public CreateSpeakerCommand(String id, String surname, String firstName) {
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
