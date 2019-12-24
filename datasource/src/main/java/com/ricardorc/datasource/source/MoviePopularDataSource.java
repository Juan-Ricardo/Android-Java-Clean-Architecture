package com.ricardorc.datasource.source;

import androidx.lifecycle.MutableLiveData;

import com.ricardorc.datasource.persistance.database.room.entity.MovieDetailPopularEntity;
import com.ricardorc.domain.MoviePopular;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MoviePopularDataSource {
    Completable insertCompletableMoviePopular(MovieDetailPopularEntity movieDetailPopularEntity);

    Completable insertCompletableMoviePopular(List<MovieDetailPopularEntity> movieDetailPopularEntities);

    Observable<MoviePopular> getObservableMoviePopular();

    Single<MoviePopular> getSingleMoviePopular();

    MutableLiveData<MoviePopular> getMutableLiveDataMoviePopular();
}
