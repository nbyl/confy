package de.codecentric.nbyl.confy.domain.speakers;

import java.time.LocalDate;

public class Talk {

    private  String id;

    private  String title;

    private  String event;

    private LocalDate dateHeld;

    public Talk(String id, String title, String event, LocalDate dateHeld) {
        this.id = id;
        this.title = title;
        this.event = event;
        this.dateHeld = dateHeld;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LocalDate getDateHeld() {
        return dateHeld;
    }

    public void setDateHeld(LocalDate dateHeld) {
        this.dateHeld = dateHeld;
    }
}
