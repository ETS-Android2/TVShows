package com.jobsity.leonardoinvernizzi.tvseries.model;

import java.io.Serializable;

public class Person implements Serializable {

    private int id;
    private String name;
    private ShowImage image;

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

    public ShowImage getImage() {
        return image;
    }

    public void setImage(ShowImage image) {
        this.image = image;
    }
}
