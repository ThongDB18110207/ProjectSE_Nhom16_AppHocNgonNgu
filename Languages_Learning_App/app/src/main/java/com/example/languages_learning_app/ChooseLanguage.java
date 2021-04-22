package com.example.languages_learning_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ChooseLanguage extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Language> listLanguage;
    LanguageAdapter languageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        recyclerView = findViewById(R.id.recyclerview);
        listLanguage = new ArrayList<>();
        listLanguage.add(new Language(R.drawable.flag_of_england, "Tiếng Anh"));
        listLanguage.add(new Language(R.drawable.flag_of_china, "Tiếng Trung Quốc"));

        languageAdapter = new LanguageAdapter(getApplicationContext(), listLanguage);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(languageAdapter);
    }
}