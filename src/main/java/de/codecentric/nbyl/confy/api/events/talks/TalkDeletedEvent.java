package de.codecentric.nbyl.confy.api.events.talks;

public class TalkDeletedEvent {

    private final String id;

    private final String speakerId;

    public TalkDeletedEvent(String id, String speakerId) {
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
