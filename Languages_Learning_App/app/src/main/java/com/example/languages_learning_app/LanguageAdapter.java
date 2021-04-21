package com.example.languages_learning_app;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder>{
    Context context;
    ArrayList<Language> listLanguage;

    public LanguageAdapter(Context context, ArrayList<Language> listLanguage){
        this.context = context;
        this.listLanguage = listLanguage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.language_layout, parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull LanguageAdapter.ViewHolder holder, int position) {
        Language language = listLanguage.get(position);

        holder.tvlanguage.setText(language.getName());
        holder.imageView.setImageResource(language.getImage());
    }

    @Override
    public int getItemCount() {
        return listLanguage.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvlanguage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvlanguage = itemView.findViewById(R.id.tvLanguage);
        }
    }
}
