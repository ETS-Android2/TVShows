package com.jobsity.leonardoinvernizzi.tvseries.dao;

import com.jobsity.leonardoinvernizzi.tvseries.model.ShowSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShowSearchDAO {

    @GET("/search/shows")
    Call<List<ShowSearch>> getShowsSearch(@Query("q") String q);

}
