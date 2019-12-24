package com.ricardorc.datasource.repository;

import androidx.lifecycle.MutableLiveData;

import com.ricardorc.domain.MovieDetailPopular;
import com.ricardorc.domain.MoviePopular;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MoviePopularRepository {
    Observable<MoviePopular> getObservableMoviePopularNetwork();

    Single<MoviePopular> getSingleMoviePopularNetwork();

    Completable insertCompletableMoviePopularNetwork(MovieDetailPopular movieDetailPopular);

    MutableLiveData<MoviePopular> getMutableLiveDataMoviePopularNetwork();

    Observable<MoviePopular> getObservableMoviePopularDatabase();

    Single<MoviePopular> getSingleMoviePopularDatabase();

    Completable insertCompletableMoviePopularDatabase(MovieDetailPopular movieDetailPopular);

    Completable insertCompletableMoviePopularDatabase(List<MovieDetailPopular> movieDetailPopulars);

    Observable<MoviePopular> getObservableMoviePopularMemory();

    Completable insertCompletableMoviePopularMemory(MovieDetailPopular movieDetailPopular);

    Single<MoviePopular> getSingleMoviePopularMemory();
}
