package com.ricardorc.datasource.repository.mapper;

import com.ricardorc.datasource.mapper.Mapper;
import com.ricardorc.datasource.persistance.database.room.entity.MovieDetailPopularEntity;
import com.ricardorc.domain.MovieDetailPopular;

public class InsertMoviePopularMapper implements Mapper<MovieDetailPopular, MovieDetailPopularEntity> {
    @Override
    public MovieDetailPopularEntity convert(MovieDetailPopular fields) {
        MovieDetailPopularEntity movieDetailPopularEntity = new MovieDetailPopularEntity();

        movieDetailPopularEntity.setId(fields.getId());
        movieDetailPopularEntity.setTitle(fields.getTitle());
        movieDetailPopularEntity.setOriginal_title(fields.getOriginal_title());
        movieDetailPopularEntity.setPoster_path(fields.getPoster_path());
        movieDetailPopularEntity.setAdult(fields.isAdult());
        movieDetailPopularEntity.setBackdrop_path(fields.getBackdrop_path());
        //movieDetailPopularEntity.setGenre_ids(fields.getGenre_ids());
        movieDetailPopularEntity.setPopularity(fields.getPopularity());

        return movieDetailPopularEntity;
    }
}
