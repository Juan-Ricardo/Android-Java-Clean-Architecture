package com.ricardorc.datasource.persistance.database.room.entity;

import com.ricardorc.datasource.persistance.database.room.entity.MovieDetailPopularEntity;

import java.util.List;

public class MoviePopularEntity {
    private List<MovieDetailPopularEntity> results;

    public MoviePopularEntity() {

    }

    public List<MovieDetailPopularEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieDetailPopularEntity> results) {
        this.results = results;
    }
}
