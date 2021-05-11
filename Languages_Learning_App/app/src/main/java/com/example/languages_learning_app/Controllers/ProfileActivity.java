package com.example.languages_learning_app.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.languages_learning_app.Common.Common;
import com.example.languages_learning_app.DAO.UserDAO;
import com.example.languages_learning_app.DTO.User;
import com.example.languages_learning_app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private TextInputEditText tetName, tetEmail, tetPhone;
    private Button btUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tetName = (TextInputEditText) findViewById(R.id.tetName);
        tetEmail = (TextInputEditText) findViewById(R.id.tetEmail);
        tetPhone = (TextInputEditText) findViewById(R.id.tetPhone);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                fillInUserInfo(userProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happen!", Toast.LENGTH_LONG).show();
            }
        });

        btUpdate = (Button) findViewById(R.id.btUpdate);
        btUpdate.setOnClickListener((View v) -> {
            updateUserProfile();
        });
    }

    private void fillInUserInfo(User user){
        if(user != null){
            tetName.setText(user.getFullName());
            tetEmail.setText(user.getEmail());
            tetPhone.setText(user.getPhone());
        }
    }

    private void updateUserProfile(){
        String name = tetName.getText().toString();
        String phone = tetPhone.getText().toString();

        if (name.isEmpty()){
            tetName.setError("Name is required!");
            tetName.requestFocus();
            return;
        }

        if (phone.isEmpty()){
            tetPhone.setError("Phone is required!");
            tetPhone.requestFocus();
            return;
        }

        User user = Common.user;

        try {
            user.setFullName(name);
            user.setPhone(phone);
            UserDAO.getInstance().setUserValue(user);

            Toast.makeText(this, "Update profile successfully!", Toast.LENGTH_SHORT);
        }
        catch (Exception e){
            Toast.makeText(this, "Sorry. Update failed!", Toast.LENGTH_SHORT);
        }
    }
}