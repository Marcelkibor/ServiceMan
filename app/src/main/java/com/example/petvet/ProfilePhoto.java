package com.example.petvet;

public class ProfilePhoto {
    private String userId;
    private String imageUri;

    public ProfilePhoto() {
    }

    public ProfilePhoto(String userId, String imageUri) {
        this.userId = userId;
        this.imageUri = imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getUserId() {
        return userId;
    }

}

