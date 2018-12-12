package com.me.dine.dineme.ViewModel.LocalDatabase.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyEvent;

import java.util.List;

@Dao
public interface MyEventsDao {
    @Query("SELECT * FROM my_events ORDER BY name ASC")
    LiveData<List<DineMeMyEvent>> loadAllMyEvents();

    @Insert
    void insertMyEvent(DineMeMyEvent dineMeMyEvent);

    @Insert
    void insertMyEvents(List<DineMeMyEvent> dineMeMyEvents);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMyEvent(DineMeMyEvent dineMeMyEvent);

    @Delete
    void deleteMyEvent(DineMeMyEvent dineMeMyEvent);

    @Query("DELETE FROM my_groups")
    void deleteAllMyEvents();
}
