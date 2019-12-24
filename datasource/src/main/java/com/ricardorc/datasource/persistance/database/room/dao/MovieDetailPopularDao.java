package com.ricardorc.datasource.persistance.database.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ricardorc.datasource.persistance.database.room.entity.MovieDetailPopularEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

//https://developer.android.com/training/data-storage/room/accessing-data

@Dao
public interface MovieDetailPopularDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(MovieDetailPopularEntity movieDetailPopularEntity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(List<MovieDetailPopularEntity> movieDetailPopularEntity);

    /*@Insert(onConflict = OnConflictStrategy.IGNORE)
    Maybe<List<Long>> insert(List<MovieDetailPopularEntity> movieDetailPopularEntities);*/

    @Query("DELETE FROM movie_detail_popular")
    void deleteAll();

    @Query("SELECT * from movie_detail_popular")
    Observable<List<MovieDetailPopularEntity>> getObservableMoviePopularEntity();

    //@Query("SELECT * from movie_detail_popular ORDER BY popularity DESC")
    @Query("SELECT * from movie_detail_popular")
    Single<List<MovieDetailPopularEntity>> getSingleMoviePopularEntity();

    @Query("SELECT * FROM movie_detail_popular WHERE id = :movieDetailPopularEntityId")
    Single<MovieDetailPopularEntity> geMovieDetailPopularEntityById(String movieDetailPopularEntityId);
}
