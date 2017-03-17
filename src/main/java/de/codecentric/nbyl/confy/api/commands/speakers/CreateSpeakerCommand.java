package de.codecentric.nbyl.confy.api.commands.speakers;

public class CreateSpeakerCommand {

    private final String surname;

    private final String firstName;

    public CreateSpeakerCommand(String surname, String firstName) {
        this.surname = surname;
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }
}
