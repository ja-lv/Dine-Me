package com.me.dine.dineme.ViewModel.LocalDatabase.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyGroup;

import java.util.List;

@Dao
public interface MyGroupsDao {
    @Query("SELECT * FROM my_groups ORDER BY name ASC")
    LiveData<List<DineMeMyGroup>> loadAllMyGroups();

    @Insert
    void insertMyGroup(DineMeMyGroup dineMeMyGroup);

    @Insert
    void insertMyGroups(List<DineMeMyGroup> dineMeMyGroups);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMyGroup(DineMeMyGroup dineMeMyGroup);

    @Delete
    void deleteMyGroup(DineMeMyGroup dineMeMyGroup);

    @Query("DELETE FROM my_groups")
    void deleteAllMyGroups();
}
