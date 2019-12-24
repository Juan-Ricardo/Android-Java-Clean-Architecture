package com.ricardorc.datasource.persistance.database.data;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.ricardorc.datasource.persistance.database.room.RoomDatabaseManager;
import com.ricardorc.datasource.persistance.database.room.dao.MovieDetailPopularDao;
import com.ricardorc.datasource.persistance.database.room.entity.MovieDetailPopularEntity;
import com.ricardorc.datasource.persistance.database.room.mapper.MovieDetailPopularEntityMapper;
import com.ricardorc.datasource.source.MoviePopularDataSource;
import com.ricardorc.domain.MoviePopular;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class MoviePopularDataSourceDatabase implements MoviePopularDataSource {

    private RoomDatabaseManager roomDatabaseManager;
    private MovieDetailPopularDao movieDetailPopularDao;
    private MovieDetailPopularEntityMapper mapper;

    public MoviePopularDataSourceDatabase(Context context) {
        this.roomDatabaseManager = RoomDatabaseManager.getInstance(context);
        this.movieDetailPopularDao = this.roomDatabaseManager.movieDetailPopularDao();
        this.mapper = new MovieDetailPopularEntityMapper();
    }

    @Override
    public Completable insertCompletableMoviePopular(MovieDetailPopularEntity movieDetailPopularEntity) {
        return this.movieDetailPopularDao.insert(movieDetailPopularEntity);
    }

    @Override
    public Completable insertCompletableMoviePopular(List<MovieDetailPopularEntity> movieDetailPopulars) {

        //Eliminamos objetos anteriores para agregar datos actualizados.
        this.movieDetailPopularDao.deleteAll();

        //Insertamos objetos actualizados.
        return this.movieDetailPopularDao.insert(movieDetailPopulars);
    }

    @Override
    public Observable<MoviePopular> getObservableMoviePopular() {
        return this.movieDetailPopularDao.getObservableMoviePopularEntity()
                .map(new Function<List<MovieDetailPopularEntity>, MoviePopular>() {
                    @Override
                    public MoviePopular apply(List<MovieDetailPopularEntity> movieDetailPopularEntities) {
                        return mapper.convert(movieDetailPopularEntities);
                    }
                });
    }

    @Override
    public Single<MoviePopular> getSingleMoviePopular() {
        return this.movieDetailPopularDao.getSingleMoviePopularEntity()
                .map(new Function<List<MovieDetailPopularEntity>, MoviePopular>() {
                    @Override
                    public MoviePopular apply(List<MovieDetailPopularEntity> movieDetailPopularEntities) {
                        return mapper.convert(movieDetailPopularEntities);
                    }
                });
    }

    @Override
    public MutableLiveData<MoviePopular> getMutableLiveDataMoviePopular() {
        return null;
    }
}
