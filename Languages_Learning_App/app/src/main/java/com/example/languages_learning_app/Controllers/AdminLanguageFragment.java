package com.example.languages_learning_app.Controllers;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.languages_learning_app.Adapters.LanguageAdapter;
import com.example.languages_learning_app.DAO.LanguageDAO;
import com.example.languages_learning_app.DTO.Language;
import com.example.languages_learning_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminLanguageFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Language> listLanguage;
    LanguageAdapter languageAdapter;

    DatabaseReference mDatabase;

    private LanguageAdapter.RecyclerViewClickListener listener;

    FloatingActionButton btOpenDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_admin_language, container, false);

        setOnClickListener();
        setRecyclerView(root);

        btOpenDialog = (FloatingActionButton) root.findViewById(R.id.btOpenLanguageDialog);
        btOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Language language = new Language(R.drawable.flag_of_england,"england2", "Tiếng Anh", "Anh");
                LanguageDAO.getInstance().addLanguage(language);
            }
        });

        return root;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = item.getGroupId();
        switch (item.getItemId()){
            case 0:
                return true;
            case 1:
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void setOnClickListener() {
        listener = new LanguageAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, int position) {

            }
        };
    }

    private void setRecyclerView(View root){
        recyclerView = root.findViewById(R.id.rvLanguages);

        listLanguage = new ArrayList<>();

        languageAdapter = new LanguageAdapter(getContext(), listLanguage, listener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(languageAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference("Languages");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listLanguage.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Language language = dataSnapshot.getValue(Language.class);
                    listLanguage.add(language);
                }
                languageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}