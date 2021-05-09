package com.example.languages_learning_app.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;

import com.example.languages_learning_app.Adapters.LanguageAdapter;
import com.example.languages_learning_app.DTO.Language;
import com.example.languages_learning_app.R;

import java.util.ArrayList;

public class ChooseLanguageActivity extends AppCompatActivity {
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

        recyclerView = findViewById(R.id.rvLanguages);
        listLanguage = new ArrayList<>();
        listLanguage.add(new Language(R.drawable.flag_of_england,"england", "Tiếng Anh", "Anh"));
        listLanguage.add(new Language(R.drawable.flag_of_china, "china", "Tiếng Trung Quốc", "Trung"));

        languageAdapter = new LanguageAdapter(getApplicationContext(), listLanguage, listener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(languageAdapter);

        back = (Button) findViewById(R.id.btBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();
            }
        });
    }

    private void setOnClickListener() {
        listener = new LanguageAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("LanguageN", listLanguage.get(position).getName());
                intent.putExtra("LanguageDN", listLanguage.get(position).getDisplayName());
                //Convert int to string
                intent.putExtra("LanguageIM", String.valueOf(listLanguage.get(position).getImage()));
                startActivity(intent);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, int position) {
                menu.add(position,0,0,"Edit");
                menu.add(position,1,1,"Delete");
            }
        };
    }
}