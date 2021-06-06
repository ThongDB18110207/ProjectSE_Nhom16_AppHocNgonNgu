package com.example.languages_learning_app.Views.Trainee;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.languages_learning_app.Common.Common;
import com.example.languages_learning_app.DTO.Lesson;
import com.example.languages_learning_app.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TraineeVocabularyActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tabFlashcard, tabLearning, tabLearned;
    private TraineeVocabPagerAdapter pagerAdapter;

    private Lesson lesson;
    private ArrayList<String> listVocabs;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_vocabulary);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            lesson = (Lesson) bundle.getSerializable("lesson");
        }

        // Get list vocabulary id that current user has learned
        getVocabIdsFromFirebase();

        // Mapping Tab layout, tab items and view pager
        tabLayout   = (TabLayout) findViewById(R.id.trainee_vocabs_tabLayout);
        tabFlashcard  = (TabItem) findViewById(R.id.tab_flashCard);
        tabLearning     = (TabItem) findViewById(R.id.tab_learning);
        tabLearned     = (TabItem) findViewById(R.id.tab_learned);
        viewPager   = (ViewPager) findViewById(R.id.trainee_vocabs_viewPager);

        pagerAdapter = new TraineeVocabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), lesson, listVocabs);
        viewPager.setAdapter(pagerAdapter);

        // Change page when selecting tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // Get list vocabulary id that current user has learned
    private void getVocabIdsFromFirebase() {
        listVocabs = new ArrayList<>();
        // Fill data from Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("Processes")
                .child(Common.user.getUserId())
                .child(Common.language.getId())
                .child("Vocabularies")
                .child(lesson.getId());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listVocabs.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String vocabId = dataSnapshot.getValue(String.class);
                    listVocabs.add(vocabId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}