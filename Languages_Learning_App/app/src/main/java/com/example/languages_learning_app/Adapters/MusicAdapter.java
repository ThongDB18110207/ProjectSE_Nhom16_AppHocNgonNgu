package com.example.languages_learning_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.languages_learning_app.DTO.Music;
import com.example.languages_learning_app.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    Context context;

    ArrayList<Music> list;

    public MusicAdapter(Context context, ArrayList<Music> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_for_recyclerview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MusicAdapter.MyViewHolder holder, int position) {

        Music music = list.get(position);
        holder.videoName.setText(music.getVideoname());
        holder.videoURL.setText(music.getUrl());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView videoName, videoURL;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            videoName = itemView.findViewById(R.id.TeViVideoNameinMusicRecyclerView);
            videoURL = itemView.findViewById(R.id.TeViVideoURLinMusicRecyclerView);
            linearLayout = itemView.findViewById(R.id.linearlayout_foreground);
        }
    }

    public void removeItem (int index) {
        list.remove(index);

        notifyItemRemoved(index);
    }

    public void undoItem (Music music, int index) {
        list.add(index, music);

        notifyItemInserted(index);
    }

}
