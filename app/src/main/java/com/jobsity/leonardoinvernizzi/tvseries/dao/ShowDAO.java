package com.jobsity.leonardoinvernizzi.tvseries.dao;

import com.jobsity.leonardoinvernizzi.tvseries.model.Show;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShowDAO {

    @GET("/shows")
    Call<List<Show>> getShows(@Query("page") int page);

    @GET("/shows/{id}")
    Call<Show> getShow(@Path("id") int id);

}
