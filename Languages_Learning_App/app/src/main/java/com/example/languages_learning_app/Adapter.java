package com.example.languages_learning_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<com.example.languages_learning_app.Adapter.ViewHolder> {

    List<String> units;
    Integer image;
    LayoutInflater inflater;

    public Adapter(Context context, List<String> units, Integer image) {
        this.units = units;
        this.image = image;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public com.example.languages_learning_app.Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.unit_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.languages_learning_app.Adapter.ViewHolder holder, int position) {
        holder.unitTitle.setText(units.get(position));
        holder.icon.setImageResource(image);
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView unitTitle;
        ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unitTitle = itemView.findViewById(R.id.unitTitle);
            icon = itemView.findViewById(R.id.imageView);
        }
    }
}
