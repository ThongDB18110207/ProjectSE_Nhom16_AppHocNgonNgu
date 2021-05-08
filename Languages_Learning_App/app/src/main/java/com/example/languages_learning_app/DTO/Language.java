package com.example.languages_learning_app.DTO;

public class Language {
    private int image;
    private String name, displayName, briefName;

    public Language(){}

    public Language(int image, String name, String displayName, String briefName) {
        this.image = image;
        this.name = name;
        this.displayName = displayName;
        this.briefName = briefName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBriefName() {
        return briefName;
    }

    public void setBriefName(String briefName) {
        this.briefName = briefName;
    }
}

