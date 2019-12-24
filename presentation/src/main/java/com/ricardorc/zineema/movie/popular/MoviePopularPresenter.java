package com.ricardorc.zineema.movie.popular;

import android.content.Context;
import android.util.Log;

import com.ricardorc.datasource.repository.MoviePopularRepository;
import com.ricardorc.domain.MovieDetailPopular;
import com.ricardorc.domain.MoviePopular;
import com.ricardorc.usecase.GetMoviePopularSingleUseCase;
import com.ricardorc.usecase.InsertMoviePopularSingleUseCase;
import com.ricardorc.usecase.typesource.TypeDataSource;
import com.ricardorc.zineema.R;
import com.ricardorc.zineema.utilities.Utilities;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class MoviePopularPresenter implements MoviePopularContract.Presenter, TypeDataSource {

    private MoviePopularContract.View view;
    private Context context;
    private Scheduler executorThread;
    private Scheduler uiThread;
    private MoviePopularRepository moviePopularRepository;
    private GetMoviePopularSingleUseCase getMoviePopularSingleUseCase;
    private InsertMoviePopularSingleUseCase insertMoviePopularSingleUseCase;

    public MoviePopularPresenter(MoviePopularContract.View view,
                                 Context context,
                                 Scheduler executorThread,
                                 Scheduler uiThread,
                                 MoviePopularRepository moviePopularRepository) {
        this.view = view;
        this.context = context;
        this.executorThread = executorThread;
        this.uiThread = uiThread;
        this.moviePopularRepository = moviePopularRepository;

        this.getMoviePopularSingleUseCase = new GetMoviePopularSingleUseCase(this.executorThread,
                this.uiThread, this.context, this.moviePopularRepository);

        this.insertMoviePopularSingleUseCase = new InsertMoviePopularSingleUseCase(this.executorThread,
                this.uiThread, this.context, this.moviePopularRepository);
    }

    private List<MovieDetailPopular> movieDetailPopularOrder(List<MovieDetailPopular> movieDetailPopulars) {
        List<MovieDetailPopular> results;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            results = movieDetailPopulars
                    .stream()
                    .sorted(Comparator.comparingDouble(MovieDetailPopular::getPopularity)
                            .reversed())
                    .collect(Collectors.toList());
        } else {
            results = movieDetailPopulars;
        }
        return results;
    }

    @Override
    public void getMoviePopularNetwork(final Context context, boolean offline) {
        view.showLoading();
        if (isOnline()) {
            this.getMoviePopularSingleUseCase.execute(new DisposableSingleObserver<MoviePopular>() {
                @Override
                public void onSuccess(MoviePopular moviePopular) {
                    if (moviePopular != null && moviePopular.getResults().size() > 0) {
                        view.hideLoading();
                        view.showMoviePopulars(movieDetailPopularOrder(moviePopular.getResults()));
                        view.showSuccess(context.getString(R.string.popular_movie_network));

                        //Inserta lista de objetos a la Base de datos.
                        insertMoviePopularDatabase(context, moviePopular.getResults());

                    } else {
                        view.hideLoading();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    view.hideLoading();
                    view.showError(e.getMessage());
                }
            }, GetMoviePopularSingleUseCase.Params.params(NETWORK_DATA_SOURCE));
        } else {
            if (offline) {
                view.hideLoading();
                getMoviePopularDatabase(context);
            } else {
                view.hideLoading();
                view.youHaveNoInternetConnection(
                        context.getResources().getString(R.string.alert_dialog_title),
                        context.getResources().getString(R.string.alert_dialog_sub_title));
            }
        }
    }

    @Override
    public void getMoviePopularDatabase(final Context context) {
        this.getMoviePopularSingleUseCase.execute(new DisposableSingleObserver<MoviePopular>() {
            @Override
            public void onSuccess(MoviePopular moviePopular) {
                if (moviePopular != null && moviePopular.getResults().size() > 0) {
                    view.hideLoading();
                    view.showMoviePopulars(movieDetailPopularOrder(moviePopular.getResults()));
                    view.showSuccess(context.getString(R.string.you_are_in_disconnected_mode));
                } else {
                    view.hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                view.showError(e.getMessage());
            }
        }, GetMoviePopularSingleUseCase.Params.params(DATABASE_DATA_SOURCE));
    }

    @Override
    public void insertMoviePopularDatabase(final Context context, MovieDetailPopular movieDetailPopular) {
        this.insertMoviePopularSingleUseCase.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                view.hideLoading();
                //view.showSuccess(context.getString(R.string.popular_movie_insert));
            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                view.showError(e.getMessage());
            }
        }, InsertMoviePopularSingleUseCase.Params.params(DATABASE_DATA_SOURCE, movieDetailPopular));
    }

    @Override
    public void insertMoviePopularDatabase(final Context context, List<MovieDetailPopular> movieDetailPopulars) {
        this.insertMoviePopularSingleUseCase.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                view.hideLoading();
                //view.showSuccess(context.getString(R.string.popular_movie_inserts));
                //getMoviePopularDatabase(context);
            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                view.showError(e.getMessage());
            }
        }, InsertMoviePopularSingleUseCase.Params.params(DATABASE_DATA_SOURCE, movieDetailPopulars));
    }

    @Override
    public void getMoviePopularMemory(Context context) {

    }

    @Override
    public void getMovieNowPlayingNetWork(Context context) {

    }

    @Override
    public boolean isOnline() {
        if (Utilities.isOnline(context)) return true;
        else return false;
    }

    @Override
    public void onStop() {
        this.getMoviePopularSingleUseCase.dispose();
    }
}
