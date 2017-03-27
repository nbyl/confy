package de.codecentric.nbyl.confy.api.events.talks;

import java.time.LocalDate;

public class TalkCreatedEvent {

    private final String id;

    private final String title;

    private final String event;

    private final LocalDate dateHeld;

    private final String speakerId;

    private final String speakerSurname;

    private final String speakerFirstName;

    public TalkCreatedEvent(String id, String title, String event, LocalDate dateHeld, String speakerId, String speakerSurname, String speakerFirstName) {
        this.id = id;
        this.title = title;
        this.event = event;
        this.dateHeld = dateHeld;
        this.speakerId = speakerId;
        this.speakerSurname = speakerSurname;
        this.speakerFirstName = speakerFirstName;
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

    public String getSpeakerSurname() {
        return speakerSurname;
    }

    public String getSpeakerFirstName() {
        return speakerFirstName;
    }
}
