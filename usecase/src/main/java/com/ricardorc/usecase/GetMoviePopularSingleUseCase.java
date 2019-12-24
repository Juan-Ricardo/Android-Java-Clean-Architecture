package com.ricardorc.usecase;

import android.content.Context;

import com.ricardorc.datasource.repository.MoviePopularRepository;
import com.ricardorc.domain.MoviePopular;
import com.ricardorc.usecase.reactive.SingleUseCase;
import com.ricardorc.usecase.typesource.TypeDataSource;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetMoviePopularSingleUseCase
        extends SingleUseCase<MoviePopular, GetMoviePopularSingleUseCase.Params>
        implements TypeDataSource {
    private MoviePopularRepository moviePopularRepository;
    private Context context;

    public GetMoviePopularSingleUseCase(Scheduler executorThread,
                                        Scheduler postExecutionThread,
                                        Context context,
                                        MoviePopularRepository moviePopularRepository) {
        super(executorThread, postExecutionThread);
        this.context = context;
        this.moviePopularRepository = moviePopularRepository;
    }

    @Override
    protected Single<MoviePopular> buildUseCase(Params params) {
        if (params.typeDataSource == NETWORK_DATA_SOURCE) {
            return this.moviePopularRepository.getSingleMoviePopularNetwork();
        } else if (params.typeDataSource == DATABASE_DATA_SOURCE) {
            return this.moviePopularRepository.getSingleMoviePopularDatabase();
        } else {
            return this.moviePopularRepository.getSingleMoviePopularMemory();
        }
    }

    public static final class Params {

        private final int typeDataSource;

        private Params(int typeDataSource) {
            this.typeDataSource = typeDataSource;
        }

        public static Params params(int typeDataSource) {
            return new Params(typeDataSource);
        }
    }
}
