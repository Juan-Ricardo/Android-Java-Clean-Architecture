package com.ricardorc.datasource.persistance.database.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ricardorc.datasource.persistance.database.room.dao.MovieDetailPopularDao;
import com.ricardorc.datasource.persistance.database.room.entity.MovieDetailPopularEntity;

@Database(entities = {MovieDetailPopularEntity.class}, version = 1, exportSchema = false)
public abstract class RoomDatabaseManager extends RoomDatabase {
    public abstract MovieDetailPopularDao movieDetailPopularDao();

    private static volatile RoomDatabaseManager INSTANCE;

    public static RoomDatabaseManager getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabaseManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabaseManager.class, "themovie_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
