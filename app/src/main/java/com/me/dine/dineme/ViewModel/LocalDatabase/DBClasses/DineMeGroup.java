package com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//@Entity(tableName = "groups")
public class DineMeGroup {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "db_id")
    private int dbId;
    private String name;
    private String description;
    private String date;
    @ColumnInfo(name = "image_url")
    private String imageUrl;

    public DineMeGroup(int dbId, String name, String description, String date, String imageUrl) {
        this.dbId = dbId;
        this.name = name;
        this.description = description;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    @Ignore
    public DineMeGroup(@NonNull int id, int dbId, String name, String description, String date, String imageUrl) {
        this.id = id;
        this.dbId = dbId;
        this.name = name;
        this.description = description;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
