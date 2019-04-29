package com.atahar.fmmanager.network;

import android.util.Log;

import com.atahar.fmmanager.model.AlbumMatch;
import com.atahar.fmmanager.model.AlbumsList;
import com.atahar.fmmanager.model.ArtistsList;
import com.atahar.fmmanager.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    private static Retrofit retrofit = null;
    private static String TAG = "ApiClient";

    public void setServerResponseListener(ServerResponseListener serverResponseListener) {
        this.serverResponseListener = serverResponseListener;
    }

    ServerResponseListener serverResponseListener;

    public interface ServerResponseListener {
        void onResponseReceived(Object response);
    }

    private static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void getAlbumDetails(String artistName, final String albumName) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<AlbumMatch> call = apiService.getAlbumDetails(artistName, albumName, Constants.API_KEY);
        call.enqueue(new Callback<AlbumMatch>() {
            @Override
            public void onResponse(Call<AlbumMatch> call, Response<AlbumMatch> response) {
                AlbumMatch albumMatch = response.body();
                serverResponseListener.onResponseReceived(response.body());
            }

            @Override
            public void onFailure(Call<AlbumMatch> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

    public void getArtistTopAlbums(String artistName) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<AlbumsList> call = apiService.getTopAlbums(artistName, Constants.API_KEY);
        call.enqueue(new Callback<AlbumsList>() {
            @Override
            public void onResponse(Call<AlbumsList> call, Response<AlbumsList> response) {
                AlbumsList albumsList = response.body();
                serverResponseListener.onResponseReceived(response.body());
            }

            @Override
            public void onFailure(Call<AlbumsList> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

    public void getArtist(String searchTxt, int limit) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ArtistsList> call = apiService.getArtist(searchTxt, Constants.LIMIT, Constants.API_KEY);
        call.enqueue(new Callback<ArtistsList>() {
            @Override
            public void onResponse(Call<ArtistsList> call, Response<ArtistsList> response) {
                ArtistsList artistsList = response.body();
                serverResponseListener.onResponseReceived(response.body());
            }

            @Override
            public void onFailure(Call<ArtistsList> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

}
