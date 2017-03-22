package de.codecentric.nbyl.confy.api.commands.talks;

import java.time.LocalDate;

public class CreateTalkCommand {

    private final String title;

    private final String event;

    private final LocalDate dateHeld;

    private final String speakerId;

    public CreateTalkCommand(String title, String event, LocalDate dateHeld, String speakerId) {
        this.title = title;
        this.event = event;
        this.dateHeld = dateHeld;
        this.speakerId = speakerId;
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
