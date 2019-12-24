package com.ricardorc.datasource.repository.mapper;

import com.ricardorc.datasource.mapper.Mapper;
import com.ricardorc.datasource.persistance.network.response.MovieDetailPopularResponse;
import com.ricardorc.datasource.persistance.network.response.MoviePopularResponse;
import com.ricardorc.domain.MovieDetailPopular;
import com.ricardorc.domain.MoviePopular;

import java.util.ArrayList;
import java.util.List;

public class GetMoviePopularMapper implements Mapper<MoviePopularResponse, MoviePopular> {
    @Override
    public MoviePopular convert(MoviePopularResponse response) {
        MoviePopular moviePopularModel = new MoviePopular();
        List<MovieDetailPopular> movieDetailPopularModels = new ArrayList<>();

        for (MovieDetailPopularResponse row : response.getResults()) {

            MovieDetailPopular movieDetailPopular = new MovieDetailPopular();

            movieDetailPopular.setId(row.getId());
            movieDetailPopular.setTitle(row.getTitle());
            movieDetailPopular.setOriginal_title(row.getOriginal_title());
            movieDetailPopular.setPoster_path(row.getPoster_path());
            movieDetailPopular.setAdult(row.isAdult());
            movieDetailPopular.setBackdrop_path(row.getBackdrop_path());
            movieDetailPopular.setGenre_ids(row.getGenre_ids());
            movieDetailPopular.setPopularity(row.getPopularity());

            movieDetailPopularModels.add(movieDetailPopular);
        }
        moviePopularModel.setResults(movieDetailPopularModels);
        return moviePopularModel;
    }
}
