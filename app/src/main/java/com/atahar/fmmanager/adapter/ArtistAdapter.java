package com.atahar.fmmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atahar.fmmanager.R;
import com.atahar.fmmanager.TopAlbumActivity;
import com.atahar.fmmanager.model.Artist;
import com.atahar.fmmanager.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG;
    private List<Artist> artistList;
    private Context mContext;

    public ArtistAdapter(Context context, List<Artist> artistList) {
        this.mContext = context;
        this.artistList = artistList;
        TAG = "ArtistAdapter";
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artist_single_item,
                viewGroup, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final Artist artist = artistList.get(position);

        final ArtistAdapter.ArtistViewHolder artistViewHolder = (ArtistAdapter.ArtistViewHolder) viewHolder;

        artistViewHolder.artistNameTV.setText(artist.getName());

        if(!artist.getImage().get(2).getText().isEmpty()) {
            Glide.with(artistViewHolder.artistNameTV.getContext())
                    .load(artist.getImage().get(2).getText())
                    .into(artistViewHolder.artistIV);
        }

        artistViewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TopAlbumActivity.class);
                intent.putExtra(Constants.ARTIST_NAME, artist.getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistList == null ? 0 : artistList.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout root;
        ImageView artistIV;
        TextView artistNameTV;

        ArtistViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            artistNameTV = itemView.findViewById(R.id.artist_name_TV);
            artistIV = itemView.findViewById(R.id.artist_IV);
        }
    }
}
