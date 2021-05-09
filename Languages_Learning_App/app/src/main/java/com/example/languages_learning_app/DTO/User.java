package com.example.languages_learning_app.DTO;

public class User {
    private String fullName, phone, email, role;
    private int image;
    private boolean isActive;

    public User(){}

    public User(String fullName, String phone, String email, String role) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.isActive = true;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}