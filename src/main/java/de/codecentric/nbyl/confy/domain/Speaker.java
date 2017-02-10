package de.codecentric.nbyl.confy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Speaker speaker = (Speaker) o;
        if (speaker.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, speaker.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Speaker{" +
            "id=" + id +
            ", surname='" + surname + "'" +
            ", firstName='" + firstName + "'" +
            '}';
    }
}
