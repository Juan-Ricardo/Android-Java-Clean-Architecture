package com.ricardorc.zineema.movie.popular.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ricardorc.domain.MovieDetailPopular;
import com.ricardorc.zineema.R;

import java.util.LinkedList;
import java.util.List;

public class MoviePopularRecyclerAdapter extends
        RecyclerView.Adapter<MoviePopularRecyclerAdapter.MoviePopularViewHolder> implements
        Filterable {

    private List<MovieDetailPopular> movieDetailPopularModels;
    private List<MovieDetailPopular> filterMovieDetailPopularModels;
    private Activity activity;
    private int resource;

    public MoviePopularRecyclerAdapter(List<MovieDetailPopular> movieDetailPopularModels, Activity activity, int resource) {
        this.movieDetailPopularModels = movieDetailPopularModels;
        this.filterMovieDetailPopularModels = movieDetailPopularModels;
        this.activity = activity;
        this.resource = resource;
    }


    @NonNull
    @Override
    public MoviePopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(activity.getBaseContext())
                .inflate(resource, parent, false);
        return new MoviePopularViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePopularViewHolder holder, int position) {
        MovieDetailPopular movie = movieDetailPopularModels.get(position);
        holder.title.setText(movie.getTitle());
        Glide.with(activity).load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return movieDetailPopularModels.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence inputText) {
                FilterResults filterResults = new FilterResults();
                if (inputText != null && inputText.length() > 0) {
                    String query = inputText.toString().toLowerCase();
                    List<MovieDetailPopular> filters = new LinkedList<>();
                    for (MovieDetailPopular item : filterMovieDetailPopularModels) {
                        if (item.getTitle().toLowerCase().contains(query) ||
                                item.getOriginal_title().toLowerCase().contains(query)) {
                            filters.add(item);
                        }
                    }
                    filterResults.count = filters.size();
                    filterResults.values = filters;
                } else {
                    filterResults.count = filterMovieDetailPopularModels.size();
                    filterResults.values = filterMovieDetailPopularModels;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                movieDetailPopularModels = (List<MovieDetailPopular>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MoviePopularViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MoviePopularViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }
}
