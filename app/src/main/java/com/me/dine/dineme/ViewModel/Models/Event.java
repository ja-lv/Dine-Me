package com.me.dine.dineme.ViewModel.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class Event {
    private String groupName;
    private String name;
    private String description;
    private Date date;
    private String imageUrl;
//    private int stars;
//    private int starVotes;
//    private int minReservation;
//    private int maxReservation;
    private List<String> foods;
    private String location;

    public Event(){}

//    public Event(String groupName, String name, String description, Date date, String imageUrl, int stars, int starVotes, int minReservation, int maxReservation, List<String> foods, String location) {

    public Event(String groupName, String name, String description, Date date, String imageUrl, List<String> foods, String location) {
        this.groupName = groupName;
        this.name = name;
        this.description = description;
        this.date = date;
        this.imageUrl = imageUrl;
//        this.stars = stars;
//        this.starVotes = starVotes;
//        this.minReservation = minReservation;
//        this.maxReservation = maxReservation;
        this.foods = foods;
        this.location = location;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
//
//    public int getStars() {
//        return stars;
//    }
//
//    public void setStars(int stars) {
//        this.stars = stars;
//    }
//
//    public int getStarVotes() {
//        return starVotes;
//    }
//
//    public void setStarVotes(int starVotes) {
//        this.starVotes = starVotes;
//    }
//
//    public int getMinReservation() {
//        return minReservation;
//    }
//
//    public void setMinReservation(int minReservation) {
//        this.minReservation = minReservation;
//    }
//
//    public int getMaxReservation() {
//        return maxReservation;
//    }
//
//    public void setMaxReservation(int maxReservation) {
//        this.maxReservation = maxReservation;
//    }

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
