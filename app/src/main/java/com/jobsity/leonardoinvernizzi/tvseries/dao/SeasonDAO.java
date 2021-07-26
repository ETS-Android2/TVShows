package com.jobsity.leonardoinvernizzi.tvseries.dao;

import com.jobsity.leonardoinvernizzi.tvseries.model.Season;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SeasonDAO {

    @GET("/shows/{id}/seasons")
    Call<List<Season>> getSeasons(@Path("id") int id);

}
