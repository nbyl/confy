package de.codecentric.nbyl.confy.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;


@Entity
@Table(name = "talk")
public class Talk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @ManyToOne
    @NotNull
    private Event heldAt;

    @ManyToMany
    @NotNull
    @JoinTable(name = "talk_speakers",
            joinColumns = @JoinColumn(name = "talks_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "speakers_id", referencedColumnName = "ID"))
    private Set<Speaker> speakers = new HashSet<>();

    public Talk() {}

    public Talk(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Talk title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Talk description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public Talk startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public Event getHeldAt() {
        return heldAt;
    }

    public Talk heldAt(Event event) {
        this.heldAt = event;
        return this;
    }

    public void setHeldAt(Event event) {
        this.heldAt = event;
    }

    public Set<Speaker> getSpeakers() {
        return speakers;
    }

    public Talk speakers(Set<Speaker> speakers) {
        this.speakers = speakers;
        return this;
    }

    public Talk addSpeakers(Speaker speaker) {
        speakers.add(speaker);
        speaker.getTalks().add(this);
        return this;
    }

    public Talk removeSpeakers(Speaker speaker) {
        speakers.remove(speaker);
        speaker.getTalks().remove(this);
        return this;
    }

    public void setSpeakers(Set<Speaker> speakers) {
        this.speakers = speakers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Talk)) return false;
        Talk talk = (Talk) o;
        return Objects.equals(id, talk.id) &&
                Objects.equals(title, talk.title) &&
                Objects.equals(description, talk.description) &&
                Objects.equals(startTime, talk.startTime) &&
                Objects.equals(heldAt, talk.heldAt) &&
                Objects.equals(speakers, talk.speakers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, startTime, heldAt, speakers);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Talk.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .add("description='" + description + "'")
                .add("startTime=" + startTime)
                .add("heldAt=" + heldAt)
                .add("speakers=" + speakers)
                .toString();
    }
}
