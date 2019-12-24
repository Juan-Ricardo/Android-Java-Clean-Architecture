package com.ricardorc.zineema.mainmenu;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.ricardorc.zineema.R;
import com.ricardorc.zineema.movie.now_playing.MovieNowPlayingFragment;
import com.ricardorc.zineema.movie.popular.MoviePopularFragment;
import com.ricardorc.zineema.movie.today.MovieTodayFragment;


public class MainMenuActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        finds();
    }

    private void finds() {
        setupViews();
    }

    private void setupViews() {
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.ic_today:
                                FragmentTransaction todayFragmentTransaction = getSupportFragmentManager().beginTransaction();
                                todayFragmentTransaction.replace(R.id.content, new MovieTodayFragment()).commit();
                                break;
                            case R.id.ic_popular:
                                FragmentTransaction moviePopularFragmentTransaction = getSupportFragmentManager().beginTransaction();
                                moviePopularFragmentTransaction.replace(R.id.content, new MoviePopularFragment()).commit();
                                break;
                            case R.id.ic_now_playing:
                                FragmentTransaction nowPlayingFragmentTransaction = getSupportFragmentManager().beginTransaction();
                                nowPlayingFragmentTransaction.replace(R.id.content, new MovieNowPlayingFragment()).commit();
                                break;
                        }
                        return true;
                    }
                });
        bottomNavigationView.setSelectedItemId(R.id.ic_popular);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_SELECTED);

    }
}
