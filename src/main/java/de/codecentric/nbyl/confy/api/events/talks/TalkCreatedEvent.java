package de.codecentric.nbyl.confy.api.events.talks;

import java.time.LocalDate;

public class TalkCreatedEvent {

    private final String id;

    private final String title;

    private final String event;

    private final LocalDate dateHeld;

    private final String speakerId;

    public TalkCreatedEvent(String id, String title, String event, LocalDate dateHeld, String speakerId) {
        this.id = id;
        this.title = title;
        this.event = event;
        this.dateHeld = dateHeld;
        this.speakerId = speakerId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getEvent() {
        return event;
    }

    public LocalDate getDateHeld() {
        return dateHeld;
    }

    public String getSpeakerId() {
        return speakerId;
    }
}
