package com.ricardorc.usecase;

import android.content.Context;

import com.ricardorc.datasource.repository.MoviePopularRepository;
import com.ricardorc.domain.MovieDetailPopular;
import com.ricardorc.usecase.reactive.CompletableUseCase;
import com.ricardorc.usecase.typesource.TypeDataSource;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class InsertMoviePopularSingleUseCase
        extends CompletableUseCase<Long, InsertMoviePopularSingleUseCase.Params>
        implements TypeDataSource {
    private MoviePopularRepository moviePopularRepository;
    private Context context;

    public InsertMoviePopularSingleUseCase(Scheduler executorThread,
                                           Scheduler uiThread,
                                           Context context,
                                           MoviePopularRepository moviePopularRepository) {
        super(executorThread, uiThread);
        this.context = context;
        this.moviePopularRepository = moviePopularRepository;
    }

    @Override
    protected Completable buildUseCase(Params params) {
        if (params.typeDataSource == NETWORK_DATA_SOURCE) {
            return this.moviePopularRepository.insertCompletableMoviePopularNetwork(
                    null);
        } else if (params.typeDataSource == DATABASE_DATA_SOURCE) {
            return this.moviePopularRepository.insertCompletableMoviePopularDatabase(
                    params.movieDetailPopulars);
        } else {
            return this.moviePopularRepository.insertCompletableMoviePopularMemory(
                    null);
        }
    }

    public static final class Params {

        private final int typeDataSource;
        private final MovieDetailPopular movieDetailPopular;
        private final List<MovieDetailPopular> movieDetailPopulars;

        private Params(int typeDataSource, MovieDetailPopular movieDetailPopular) {
            this.typeDataSource = typeDataSource;
            this.movieDetailPopular = movieDetailPopular;
            this.movieDetailPopulars = new LinkedList<>();
        }

        private Params(int typeDataSource, List<MovieDetailPopular> movieDetailPopulars) {
            this.typeDataSource = typeDataSource;
            this.movieDetailPopular = new MovieDetailPopular();
            this.movieDetailPopulars = movieDetailPopulars;
        }

        public static Params params(int typeDataSource, MovieDetailPopular movieDetailPopular) {
            return new Params(typeDataSource, movieDetailPopular);
        }

        public static Params params(int typeDataSource, List<MovieDetailPopular> movieDetailPopulars) {
            return new Params(typeDataSource, movieDetailPopulars);
        }
    }
}
