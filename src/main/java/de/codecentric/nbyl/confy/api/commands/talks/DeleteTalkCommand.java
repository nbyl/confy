package de.codecentric.nbyl.confy.api.commands.talks;

public class DeleteTalkCommand {

    private final String id;

    private final String speakerId;

    public DeleteTalkCommand(String id, String speakerId) {
        this.id = id;
        this.speakerId = speakerId;
    }

    public String getId() {
        return id;
    }

    public String getSpeakerId() {
        return speakerId;
    }
}
