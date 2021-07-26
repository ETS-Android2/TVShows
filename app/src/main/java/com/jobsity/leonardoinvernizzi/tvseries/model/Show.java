package com.jobsity.leonardoinvernizzi.tvseries.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.jobsity.leonardoinvernizzi.tvseries.Utils;

import java.io.Serializable;

@Entity(tableName = "favourites")
public class Show implements Serializable {

    @PrimaryKey
    private int id;
    private String name;
    @Ignore
    private String url;
    @Ignore
    private ShowImage image;
    @Ignore
    private ShowSchedule schedule;
    @Ignore
    private String[] genres;
    @Ignore
    private String summary;
    @Ignore
    private ShowRating rating;
    @Ignore
    private String premiered;

    public ShowImage getImage() {
        return image;
    }

    public void setImage(ShowImage image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getSummary() {
        return Utils.cleanTags(this.summary);
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ShowRating getRating() {
        return rating;
    }

    public void setRating(ShowRating rating) {
        this.rating = rating;
    }

    public String getPremiered() {
        return premiered;
    }

    public ShowSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(ShowSchedule schedule) {
        this.schedule = schedule;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    /*public Calendar getPremieredDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date date = simpleDateFormat.parse(this.getPremiered());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }*/
}
