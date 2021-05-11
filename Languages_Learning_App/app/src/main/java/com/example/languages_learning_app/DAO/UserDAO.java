package com.example.languages_learning_app.DAO;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.languages_learning_app.Controllers.LoginActivity;
import com.example.languages_learning_app.Controllers.ProfileActivity;
import com.example.languages_learning_app.DTO.Language;
import com.example.languages_learning_app.DTO.User;
import com.example.languages_learning_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDAO {
    String path;
    DatabaseReference mDatabase;

    private static UserDAO instance;

    public static UserDAO getInstance() {
        if (instance==null)
            instance = new UserDAO();
        return instance;
    }

    public static void setInstance(UserDAO instance) {
        UserDAO.instance = instance;
    }

    public UserDAO(){
        path = "Users";
    }

    public void setUserValue(User user){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(path).child(user.getUserId()).setValue(user);
    }

    public boolean deleteUser(String userId) {
        try {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child(path).child(userId).removeValue();
            return true;
        } catch (Error error){
            return false;
        }
    }

    public void changeStatusUser(String userId, boolean status){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(path).child(userId).child("active").setValue(!status);
    }
}