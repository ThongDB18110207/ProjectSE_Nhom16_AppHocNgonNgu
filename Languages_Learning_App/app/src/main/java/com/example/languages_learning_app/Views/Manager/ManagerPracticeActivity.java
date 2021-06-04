package com.example.languages_learning_app.Views.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.languages_learning_app.DTO.Lesson;
import com.example.languages_learning_app.R;

public class ManagerPracticeActivity extends AppCompatActivity {
    Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_practice);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            lesson = (Lesson) bundle.getSerializable("lesson");
        }
    }
}