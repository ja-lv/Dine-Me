package com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import android.arch.persistence.room.Ignore;

@Entity(tableName = "my_groups")
public class DineMeMyGroup extends DineMeGroup{
    public DineMeMyGroup(int dbId, String name, String description, String date, String imageUrl){
        super(dbId, name, description, date, imageUrl);
    }

    @Ignore
    public DineMeMyGroup(@NonNull int id, int dbId, String name, String description, String date, String imageUrl){
        super(dbId, name, description, date, imageUrl);
    }
}
