package com.me.dine.dineme.ViewModel.LocalDatabase.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;


@Dao
public interface MainUserDao {
    @Query("SELECT * FROM main_user WHERE id = :id")
    LiveData<DineMeMainUser> loadMainUser(int id);

    @Insert
    void insertMainUser(DineMeMainUser mainUser);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMainUser(DineMeMainUser mainUser);

    @Delete
    void deleteMainUser(DineMeMainUser mainUser);
}
