package com.example.languages_learning_app.DAO;

import androidx.annotation.NonNull;

import com.example.languages_learning_app.DTO.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public User getUserById(String userId){
        final User[] user = new User[1];
        mDatabase.child(path).child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user[0] = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return user[0];
    }


}
