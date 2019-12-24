package com.ricardorc.datasource.persistance.network.data;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.ricardorc.datasource.persistance.database.room.entity.MovieDetailPopularEntity;
import com.ricardorc.datasource.source.MoviePopularDataSource;
import com.ricardorc.datasource.persistance.network.client.themovie.ThemovieClient;
import com.ricardorc.datasource.persistance.network.response.MoviePopularResponse;
import com.ricardorc.datasource.persistance.network.service.ThemovieService;
import com.ricardorc.datasource.repository.mapper.GetMoviePopularMapper;
import com.ricardorc.domain.MoviePopular;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePopularDataSourceNetwork implements MoviePopularDataSource {

    private ThemovieService themovieService;
    private GetMoviePopularMapper getMoviePopularMapper;

    public MoviePopularDataSourceNetwork(Context context) {
        this.themovieService = ThemovieClient.getInstance(context).createService(ThemovieService.class);
        this.getMoviePopularMapper = new GetMoviePopularMapper();
    }

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
        return this.themovieService.getObservableMoviePopularResponse()
                .map(new Function<MoviePopularResponse, MoviePopular>() {
                         @Override
                         public MoviePopular apply(MoviePopularResponse moviePopularResponse) throws Exception {
                             return getMoviePopularMapper.convert(moviePopularResponse);
                         }
                     }
                );
    }

    @Override
    public Single<MoviePopular> getSingleMoviePopular() {
        return this.themovieService.getSingleMoviePopularResponse()
                .map(new Function<MoviePopularResponse, MoviePopular>() {
                    @Override
                    public MoviePopular apply(MoviePopularResponse moviePopularResponse) {
                        return getMoviePopularMapper.convert(moviePopularResponse);
                    }
                });
    }

    @Override
    public MutableLiveData<MoviePopular> getMutableLiveDataMoviePopular() {
        final MutableLiveData<MoviePopular> moviePopularMutableLiveData = new MutableLiveData<>();
        this.themovieService.getCallMoviePopularResponse().enqueue(new Callback<MoviePopularResponse>() {
            @Override
            public void onResponse(Call<MoviePopularResponse> call, Response<MoviePopularResponse> response) {
                MoviePopular moviePopular = getMoviePopularMapper.convert(response.body());
                moviePopularMutableLiveData.setValue(moviePopular);
            }

            @Override
            public void onFailure(Call<MoviePopularResponse> call, Throwable t) {

            }
        });
        return moviePopularMutableLiveData;
    }
}
