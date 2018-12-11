package com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import android.arch.persistence.room.Ignore;

//recommended groups
@Entity(tableName = "recommended_groups")
public class DineMeRGroup extends  DineMeGroup {
    public DineMeRGroup(int dbId, String name, String description, String date, String imageUrl){
        super(dbId, name, description, date, imageUrl);
    }

    @Ignore
    public DineMeRGroup(@NonNull int id, int dbId, String name, String description, String date, String imageUrl){
        super(dbId, name, description, date, imageUrl);
    }
}
