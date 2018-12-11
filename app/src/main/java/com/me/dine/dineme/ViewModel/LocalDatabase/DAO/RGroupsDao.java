package com.me.dine.dineme.ViewModel.LocalDatabase.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeRGroup;

import java.util.List;

@Dao
public interface RGroupsDao {
    @Query("SELECT * FROM recommended_groups ORDER BY name ASC")
    LiveData<List<DineMeRGroup>> loadAllRGroups();

    @Insert
    void insertRGroup(DineMeRGroup dineMeRGroup);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRGroup(DineMeRGroup dineMeRGroup);

    @Delete
    void deleteRGroup(DineMeRGroup dineMeRGroup);

    @Query("DELETE FROM recommended_groups")
    void clearAllRGroups();
}
