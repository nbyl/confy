package de.codecentric.nbyl.confy.rest.dto;

public class SpeakerDTO {

    private String id;

    private String surname;

    private String firstName;

    public SpeakerDTO(String id, String surname, String firstName) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
    }

    public SpeakerDTO() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("surname", surname)
                .add("firstName", firstName)
                .toString();
    }
}
