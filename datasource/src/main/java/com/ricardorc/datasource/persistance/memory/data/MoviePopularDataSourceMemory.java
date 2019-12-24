package com.ricardorc.datasource.persistance.memory.data;

import androidx.lifecycle.MutableLiveData;

import com.ricardorc.datasource.persistance.database.room.entity.MovieDetailPopularEntity;
import com.ricardorc.datasource.source.MoviePopularDataSource;
import com.ricardorc.domain.MoviePopular;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MoviePopularDataSourceMemory implements MoviePopularDataSource {

    @Override
    public Completable insertCompletableMoviePopular(MovieDetailPopularEntity movieDetailPopularEntity) {
        return null;
    }

    @Override
    public Completable insertCompletableMoviePopular(List<MovieDetailPopularEntity> movieDetailPopulars) {
        return null;
    }

    @Override
    public Observable<MoviePopular> getObservableMoviePopular() {
        return null;
    }

    @Override
    public Single<MoviePopular> getSingleMoviePopular() {
        return null;
    }

    @Override
    public MutableLiveData<MoviePopular> getMutableLiveDataMoviePopular() {
        return null;
    }
}
