package com.ricardorc.datasource.persistance.network.service;

import com.ricardorc.datasource.persistance.network.response.MovieNowPlayingResponse;
import com.ricardorc.datasource.persistance.network.response.MoviePopularResponse;
import com.ricardorc.datasource.persistance.network.utilities.Utilities;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ThemovieService {
    //Peliculas populares (Observable)
    @GET("movie/popular" + Utilities.API_KEY + Utilities.LANGUAGE)
    Observable<MoviePopularResponse> getObservableMoviePopularResponse();

    //Peliculas populares (Single)
    @GET("movie/popular" + Utilities.API_KEY + Utilities.LANGUAGE)
    Single<MoviePopularResponse> getSingleMoviePopularResponse();

    //Peliculas populares (LiveData)
    @GET("movie/popular" + Utilities.API_KEY)
    Call<MoviePopularResponse> getCallMoviePopularResponse();

    //Consigue la película más reciente
    @GET("movie/latest" + Utilities.API_KEY)
    Observable<MoviePopularResponse> getObservableLatestObservable();

    //Obtener una lista de películas en los cines
    @GET("movie/now_playing" + Utilities.API_KEY)
    Observable<MovieNowPlayingResponse> getListNowPlayingObservable();
}
