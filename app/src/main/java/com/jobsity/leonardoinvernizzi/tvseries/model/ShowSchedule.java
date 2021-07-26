package com.jobsity.leonardoinvernizzi.tvseries.model;

import java.io.Serializable;

public class ShowSchedule implements Serializable {

    private String time;
    private String[] days;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getDays() {
        return days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }
}
