package com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;


@Entity(tableName = "main_user")
public class DineMeMainUser extends DineMeUser{

    private String googleKey;

    public DineMeMainUser(String username, String gmail, String description, int age, String foods, String location, String googleKey){
        super(username, gmail, description, age, foods, location);
        this.googleKey = googleKey;
    }
    @Ignore
    public DineMeMainUser(@NonNull int id, String username, String gmail, String description, int age, String foods, String location, String googleKey){
        super(id, username, gmail, description, age, foods, location);
        this.googleKey = googleKey;
    }

    public String getGoogleKey() {
        return googleKey;
    }

    public void setGoogleKey(String googleKey) {
        this.googleKey = googleKey;
    }
}
