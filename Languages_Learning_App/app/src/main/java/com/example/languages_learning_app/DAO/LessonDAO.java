package com.example.languages_learning_app.DAO;

import com.example.languages_learning_app.DTO.Language;
import com.example.languages_learning_app.DTO.Lesson;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LessonDAO<changeIsActivedLanguage> {
    String path;
    DatabaseReference mDatabase;

    private static LessonDAO instance;

    public static LessonDAO getInstance() {
        if (instance==null)
            instance = new LessonDAO();
        return instance;
    }

    public static void setInstance(LessonDAO instance) {
        LessonDAO.instance = instance;
    }

    public LessonDAO(){
        path = "Lessons";
    }

    public void setLessonValue(Lesson lesson){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(path).child(String.valueOf(lesson.getId())).setValue(lesson);
    }

    public boolean deleteLesson(int id) {
        try {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child(path).child(String.valueOf(id)).removeValue();
            return true;
        } catch (Error error){
            return false;
        }
    }

    public void changeStatusLesson(Lesson lesson){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(path).child(String.valueOf(lesson.getId())).child("status").setValue(!lesson.isStatus());
    }
}
