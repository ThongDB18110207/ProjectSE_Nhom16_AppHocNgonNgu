package com.example.languages_learning_app.DTO;

public class Language {
    private int image;
    private String name, displayName;

    public Language(){}

    public Language(int image, String name, String displayName) {
        this.image = image;
        this.name = name;
        this.displayName = displayName;
    }

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public  int getImage(){return image;}
    public void setImage(int image){this.image = image;}

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}

