package com.atahar.fmmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.atahar.fmmanager.model.FavoriteAlbum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferenceClient {

    public static void saveMap(Context context, String key, List<FavoriteAlbum> album){
        SharedPreferences pSharedPref = context.getSharedPreferences(Constants.FAV_ALBUMS_STORAGE_KEY,
                Context.MODE_PRIVATE);

        if (pSharedPref != null) {
            Gson jsonObject = new Gson();
            String jsonString = jsonObject.toJson(album);
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove(key).commit();
            editor.putString(key, jsonString);
            editor.commit();
        }
    }

    public static List<FavoriteAlbum> getFavouriteAlbums(Context context, String key){
        List<FavoriteAlbum> albums = new ArrayList<>();
        SharedPreferences pSharedPref = context.getSharedPreferences(Constants.FAV_ALBUMS_STORAGE_KEY,
                Context.MODE_PRIVATE);

        try{
            if (pSharedPref != null){
                String jsonString = pSharedPref.getString(key, (new JSONObject()).toString());
                Gson gsonObject = new Gson();

                albums = new ArrayList<>();
                Type type = new TypeToken<List<FavoriteAlbum>>() {}.getType();
                albums = gsonObject.fromJson(jsonString, type);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return albums;
    }
}
