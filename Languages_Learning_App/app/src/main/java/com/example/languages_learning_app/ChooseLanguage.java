package com.example.languages_learning_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ChooseLanguage extends AppCompatActivity {
    Button back;
    RecyclerView recyclerView;
    ArrayList<Language> listLanguage;
    LanguageAdapter languageAdapter;
    private LanguageAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        setOnClickListener();

        recyclerView = findViewById(R.id.recyclerview);
        listLanguage = new ArrayList<>();
        listLanguage.add(new Language(R.drawable.flag_of_england, "Tiếng Anh"));
        listLanguage.add(new Language(R.drawable.flag_of_china, "Tiếng Trung Quốc"));

        languageAdapter = new LanguageAdapter(getApplicationContext(), listLanguage, listener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(languageAdapter);

        back = (Button) findViewById(R.id.btBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setOnClickListener() {
        listener = new LanguageAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Language", listLanguage.get(position).getName());
                startActivity(intent);
            }
        };
    }
}