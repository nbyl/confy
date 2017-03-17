package de.codecentric.nbyl.confy.domain.speakers;

public class SpeakerDeletedEvent {

    private final String id;

    public SpeakerDeletedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
