package com.example.languages_learning_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.languages_learning_app.R;

import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.ViewHolder> {

    List<String> units;
    Integer image;
    LayoutInflater inflater;

    public UnitAdapter(Context context, List<String> units, Integer image) {
        this.units = units;
        this.image = image;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.unit_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitAdapter.ViewHolder holder, int position) {
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
