package com.atahar.fmmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlbumMatch {
    @SerializedName("album")
    @Expose
    private FavoriteAlbum album;

    public FavoriteAlbum getAlbum() {
        return album;
    }

    public void setAlbum(FavoriteAlbum album) {
        this.album = album;
    }
}
