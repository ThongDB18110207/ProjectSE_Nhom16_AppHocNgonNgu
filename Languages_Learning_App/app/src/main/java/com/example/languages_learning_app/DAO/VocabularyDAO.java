package com.example.languages_learning_app.DAO;

import com.example.languages_learning_app.Common.Common;
import com.example.languages_learning_app.DTO.Vocabulary;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VocabularyDAO<changeIsActivedLanguage> {
    String path;
    DatabaseReference mDatabase;

    private static VocabularyDAO instance;

    public static VocabularyDAO getInstance() {
        if (instance==null)
            instance = new VocabularyDAO();
        return instance;
    }

    public static void setInstance(VocabularyDAO instance) {
        VocabularyDAO.instance = instance;
    }

    public VocabularyDAO(){
        path = "Vocabularies";
    }

    public boolean delete(String id) {
        try {
            mDatabase = FirebaseDatabase.getInstance().getReference(path);
            mDatabase.child(Common.language.getId()).child(id).removeValue();
            return true;
        } catch (Error error){
            return false;
        }
    }

    public void changeStatus(Vocabulary vocabulary){
        mDatabase = FirebaseDatabase.getInstance().getReference(path);
        mDatabase.child(Common.language.getId()).child(vocabulary.getId()).child("active").setValue(!vocabulary.isActive());
    }
}
