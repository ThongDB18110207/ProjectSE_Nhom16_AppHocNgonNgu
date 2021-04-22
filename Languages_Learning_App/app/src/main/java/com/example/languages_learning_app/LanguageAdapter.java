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

    private RecyclerViewClickListener listener;


    public LanguageAdapter(Context context, ArrayList<Language> listLanguage, RecyclerViewClickListener listener){
        this.context = context;
        this.listLanguage = listLanguage;
        this.listener = listener;
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView tvlanguage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvlanguage = itemView.findViewById(R.id.tvLanguage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
