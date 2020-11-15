package com.example.usercollageapplication.ui.about;

public class BranceModel {
    private int img;
    private String title,description;

    public BranceModel() {
    }

    public BranceModel(int img, String title, String description) {
        this.img = img;
        this.title = title;
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getDescribe() {
        return description;
    }
}
