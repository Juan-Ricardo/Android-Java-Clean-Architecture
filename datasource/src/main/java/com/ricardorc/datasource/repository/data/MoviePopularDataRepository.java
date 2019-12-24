package com.ricardorc.datasource.repository.data;

import androidx.lifecycle.MutableLiveData;

import com.ricardorc.datasource.persistance.database.room.entity.MovieDetailPopularEntity;
import com.ricardorc.datasource.persistance.memory.data.MoviePopularDataSourceMemory;
import com.ricardorc.datasource.persistance.database.data.MoviePopularDataSourceDatabase;
import com.ricardorc.datasource.persistance.network.data.MoviePopularDataSourceNetwork;
import com.ricardorc.datasource.repository.MoviePopularRepository;
import com.ricardorc.datasource.repository.mapper.InsertMoviePopularMapper;
import com.ricardorc.domain.MovieDetailPopular;
import com.ricardorc.domain.MoviePopular;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MoviePopularDataRepository implements MoviePopularRepository {

    private MoviePopularDataSourceNetwork moviePopularDataSourceNetwork;
    private MoviePopularDataSourceDatabase moviePopularDataSourceDatabase;
    private MoviePopularDataSourceMemory moviePopularDataSourceMemory;
    private InsertMoviePopularMapper mapper;

    public MoviePopularDataRepository(MoviePopularDataSourceNetwork moviePopularDataSourceNetwork,
                                      MoviePopularDataSourceDatabase moviePopularDataSourceDatabase,
                                      MoviePopularDataSourceMemory moviePopularDataSourceMemory) {
        this.moviePopularDataSourceNetwork = moviePopularDataSourceNetwork;
        this.moviePopularDataSourceDatabase = moviePopularDataSourceDatabase;
        this.moviePopularDataSourceMemory = moviePopularDataSourceMemory;
        this.mapper=new InsertMoviePopularMapper();
    }

    @Override
    public Observable<MoviePopular> getObservableMoviePopularNetwork() {
        return this.moviePopularDataSourceNetwork.getObservableMoviePopular();
    }

    @Override
    public Single<MoviePopular> getSingleMoviePopularNetwork() {
        return this.moviePopularDataSourceNetwork.getSingleMoviePopular();
    }

    @Override
    public Completable insertCompletableMoviePopularNetwork(MovieDetailPopular movieDetailPopular) {
        return null;
    }

    @Override
    public MutableLiveData<MoviePopular> getMutableLiveDataMoviePopularNetwork() {
        return this.moviePopularDataSourceNetwork.getMutableLiveDataMoviePopular();
    }

    @Override
    public Observable<MoviePopular> getObservableMoviePopularDatabase() {
        return this.moviePopularDataSourceDatabase.getObservableMoviePopular();
    }

    @Override
    public Single<MoviePopular> getSingleMoviePopularDatabase() {
        return this.moviePopularDataSourceDatabase.getSingleMoviePopular();
    }

    @Override
    public Completable insertCompletableMoviePopularDatabase(MovieDetailPopular movieDetailPopular) {
        MovieDetailPopularEntity movieDetailPopularEntity = this.mapper.convert(movieDetailPopular);
        return this.moviePopularDataSourceDatabase.insertCompletableMoviePopular(movieDetailPopularEntity);
    }

    @Override
    public Completable insertCompletableMoviePopularDatabase(List<MovieDetailPopular> movieDetailPopulars) {
        List<MovieDetailPopularEntity> movieDetailPopularEntities = new LinkedList<>();
        for (MovieDetailPopular row : movieDetailPopulars) {

            MovieDetailPopularEntity movieDetailPopularEntity = new MovieDetailPopularEntity();

            movieDetailPopularEntity.setId(row.getId());
            movieDetailPopularEntity.setTitle(row.getTitle());
            movieDetailPopularEntity.setOriginal_title(row.getOriginal_title());
            movieDetailPopularEntity.setPoster_path(row.getPoster_path());
            movieDetailPopularEntity.setAdult(row.isAdult());
            movieDetailPopularEntity.setBackdrop_path(row.getBackdrop_path());
            //movieDetailPopularEntity.setGenre_ids(row.getGenre_ids());
            movieDetailPopularEntity.setPopularity(row.getPopularity());

            movieDetailPopularEntities.add(movieDetailPopularEntity);
        }
        return this.moviePopularDataSourceDatabase.insertCompletableMoviePopular(movieDetailPopularEntities);
    }

    @Override
    public Observable<MoviePopular> getObservableMoviePopularMemory() {
        return this.moviePopularDataSourceMemory.getObservableMoviePopular();
    }

    @Override
    public Completable insertCompletableMoviePopularMemory(MovieDetailPopular movieDetailPopular) {
        return null;
    }


    @Override
    public Single<MoviePopular> getSingleMoviePopularMemory() {
        return this.moviePopularDataSourceMemory.getSingleMoviePopular();
    }
}
