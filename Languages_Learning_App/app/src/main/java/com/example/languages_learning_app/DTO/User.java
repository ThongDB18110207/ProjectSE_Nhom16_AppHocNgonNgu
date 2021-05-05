package com.example.languages_learning_app.DTO;

public class User {
    public String fullName, phone, email;

    public User(){}

    public User(String fullName, String phone, String email){
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }
}