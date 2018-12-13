package com.me.dine.dineme.ViewModel.Models;

import java.util.List;

public class User {
    private String username;
    private String email;
    private String description;
    private int age;
    private String location;
    private String photoUrl;
    private List<String> foods;

    //empty for google authenticate
    public User(){}

    public User(String username, String email, String description, int age, String location, String photoUrl, List<String> foods) {
        this.username = username;
        this.email = email;
        this.description = description;
        this.age = age;
        this.location = location;
        this.photoUrl = photoUrl;
        this.foods = foods;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<String> getFoods() {
        return foods;
    }

    public void setFoods(List<String> foods) {
        this.foods = foods;
    }
}
