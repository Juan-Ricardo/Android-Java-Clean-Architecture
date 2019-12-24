package com.ricardorc.datasource.persistance.database.room.mapper;

import com.ricardorc.datasource.mapper.Mapper;
import com.ricardorc.datasource.persistance.database.room.entity.MovieDetailPopularEntity;
import com.ricardorc.domain.MovieDetailPopular;
import com.ricardorc.domain.MoviePopular;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailPopularEntityMapper implements Mapper<List<MovieDetailPopularEntity>, MoviePopular> {
    @Override
    public MoviePopular convert(List<MovieDetailPopularEntity> response) {
        MoviePopular moviePopularModel = new MoviePopular();
        List<MovieDetailPopular> movieDetailPopularModels = new ArrayList<>();

        for (MovieDetailPopularEntity row : response) {

            MovieDetailPopular movieDetailPopular = new MovieDetailPopular();

            movieDetailPopular.setId(row.getId());
            movieDetailPopular.setTitle(row.getTitle());
            movieDetailPopular.setOriginal_title(row.getOriginal_title());
            movieDetailPopular.setPoster_path(row.getPoster_path());
            movieDetailPopular.setAdult(row.isAdult());
            movieDetailPopular.setBackdrop_path(row.getBackdrop_path());
            //movieDetailPopular.setGenre_ids(row.getGenre_ids());
            movieDetailPopular.setPopularity(row.getPopularity());

            movieDetailPopularModels.add(movieDetailPopular);
        }
        moviePopularModel.setResults(movieDetailPopularModels);
        return moviePopularModel;
    }
}
