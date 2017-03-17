package de.codecentric.nbyl.confy.api.commands.speakers;

public class DeleteSpeakerCommand {

    private final String id;

    public DeleteSpeakerCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
