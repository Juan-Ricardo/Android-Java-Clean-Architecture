package com.ricardorc.domain;

import java.util.List;

public class MoviePopular {
    private List<MovieDetailPopular> results;

    public MoviePopular() {
    }

    public List<MovieDetailPopular> getResults() {
        return results;
    }

    public void setResults(List<MovieDetailPopular> results) {
        this.results = results;
    }
}
