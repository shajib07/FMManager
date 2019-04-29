package com.atahar.fmmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AlbumsList {
    @SerializedName("topalbums")
    @Expose
    private Topalbums topalbums;

    public Topalbums getTopalbums() {
        return topalbums;
    }

    public void setTopalbums(Topalbums topalbums) {
        this.topalbums = topalbums;
    }
}
