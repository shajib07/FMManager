package com.atahar.fmmanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atahar.fmmanager.R;
import com.atahar.fmmanager.model.Track;

import java.util.List;

public class TracksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "TracksAdapter";
    private Context mContext;
    private List<Track> mTrackList;

    public TracksAdapter(Context context, List<Track> tracks) {
        this.mContext = context;
        this.mTrackList = tracks;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tracks_single_item,
                viewGroup, false);
        return new TracksAdapter.TracksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        final Track track = mTrackList.get(position);
        final TracksAdapter.TracksViewHolder tracksViewHolder = (TracksAdapter.TracksViewHolder) viewHolder;
        tracksViewHolder.trackNameTV.setText(track.getName());
    }

    @Override
    public int getItemCount() {
        return mTrackList == null ? 0 : mTrackList.size();
    }

    public class TracksViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout root;
        TextView trackNameTV;

        TracksViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            trackNameTV = itemView.findViewById(R.id.track_name_TV);
        }
    }
}
