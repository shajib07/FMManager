package com.atahar.fmmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.atahar.fmmanager.adapter.FavouriteAlbumAdapter;
import com.atahar.fmmanager.model.FavoriteAlbum;
import com.atahar.fmmanager.utils.Constants;
import com.atahar.fmmanager.utils.HelperFunctions;
import com.atahar.fmmanager.utils.SharedPreferenceClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "MainActivity";
    public FrameLayout searchFL;
    private RecyclerView favAlbumRV;
    private FavouriteAlbumAdapter favAlbumAdapter;
    private List<FavoriteAlbum> albumList;

    private boolean dataSourceMap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for Internet connection
        HelperFunctions.checkInternetConnection(MainActivity.this);
        HelperFunctions.initFavAlbumsMap();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<FavoriteAlbum> favoriteAlbums;
        if (!dataSourceMap) {
            favoriteAlbums = SharedPreferenceClient.getFavouriteAlbums(MainActivity.this, Constants.FAV_ALBUMS_STORAGE_KEY);
            HelperFunctions.favAlbumsMap.put(HelperFunctions.FAV_ALBUM_MAP_KEY, favoriteAlbums);
        } else {
            favoriteAlbums = HelperFunctions.favAlbumsMap.get(HelperFunctions.FAV_ALBUM_MAP_KEY);
        }
        dataSourceMap = true;

        if (favoriteAlbums != null) {
            albumList.clear();
            albumList.addAll(favoriteAlbums);
            favAlbumAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferenceClient.saveMap(MainActivity.this, Constants.FAV_ALBUMS_STORAGE_KEY,
                albumList);
    }

    @Override
    protected void onDestroy() {
        SharedPreferenceClient.saveMap(MainActivity.this, Constants.FAV_ALBUMS_STORAGE_KEY,
                albumList);
        super.onDestroy();

    }

    private void initView() {
        searchFL = findViewById(R.id.search_FL);
        searchFL.setOnClickListener(this);

        favAlbumRV = findViewById(R.id.fav_album_RV);
        favAlbumRV.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        if (albumList == null)
            albumList = new ArrayList<>();

        favAlbumAdapter = new FavouriteAlbumAdapter(MainActivity.this, albumList);
        favAlbumRV.setAdapter(favAlbumAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.search_FL:
                Intent intent = new Intent(this, ArtistSearchActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
