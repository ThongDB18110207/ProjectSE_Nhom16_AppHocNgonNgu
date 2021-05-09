package com.example.languages_learning_app.DAO;

import androidx.annotation.NonNull;

import com.example.languages_learning_app.DTO.Language;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LanguageDAO {
    String path;
    DatabaseReference mDatabase;

    private static LanguageDAO instance;

    public static LanguageDAO getInstance() {
        if (instance==null)
            instance = new LanguageDAO();
        return instance;
    }

    public static void setInstance(LanguageDAO instance) {
        LanguageDAO.instance = instance;
    }

    public LanguageDAO(){
        path = "Languages";
    }

    public void addLanguage(Language language){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(path).child(language.getName()).setValue(language);
    }

    public ArrayList<Language> getListLanguage(){
        ArrayList<Language> listLanguage = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference(path);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Language language = dataSnapshot.getValue(Language.class);
                    listLanguage.add(language);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listLanguage;
    }
}
