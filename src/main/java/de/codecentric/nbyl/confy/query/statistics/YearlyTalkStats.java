package de.codecentric.nbyl.confy.query.statistics;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "QO_YEARLY_TALK_STATS")
@IdClass(YearlyTalkStatsId.class)
public class YearlyTalkStats {

    @Id
    private Integer year;

    @Id
    private String speakerId;

    private String surname;

    private String firstName;

    private Integer talkCount;

    public YearlyTalkStats(Integer year, String speakerId, String surname, String firstName) {
        this.year = year;
        this.speakerId = speakerId;
        this.surname = surname;
        this.firstName = firstName;

        this.talkCount = 0;
    }

    public YearlyTalkStats() {
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(String speakerId) {
        this.speakerId = speakerId;
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

    public Integer getTalkCount() {
        return talkCount;
    }

    public void setTalkCount(Integer talkCount) {
        this.talkCount = talkCount;
    }

    public void increaseTalkCount() {
        this.talkCount = talkCount + 1;
    }

    public void decreaseTalkCount() {
        this.talkCount = talkCount - 1;
    }
}
