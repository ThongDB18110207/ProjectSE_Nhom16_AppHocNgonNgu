package com.example.languages_learning_app.DAO;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.languages_learning_app.Controllers.LoginActivity;
import com.example.languages_learning_app.Controllers.ProfileActivity;
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

    public User getUserById(String userId){
        final User[] user = new User[1];
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    user[0] = userProfile;
                    int i = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return user[0];
    }


}
