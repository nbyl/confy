package de.codecentric.nbyl.confy.query.talks;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "QO_TALKS")
public class TalkQueryObject {

    @Id
    private String id;

    private String title;

    private String event;

    private LocalDate dateHeld;

    private String speakerId;

    public TalkQueryObject(String id, String title, String event, LocalDate dateHeld, String speakerId) {
        this.id = id;
        this.title = title;
        this.event = event;
        this.dateHeld = dateHeld;
        this.speakerId = speakerId;
    }

    public TalkQueryObject() {
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

    public String getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(String speakerId) {
        this.speakerId = speakerId;
    }
}
