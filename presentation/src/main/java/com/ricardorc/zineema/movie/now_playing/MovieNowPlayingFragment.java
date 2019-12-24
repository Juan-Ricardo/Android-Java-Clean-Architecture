package com.ricardorc.zineema.movie.now_playing;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ricardorc.zineema.R;

public class MovieNowPlayingFragment extends Fragment {

    private Toolbar toolbar;

    public MovieNowPlayingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_now_playing, container, false);
        finds(view);
        return view;
    }

    private void finds(View view) {
        setHasOptionsMenu(true);
        setupToolbar(view, getString(R.string.now_playing_movie), getString(R.string.empty), true);
    }

    private void setupToolbar(View view, String title, String subTitle, Boolean upButton) {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
