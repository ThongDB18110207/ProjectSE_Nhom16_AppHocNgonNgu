package com.example.languages_learning_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmemt_container, new HomeFragment()).commit();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Lựa chọn ngôn ngữ
        String language = "Tiếng anh";
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            language = extras.getString("Language");
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_audio:
                            selectedFragment = new AudioFragment();
                            break;
                        case R.id.nav_test:
                            selectedFragment = new TestFragment();
                            break;
                        case R.id.nav_practice:
                            selectedFragment = new PracticeFragment();
                            break;
                        case R.id.nav_more:
                            selectedFragment = new MoreFragment();
                            break;
                    }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmemt_container, selectedFragment).commit();
                        return true;
                    }
            };
}