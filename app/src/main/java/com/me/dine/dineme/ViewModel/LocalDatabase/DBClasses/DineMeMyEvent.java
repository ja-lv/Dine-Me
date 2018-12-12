package com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import android.arch.persistence.room.Ignore;

@Entity(tableName = "my_events")
public class DineMeMyEvent extends DineMeEvent{
    public DineMeMyEvent(int dbId, int groupId, String name, String description, String date, String imageUrl, int stars, int starVotes, int minReservation, int maxReservation, String foods){
        super(dbId, groupId, name, description, date, imageUrl, stars, starVotes, minReservation, maxReservation, foods);
    }

    @Ignore
    public DineMeMyEvent(@NonNull int id, int dbId, int groupId, String name, String description, String date, String imageUrl, int stars, int starVotes, int minReservation, int maxReservation, String foods){
        super(id, dbId, groupId, name, description, date, imageUrl, stars, starVotes, minReservation, maxReservation, foods);
    }
}
