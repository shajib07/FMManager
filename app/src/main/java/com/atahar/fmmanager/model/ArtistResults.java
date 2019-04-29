package com.atahar.fmmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtistResults {
    @SerializedName("artistmatches")
    @Expose
    private ArtistMatches artistmatchs;

    public ArtistMatches getArtistmatchs() {
        return artistmatchs;
    }

    public void setArtistmatchs(ArtistMatches artistmatchs) {
        this.artistmatchs = artistmatchs;
    }
}
