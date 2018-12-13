package com.me.dine.dineme.ViewModel.Models;

import java.util.List;

public class Group {
    private String ownerEmail;
    private String name;
    private String description;
    private String date;
    private String imageUrl;
    private List<String> foods;
    private String location;

    public Group(){};
    public Group(String ownerEmail, String name, String description, String date, String imageUrl, String location) {
        this.ownerEmail = ownerEmail;
        this.name = name;
        this.description = description;
        this.date = date;
        this.imageUrl = imageUrl;
        this.location = location;
    }


    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getFoods() {
        return foods;
    }

    public void setFoods(List<String> foods) {
        this.foods = foods;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
