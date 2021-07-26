package com.jobsity.leonardoinvernizzi.tvseries.dao;

import com.jobsity.leonardoinvernizzi.tvseries.model.Episode;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EpisodeDAO {

    @GET("/seasons/{id}/episodes")
    Call<List<Episode>> getEpisodes(@Path("id") int id);

}
