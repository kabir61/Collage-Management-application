package com.example.usercollageapplication.user.model;

public class Users {
    private String id;
    private String username;
    private String imageURL;

    public Users() {
    }

    public Users(String id, String username, String imageURL) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }



    public String getImageURL() {
        return imageURL;
    }
}
