package com.example.languages_learning_app.Controllers;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.languages_learning_app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_fragmemt_container, new HomeFragment()).commit();

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_language:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_manager:
                            selectedFragment = new AudioFragment();
                            break;
                        case R.id.nav_trainee:
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
                                .replace(R.id.admin_fragmemt_container, selectedFragment).commit();
                        return true;
                    }
            };
}