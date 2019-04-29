package com.atahar.fmmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtistsList {
    @SerializedName("results")
    @Expose
    private ArtistResults results;

    public ArtistResults getResults() {
        return results;
    }

    public void setResults(ArtistResults results) {
        this.results = results;
    }
}

