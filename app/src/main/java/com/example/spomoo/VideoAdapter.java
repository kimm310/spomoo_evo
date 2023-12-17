package com.example.spomoo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spomoo.mainscreen.MainActivity;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private String[] videoNames; // Namen oder Pfade der Videos
    private OnItemClickListener listener;

    public VideoAdapter(String[] videoNames, OnItemClickListener listener) {
        this.videoNames = videoNames;
        this.listener = listener;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.tvVideoName.setText(videoNames[position]);
        // Set the margin to reduce the space between items (adjust the values as needed)
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        layoutParams.bottomMargin = 10; // set your desired margin


    }

    @Override
    public int getItemCount() {
        return videoNames.length;
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvVideoName;

        VideoViewHolder(View itemView) {
            super(itemView);
            tvVideoName = itemView.findViewById(R.id.tvVideoName);
            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(videoNames[getAdapterPosition()]);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String videoNames);
    }
}
