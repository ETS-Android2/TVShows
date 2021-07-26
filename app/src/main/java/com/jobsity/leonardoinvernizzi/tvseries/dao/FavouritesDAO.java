package com.jobsity.leonardoinvernizzi.tvseries.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.jobsity.leonardoinvernizzi.tvseries.model.Show;

import java.util.List;

@Dao
public interface FavouritesDAO {

    @Query("SELECT * FROM favourites order by name")
    List<Show> getAll();

    @Query("SELECT * FROM favourites WHERE id = :id")
    Show findById(int id);

    @Insert
    void insert(Show show);

    @Delete
    void delete(Show show);
}
