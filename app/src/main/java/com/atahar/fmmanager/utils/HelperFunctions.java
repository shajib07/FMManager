package com.atahar.fmmanager.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.atahar.fmmanager.MainActivity;
import com.atahar.fmmanager.R;
import com.atahar.fmmanager.model.Album;
import com.atahar.fmmanager.model.FavoriteAlbum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelperFunctions {

    public static String FAV_ALBUM_MAP_KEY = "FAV_ALBUM_MAP_KEY";

    public static HashMap<String, List<FavoriteAlbum>> favAlbumsMap = new HashMap<>();

    public static void initFavAlbumsMap() {
        ArrayList<FavoriteAlbum> favoriteAlbums = new ArrayList<>();
        favAlbumsMap.put(HelperFunctions.FAV_ALBUM_MAP_KEY, favoriteAlbums);
    }

    public static void addFavouriteAlbum(FavoriteAlbum album) {
        List<FavoriteAlbum> favoriteAlbums = favAlbumsMap.get(FAV_ALBUM_MAP_KEY);
        favoriteAlbums.add(album);
        favAlbumsMap.remove(FAV_ALBUM_MAP_KEY);
        favAlbumsMap.put(FAV_ALBUM_MAP_KEY, favoriteAlbums);
    }

    public static void removeFavouriteAlbum(FavoriteAlbum album) {

        FavoriteAlbum removeAlbum = new FavoriteAlbum();
        boolean isFound = false;
        List<FavoriteAlbum> favoriteAlbums = favAlbumsMap.get(FAV_ALBUM_MAP_KEY);
        for (FavoriteAlbum favoriteAlbum : favoriteAlbums) {
            if (favoriteAlbum.getName().equalsIgnoreCase(album.getName())) {
                isFound = true;
                removeAlbum = favoriteAlbum;
            }
        }

        if (isFound) {
            favoriteAlbums.remove(removeAlbum);
        }
    }

    public static FavoriteAlbum getFavouriteAlbum(Album album) {
        FavoriteAlbum favoriteAlbum = new FavoriteAlbum();
        favoriteAlbum.setName(album.getName());
        favoriteAlbum.setArtist(album.getArtist().getName());
        favoriteAlbum.setMbid(album.getMbid());
        favoriteAlbum.setUrl(album.getImage().get(2).getText());
        return favoriteAlbum;
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static boolean isConnectedtoInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void checkInternetConnection(Context context) {
        if (!HelperFunctions.isConnectedtoInternet(context)) {
            Toast.makeText(context, context.getString(R.string.network_check),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
