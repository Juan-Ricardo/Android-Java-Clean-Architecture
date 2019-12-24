package com.ricardorc.zineema.movie.popular;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.ricardorc.datasource.persistance.memory.data.MoviePopularDataSourceMemory;
import com.ricardorc.datasource.persistance.database.data.MoviePopularDataSourceDatabase;
import com.ricardorc.datasource.persistance.network.data.MoviePopularDataSourceNetwork;
import com.ricardorc.datasource.repository.MoviePopularRepository;
import com.ricardorc.datasource.repository.data.MoviePopularDataRepository;
import com.ricardorc.domain.MovieDetailPopular;
import com.ricardorc.resource.loading.Loading;
import com.ricardorc.zineema.R;
import com.ricardorc.zineema.movie.popular.adapter.MoviePopularRecyclerAdapter;

import java.lang.reflect.Field;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MoviePopularFragment extends Fragment implements MoviePopularContract.View {

    private MoviePopularContract.Presenter presenter;
    private MoviePopularRepository moviePopularRepository;
    private RecyclerView movieRecyclerView;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private MaterialSearchView searchView;
    private MoviePopularRecyclerAdapter adapter;
    private Loading loading;
    private BottomNavigationView bottomNavigationView;
    private View view;
    private Boolean FALSE = false;
    private Boolean TRUE = true;

    public MoviePopularFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_movie_popular, container, FALSE);
        finds(view);
        return view;
    }

    private void finds(View view) {
        setHasOptionsMenu(true);
        coordinatorLayout = view.findViewById(R.id.coordinator_layout);
        setupToolbar(view, getString(R.string.popular_movie), getString(R.string.empty), TRUE);
        setupRecyclerView();
        loading = view.findViewById(R.id.movie_popular_loading);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation_view);
    }

    private void setupRecyclerView() {
        movieRecyclerView = view.findViewById(R.id.movie_recycler_view);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        setupSearchView(view);
    }

    private void setupToolbar(View view, String title, String subTitle, Boolean upButton) {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    private void setupSearchView(View view) {
        searchView = view.findViewById(R.id.search);
        searchView.setHintTextColor(getResources().getColor(R.color.search));
        searchView.setHint(getString(R.string.hint_search_toolbar));
        searchView.setVoiceIcon(getResources().getDrawable(R.drawable.ic_today));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return FALSE;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (adapter != null)
                    adapter.getFilter().filter(query);
                return TRUE;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_movie, menu);
        MenuItem item = menu.findItem(R.id.ic_action_search);
        searchView.setMenuItem(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        bottomNavigationViewMenuStateEnabled(FALSE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMovies();
    }

    private void getMovies() {

        this.moviePopularRepository = new MoviePopularDataRepository(
                new MoviePopularDataSourceNetwork(getContext()),
                new MoviePopularDataSourceDatabase(getContext()),
                new MoviePopularDataSourceMemory()
        );

        this.presenter = new MoviePopularPresenter(this,
                getContext(),
                executorThread(),
                uiThread(),
                this.moviePopularRepository);

        this.presenter.getMoviePopularNetwork(getContext(), true);
    }

    @Override
    public void showMoviePopulars(List<MovieDetailPopular> movieDetailPopularModels) {
        adapter = new MoviePopularRecyclerAdapter(movieDetailPopularModels, getActivity(), R.layout.movie_card_view);
        movieRecyclerView.setVisibility(View.VISIBLE);
        movieRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoading() {
        loading.setTextSizeMessage(16f);
        loading.show("Cargando películas populares");
    }

    @Override
    public void hideLoading() {
        loading.dismiss();
        bottomNavigationViewMenuStateEnabled(TRUE);
    }

    @Override
    public void hideAlertDialog() {

    }

    @Override
    public void showAlertDialog(int iconParam, String titleParam, String subTitleParam) {
        builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.no_internet_connection_alert_dialog,
                null);
        builder.setView(view);
        builder.setCancelable(FALSE);

        alertDialog = builder.create();
        alertDialog.show();

        ImageView icon = view.findViewById(R.id.icon_image_view);
        icon.setImageResource(iconParam);
        TextView title = view.findViewById(R.id.title_text_view);
        title.setText(titleParam);
        TextView subTitle = view.findViewById(R.id.sub_title_text_view);
        subTitle.setText(subTitleParam);
        Button accept = view.findViewById(R.id.accept_button);
        Button cancel = view.findViewById(R.id.cancel_button);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                getMovies();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void showError(String error) {
        Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getMovies();
                    }
                })
                .show();
    }

    @Override
    public void showSuccess(String success) {
        Snackbar.make(coordinatorLayout, success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Scheduler executorThread() {
        return Schedulers.io();
    }

    @Override
    public Scheduler uiThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public void youHaveNoInternetConnection(String title, String subTitle) {
        showAlertDialog(R.drawable.ic_wifi, title, subTitle);
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    /***
     * Para habilitar o deshabilitar los menus del bottomNavigationView
     * @param state si es FALSE, esta habilitado, sino Deshabilitado.
     */
    private void bottomNavigationViewMenuStateEnabled(boolean state) {
        bottomNavigationView.getMenu().findItem(R.id.ic_popular).setEnabled(state);
    }
}
