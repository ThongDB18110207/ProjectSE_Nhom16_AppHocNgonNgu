package com.example.languages_learning_app.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.languages_learning_app.Common.Common;
import com.example.languages_learning_app.DAO.UserDAO;
import com.example.languages_learning_app.DTO.User;
import com.example.languages_learning_app.R;
import com.google.android.material.textfield.TextInputEditText;


public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputEditText tetCurrentPassword, tetNewPassword, tetReNewPassword;
    private Button btChangePassword;

    private enum Ouput {
        valid,
        empty_password,
        wrong_password,
        empty_newPassword,
        invalid_newPassword,
        empty_reNewPassword,
        wrong_renewPassword
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        tetCurrentPassword = (TextInputEditText) findViewById(R.id.tetCurrentPassword);
        tetNewPassword = (TextInputEditText) findViewById(R.id.tetNewPassword);
        tetReNewPassword = (TextInputEditText) findViewById(R.id.tetReNewPassword);

        btChangePassword = (Button) findViewById(R.id.btChangePassword);
        btChangePassword.setOnClickListener((View v) -> {
            String currentPassword = tetCurrentPassword.getText().toString();
            String newPassword = tetNewPassword.getText().toString();
            String reNewPassword = tetReNewPassword.getText().toString();

            changePassword(currentPassword, newPassword, reNewPassword);
        });
    }

    private void changePassword(String currentPassword, String newPassword, String reNewPassword){
        Ouput result = validateInput(currentPassword, newPassword, reNewPassword);

        switch (result){
            case valid:
                saveNewPassword(newPassword);
                break;
            case empty_password:
                tetCurrentPassword.setError("Password is required!");
                tetCurrentPassword.requestFocus();
                break;
            case wrong_password:
                tetCurrentPassword.setError("Password is wrong!");
                tetCurrentPassword.requestFocus();
                break;
            case empty_newPassword:
                tetNewPassword.setError("Enter new password!");
                tetNewPassword.requestFocus();
                break;
            case invalid_newPassword:
                tetNewPassword.setError("Invalid new password!");
                tetNewPassword.requestFocus();
                break;
            case empty_reNewPassword:
                tetReNewPassword.setError("Enter new password again!");
                tetReNewPassword.requestFocus();
                break;
            case wrong_renewPassword:
                tetReNewPassword.setError("Different from new password!");
                tetReNewPassword.requestFocus();
                break;
        }
    }

    private Ouput validateInput(String currentPassword, String newPassword, String reNewPassword){
        if(currentPassword.isEmpty()){
            return Ouput.empty_password;
        }

        // HELP ME HERE!
        if(currentPassword != "123"){
            return Ouput.wrong_password;
        }

        if(newPassword.isEmpty()){
            return Ouput.empty_newPassword;
        }

        if(newPassword.length() < 8){
            return Ouput.invalid_newPassword;
        }

        if(reNewPassword.isEmpty()){
            return Ouput.empty_reNewPassword;
        }

        if(!reNewPassword.equals(newPassword)){
            return Ouput.wrong_renewPassword;
        }

        return Ouput.valid;
    }

    private void saveNewPassword(String newPassword){

        // get auth

        try {
            // AND HERE
            // HOW TO UPDATE PASSWORD
            Toast.makeText(getApplicationContext(), "Change password successfully!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Sorry. Change password failed!", Toast.LENGTH_SHORT).show();
        }
    }


}