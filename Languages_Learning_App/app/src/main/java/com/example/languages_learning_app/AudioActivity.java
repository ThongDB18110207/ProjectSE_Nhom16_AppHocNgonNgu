package com.example.languages_learning_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AudioActivity extends AppCompatActivity {

    RecyclerView dataList;
    List<String> units;
    Integer image;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        dataList = findViewById(R.id.dataList);

        units = new ArrayList<>();
        units.add("Unit 1: Sports and Games");
        units.add("Unit 2: Sports and Games");
        units.add("Unit 3: Sports and Games");
        units.add("Unit 4: Sports and Games");
        units.add("Unit 5: Sports and Games");
        units.add("Unit 6: Sports and Games");
        units.add("Unit 7: Sports and Games");
        units.add("Unit 8: Sports and Games");
        units.add("Unit 9: Sports and Games");

        image = R.drawable.ic_baseline_play_circle_outline;

        adapter = new Adapter(this, units, image);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
    }
}