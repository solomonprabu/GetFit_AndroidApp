package com.example.getfitnav;

public class UserItem {
    String userId;
    String userHeight;
    String userWeight;

    public  UserItem(){

    }

    public UserItem(String userId, String userHeight, String userWeight) {
        this.userId = userId;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public String getUserWeight() {
        return userWeight;
    }
}

