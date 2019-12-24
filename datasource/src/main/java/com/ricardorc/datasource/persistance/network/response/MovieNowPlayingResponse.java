package com.ricardorc.datasource.persistance.network.response;

import java.util.List;

public class MovieNowPlayingResponse {
    private List<MovieDetailPopularResponse> results;

    public MovieNowPlayingResponse() {

    }

    public List<MovieDetailPopularResponse> getResults() {
        return results;
    }

    public void setResults(List<MovieDetailPopularResponse> results) {
        this.results = results;
    }
}
