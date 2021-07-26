package com.jobsity.leonardoinvernizzi.tvseries.model;

import java.io.Serializable;

public class ShowImage implements Serializable {

    private String medium;
    private String original;

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

}
