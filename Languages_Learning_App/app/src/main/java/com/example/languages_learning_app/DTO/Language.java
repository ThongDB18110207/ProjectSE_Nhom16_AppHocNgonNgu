package com.example.languages_learning_app.DTO;

public class Language {
    private int id;
    private int image;
    private String name, displayName, briefName;
    private boolean status;

    public Language(){}

    public Language(int id, String name, String displayName, String briefName, int image) {
        this.id = id + 1;
        this.name = name;
        this.displayName = displayName;
        this.briefName = briefName;
        this.status = true;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

