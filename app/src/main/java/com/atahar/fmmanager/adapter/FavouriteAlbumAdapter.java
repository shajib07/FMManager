package com.atahar.fmmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atahar.fmmanager.AlbumDetailsActivity;
import com.atahar.fmmanager.R;
import com.atahar.fmmanager.model.FavoriteAlbum;
import com.atahar.fmmanager.utils.Constants;
import com.atahar.fmmanager.utils.HelperFunctions;
import com.bumptech.glide.Glide;

import java.util.List;

public class FavouriteAlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "FavouriteAlbumAdapter";
    private Context mContext;
    private List<FavoriteAlbum> albumList;

    public FavouriteAlbumAdapter(Context context, List<FavoriteAlbum> albumList) {
        this.mContext = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fav_album_single_item,
                viewGroup, false);
        return new FavouriteAlbumAdapter.FavAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        final FavoriteAlbum album = albumList.get(position);
        final FavouriteAlbumAdapter.FavAlbumViewHolder favAlbumViewHolder =
                (FavouriteAlbumAdapter.FavAlbumViewHolder) viewHolder;

        favAlbumViewHolder.favAlbumNameTV.setText(album.getName());

        if(!album.getUrl().isEmpty()) {
            Glide.with(mContext)
                    .load(album.getUrl())
                    .into(favAlbumViewHolder.favAlbumIV);
        }

        favAlbumViewHolder.deleteAlbumIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperFunctions.removeFavouriteAlbum(album);
                albumList.remove(album);
                notifyDataSetChanged();
            }
        });

        favAlbumViewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlbumDetailsActivity.class);
                intent.putExtra(Constants.ALBUM_NAME, album.getName());
                intent.putExtra(Constants.ARTIST_NAME, album.getArtist());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumList == null ? 0 : albumList.size();
    }

    public class FavAlbumViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout root;
        ImageView favAlbumIV, deleteAlbumIV;
        TextView favAlbumNameTV;

        FavAlbumViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            favAlbumNameTV = itemView.findViewById(R.id.fav_album_name_TV);
            favAlbumIV = itemView.findViewById(R.id.fav_album_IV);
            deleteAlbumIV = itemView.findViewById(R.id.delete_album_IV);
        }
    }

}
