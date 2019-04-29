package com.atahar.fmmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.atahar.fmmanager.adapter.TopAlbumAdapter;
import com.atahar.fmmanager.model.Album;
import com.atahar.fmmanager.model.AlbumsList;
import com.atahar.fmmanager.model.FavoriteAlbum;
import com.atahar.fmmanager.network.ApiClient;
import com.atahar.fmmanager.utils.Constants;
import com.atahar.fmmanager.utils.HelperFunctions;

import java.util.List;

public class TopAlbumActivity extends AppCompatActivity {

    private String TAG = "TopAlbumActivity";

    private RecyclerView topAlbumRV;
    private TopAlbumAdapter topAlbumAdapter;
    private List<Album> albumList;
    private TextView artistNameTV;
    private FrameLayout backFL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_album);

        // Check for Internet connection
        HelperFunctions.checkInternetConnection(TopAlbumActivity.this);

        initView();
        performServerRequest();
    }

    private void initView() {

        backFL = findViewById(R.id.back_FL);
        backFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopAlbumActivity.this.finish();
            }
        });

        artistNameTV = findViewById(R.id.artist_name_TV);
        artistNameTV.setText(getIntent().getStringExtra(Constants.ARTIST_NAME));

        topAlbumRV = findViewById(R.id.top_album_RV);
        topAlbumRV.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

    }

    private void performServerRequest() {
        ApiClient apiClient = new ApiClient();
        apiClient.getArtistTopAlbums(getIntent().getStringExtra(Constants.ARTIST_NAME));
        apiClient.setServerResponseListener(new ApiClient.ServerResponseListener() {
            @Override
            public void onResponseReceived(Object response) {
                if (response instanceof AlbumsList) {
                    albumList = ((AlbumsList) response).getTopalbums().getAlbum();
                    processAlbumList();

                    topAlbumAdapter = new TopAlbumAdapter(TopAlbumActivity.this, albumList);
                    topAlbumRV.setAdapter(topAlbumAdapter);
                }
            }
        });
    }


    private void processAlbumList() {

        for (Album album : albumList)
            album.setIsSaved(false);

        List<FavoriteAlbum> favoriteAlbums = HelperFunctions.favAlbumsMap.get(HelperFunctions.FAV_ALBUM_MAP_KEY);

        if (favoriteAlbums == null) return;

        for (Album album : albumList) {
            for (FavoriteAlbum favoriteAlbum : favoriteAlbums) {
                if (favoriteAlbum == null || album == null) {
                    continue;
                }

                if (album.getName().equalsIgnoreCase(favoriteAlbum.getName())) {
                    album.setIsSaved(true);
                }
            }
        }
    }
}
