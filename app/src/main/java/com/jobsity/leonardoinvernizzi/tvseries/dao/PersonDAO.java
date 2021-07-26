package com.jobsity.leonardoinvernizzi.tvseries.dao;

import com.jobsity.leonardoinvernizzi.tvseries.model.CastCredits;
import com.jobsity.leonardoinvernizzi.tvseries.model.PersonSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PersonDAO {

    @GET("/search/people")
    Call<List<PersonSearch>> getPersonSearch(@Query("q") String query);

    @GET("/people/{id}/castcredits?embed=show")
    Call<List<CastCredits>> getPersonShows(@Path("id") int id);

}
