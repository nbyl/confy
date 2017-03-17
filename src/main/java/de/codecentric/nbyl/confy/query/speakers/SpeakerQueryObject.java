package de.codecentric.nbyl.confy.query.speakers;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QO_SPEAKER")
public class SpeakerQueryObject {

    @Id
    private String id;

    private String surname;

    private String firstName;

    public SpeakerQueryObject(String id, String surname, String firstName) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
    }

    protected SpeakerQueryObject() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
