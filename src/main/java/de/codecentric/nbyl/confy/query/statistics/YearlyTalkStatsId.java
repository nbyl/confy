package de.codecentric.nbyl.confy.query.statistics;

import java.io.Serializable;

public class YearlyTalkStatsId implements Serializable {

    private Integer year;

    private String speakerId;

    public YearlyTalkStatsId(Integer year, String speakerId) {
        this.year = year;
        this.speakerId = speakerId;
    }

    public YearlyTalkStatsId() {
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
}
