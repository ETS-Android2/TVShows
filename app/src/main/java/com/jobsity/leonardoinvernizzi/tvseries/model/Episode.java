package com.jobsity.leonardoinvernizzi.tvseries.model;

import com.jobsity.leonardoinvernizzi.tvseries.Utils;

import java.io.Serializable;

public class Episode implements Serializable {

    private int id;
    private String name;
    private int season;
    private String summary;
    private ShowImage image;
    private int number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getSummary() {
        return Utils.cleanTags(summary);
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ShowImage getImage() {
        return image;
    }

    public void setImage(ShowImage image) {
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
