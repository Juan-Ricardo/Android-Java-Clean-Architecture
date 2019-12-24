package com.ricardorc.zineema.movie.popular;

import android.content.Context;

import com.ricardorc.domain.MovieDetailPopular;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public interface MoviePopularContract {
    interface View {

        void showMoviePopulars(List<MovieDetailPopular> movieDetailPopularModels);

        void showLoading();

        void hideLoading();

        void hideAlertDialog();

        void showAlertDialog(int icon, String title, String subTitle);

        void showError(String error);

        void showSuccess(String success);

        Scheduler executorThread();

        Scheduler uiThread();

        void youHaveNoInternetConnection(String title, String subTitle);
    }

    interface Presenter {

        void getMoviePopularNetwork(Context context, boolean offline);

        void getMoviePopularDatabase(Context context);

        void insertMoviePopularDatabase(Context context, MovieDetailPopular movieDetailPopular);

        void insertMoviePopularDatabase(Context context, List<MovieDetailPopular> movieDetailPopulars);

        void getMoviePopularMemory(Context context);

        void getMovieNowPlayingNetWork(Context context);

        boolean isOnline();

        void onStop();
    }
}
