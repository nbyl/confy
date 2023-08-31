package de.codecentric.nbyl.confy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * A Speaker.
 */
@Entity
@Table(name = "speaker")
public class Speaker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "first_name")
    private String firstName;

    @ManyToMany(mappedBy = "speakers")
    @JsonIgnore
    private Set<Talk> talks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public Speaker surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public Speaker firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    public Speaker talks(Set<Talk> talks) {
        this.talks = talks;
        return this;
    }

    public Speaker addTalks(Talk talk) {
        talks.add(talk);
        talk.getSpeakers().add(this);
        return this;
    }

    public Speaker removeTalks(Talk talk) {
        talks.remove(talk);
        talk.getSpeakers().remove(this);
        return this;
    }

    public void setTalks(Set<Talk> talks) {
        this.talks = talks;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Speaker.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("surname='" + surname + "'")
                .add("firstName='" + firstName + "'")
                .add("talks=" + talks)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speaker)) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(getSurname(), speaker.getSurname()) &&
                Objects.equals(getFirstName(), speaker.getFirstName()) &&
                Objects.equals(getTalks(), speaker.getTalks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSurname(), getFirstName(), getTalks());
    }
}
