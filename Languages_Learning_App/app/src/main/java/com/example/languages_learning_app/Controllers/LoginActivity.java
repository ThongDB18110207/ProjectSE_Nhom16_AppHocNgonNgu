package com.example.languages_learning_app.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.languages_learning_app.Common.Common;
import com.example.languages_learning_app.DAO.UserDAO;
import com.example.languages_learning_app.DTO.User;
import com.example.languages_learning_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private  TextView register, fogotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private RadioButton rbAdmin, rbManager, rbTrainee;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.etEmail);
        editTextPassword = (EditText) findViewById(R.id.etPassword);

        editTextEmail.setText("lenhattuong12345@gmail.com");
        editTextPassword.setText("12345678");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        rbAdmin = (RadioButton) findViewById(R.id.rbAdmin);
        rbManager = (RadioButton) findViewById(R.id.rbManager);
        rbTrainee = (RadioButton) findViewById(R.id.rbTrainee);

        register = (TextView) findViewById((R.id.tvRegister));
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.btLogin);
        signIn.setOnClickListener(this);

        fogotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        fogotPassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvRegister:
                startActivity(new Intent(this, RegisterUserActivity.class));
                break;
            case R.id.btLogin:
                userLogin();
                break;
            case R.id.tvForgotPassword:
                startActivity(new Intent(this, FogotPasswordActivity.class));
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid Email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 8){
            editTextPassword.setError("Min password length is 8 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //Add email to auth
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // Validate Email
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){

                        chooseRole(user.getUid());
                        // redirect to user profile
                        /*
                        if(rbAdmin.isChecked()){
                            startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                        }

                        if(rbTrainee.isChecked()){
                            startActivity(new Intent(LoginActivity.this, ChooseLanguageActivity.class));
                        }*/
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Check your Email to verify your account", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Failed to Login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void chooseRole(String userId){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                }
                else {
                    Common.user = task.getResult().getValue(User.class);
                    
                    if(!Common.user.isActive()){
                        Toast.makeText(LoginActivity.this, "Your account has been clocked!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(Common.user != null){
                        if (Common.user.getRole().equals(Common.RoleAdmin)) {
                            if (rbAdmin.isChecked()) {
                                startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                                return;
                            }
                            if (rbTrainee.isChecked()){
                                startActivity(new Intent(LoginActivity.this, ChooseLanguageActivity.class));
                                return;
                            }
                        }
                        if (Common.user.getRole().equals(Common.RoleTrainee)) {
                            if (rbTrainee.isChecked()){
                                startActivity(new Intent(LoginActivity.this, ChooseLanguageActivity.class));
                                return;
                            }
                        }
                        Toast.makeText(LoginActivity.this, "Your account doesn't have permission!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}