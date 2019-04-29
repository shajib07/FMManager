package com.atahar.fmmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atahar.fmmanager.adapter.TracksAdapter;
import com.atahar.fmmanager.model.AlbumMatch;
import com.atahar.fmmanager.model.FavoriteAlbum;
import com.atahar.fmmanager.model.Track;
import com.atahar.fmmanager.network.ApiClient;
import com.atahar.fmmanager.utils.Constants;
import com.atahar.fmmanager.utils.HelperFunctions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class AlbumDetailsActivity extends AppCompatActivity {

    private String TAG = "AlbumDetailsActivity";
    private FavoriteAlbum mAlbum;
    private ImageView coverIV;
    private TextView albumTV, artistTV;
    private List<Track> mTracks;
    private RecyclerView tracksRV;
    private TracksAdapter tracksAdapter;

    private LinearLayout noTracksLL, bodyLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);

        // Check for Internet connection
        HelperFunctions.checkInternetConnection(AlbumDetailsActivity.this);

        String album = getIntent().getStringExtra(Constants.ALBUM_NAME);
        String artist = getIntent().getStringExtra(Constants.ARTIST_NAME);
        fetchAlbumDetails(artist, album);
    }

    private void initView() {
        noTracksLL = findViewById(R.id.no_tracks_LL);
        bodyLL = findViewById(R.id.body_LL);

        albumTV = findViewById(R.id.album_name_TV);
        artistTV = findViewById(R.id.artist_name_TV);

        albumTV.setText(mAlbum.getName());
        artistTV.setText(mAlbum.getArtist());

        mTracks = mAlbum.getTracks().getTrack();

        if (mTracks.size() == 0) {
            bodyLL.setVisibility(View.GONE);
            noTracksLL.setVisibility(View.VISIBLE);
        }

        coverIV = findViewById(R.id.cover_IV);

        Glide.with(this)
                .load(mAlbum.getImage().get(2).getText())
                .apply(RequestOptions.circleCropTransform())
                .into(coverIV);

        tracksRV = findViewById(R.id.tracks_RV);
        tracksRV.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        tracksRV.setHasFixedSize(true);

        tracksAdapter = new TracksAdapter(AlbumDetailsActivity.this, mTracks);
        tracksRV.setAdapter(tracksAdapter);
    }

    private void fetchAlbumDetails(String artist, String album) {

        ApiClient apiClient = new ApiClient();
        apiClient.getAlbumDetails(artist, album);
        apiClient.setServerResponseListener(new ApiClient.ServerResponseListener() {
            @Override
            public void onResponseReceived(Object response) {
                mAlbum = ((AlbumMatch) response).getAlbum();
                initView();
            }
        });
    }
}
