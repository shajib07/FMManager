package com.atahar.fmmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.atahar.fmmanager.adapter.ArtistAdapter;
import com.atahar.fmmanager.model.Artist;
import com.atahar.fmmanager.model.ArtistsList;
import com.atahar.fmmanager.network.ApiClient;
import com.atahar.fmmanager.utils.Constants;
import com.atahar.fmmanager.utils.HelperFunctions;

import java.util.List;

public class ArtistSearchActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "ArtistSearchActivity";
    private EditText searchET;
    private FrameLayout sendFL, backFL;
    private List<Artist> artistsList;
    private RecyclerView artistSearchRV;
    private ArtistAdapter artistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_search);

        // Check for Internet connection
        HelperFunctions.checkInternetConnection(ArtistSearchActivity.this);

        initView();
    }

    private void initView() {
        backFL = findViewById(R.id.back_FL);
        backFL.setOnClickListener(this);

        sendFL = findViewById(R.id.send_FL);
        sendFL.setOnClickListener(this);

        searchET = findViewById(R.id.search_ET);
        searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(searchET.getText().toString());
                    return true;
                }
                return false;
            }
        });

        artistSearchRV = findViewById(R.id.artist_search_RV);
        artistSearchRV.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

    }

    private void performSearch(String searchTxt) {

        if (searchTxt.length() == 0) {
            HelperFunctions.hideKeyboard(ArtistSearchActivity.this);
            return;
        }

        ApiClient apiClient = new ApiClient();
        apiClient.getArtist(searchTxt, Constants.LIMIT);
        apiClient.setServerResponseListener(new ApiClient.ServerResponseListener() {
            @Override
            public void onResponseReceived(Object response) {
                if (response instanceof ArtistsList) {
                    artistsList = ((ArtistsList) response).getResults().getArtistmatchs().getArtist();

                    artistAdapter = new ArtistAdapter(ArtistSearchActivity.this, artistsList);
                    artistSearchRV.setAdapter(artistAdapter);
                }
            }
        });

        HelperFunctions.hideKeyboard(ArtistSearchActivity.this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_FL:
                performSearch(searchET.getText().toString());
                break;

            case R.id.back_FL:
                ArtistSearchActivity.this.finish();
                break;
        }
    }
}
