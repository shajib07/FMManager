package com.atahar.fmmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atahar.fmmanager.AlbumDetailsActivity;
import com.atahar.fmmanager.R;
import com.atahar.fmmanager.model.Album;
import com.atahar.fmmanager.utils.Constants;
import com.atahar.fmmanager.utils.HelperFunctions;
import com.bumptech.glide.Glide;

import java.util.List;

public class TopAlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private String TAG = "TopAlbumAdapter";
    private List<Album> topAlbumList;
    private Context mContext;

    public TopAlbumAdapter(Context context, List<Album> topAlbumList){
        this.mContext = context;
        this.topAlbumList = topAlbumList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_album_single_item,
                viewGroup, false);
        return new TopAlbumAdapter.TopAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        final Album album = topAlbumList.get(position);

        final TopAlbumAdapter.TopAlbumViewHolder topAlbumViewHolder = (TopAlbumAdapter.TopAlbumViewHolder) viewHolder;

        topAlbumViewHolder.topAlbumNameTV.setText(album.getName());

        if (album.getIsSaved()) {
            topAlbumViewHolder.saveAlbumIV.setVisibility(View.INVISIBLE);
        } else {
            topAlbumViewHolder.saveAlbumIV.setVisibility(View.VISIBLE);
        }


        if(!album.getImage().get(2).getText().isEmpty()) {
            Glide.with(mContext)
                    .load(album.getImage().get(2).getText())
                    .into(topAlbumViewHolder.topAlbumIV);
        }

        topAlbumViewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlbumDetailsActivity.class);
                intent.putExtra(Constants.ALBUM_NAME, album.getName());
                intent.putExtra(Constants.ARTIST_NAME, album.getArtist().getName());
                mContext.startActivity(intent);
            }
        });

        topAlbumViewHolder.saveAlbumIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topAlbumViewHolder.saveAlbumIV.setVisibility(View.INVISIBLE);
                HelperFunctions.addFavouriteAlbum(HelperFunctions.getFavouriteAlbum(album));
            }
        });
    }

    @Override
    public int getItemCount() {
        return topAlbumList == null ? 0 : topAlbumList.size();
    }

    public class TopAlbumViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout root;
        ImageView topAlbumIV, saveAlbumIV, deleteAlbumIV;
        TextView topAlbumNameTV;

        TopAlbumViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            topAlbumNameTV = itemView.findViewById(R.id.top_album_name_TV);
            topAlbumIV = itemView.findViewById(R.id.top_album_IV);
            saveAlbumIV = itemView.findViewById(R.id.save_album_IV);

            //This delete option can be added based on further requirement analysis
//            deleteAlbumIV = itemView.findViewById(R.id.delete_album_IV);
        }
    }

}
