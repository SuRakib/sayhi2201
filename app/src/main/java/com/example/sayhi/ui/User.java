package com.example.sayhi.ui;

public class User {

    String userName;
    String userId;
    String userEmail;
    String userPhone;
    String userCountry;

    String profileImage;

    public User(String userName, String userId, String userEmail, String userPhone, String userCountry,String profileImage) {
        this.userName = userName;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userCountry = userCountry;
        this.profileImage = profileImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }
}
