package com.jobsity.leonardoinvernizzi.tvseries;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jobsity.leonardoinvernizzi.tvseries.dao.FavouritesDAO;
import com.jobsity.leonardoinvernizzi.tvseries.model.Show;

@Database(entities = {Show.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavouritesDAO favouritesDAO();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE favourites "
                    + " ADD COLUMN name VARCHAR(100)");
        }
    };
}