package de.codecentric.nbyl.confy.api.commands.speakers;

public class UpdateSpeakerCommand extends CreateSpeakerCommand {

    private final String id;

    public UpdateSpeakerCommand(String id, String surname, String firstName) {
        super(surname, firstName);

        this.id = id;
    }

    public String getId() {
        return id;
    }
}
