package com.me.dine.dineme.ViewModel.LocalDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.me.dine.dineme.ViewModel.LocalDatabase.DAO.MainUserDao;
import com.me.dine.dineme.ViewModel.LocalDatabase.DAO.MyEventsDao;
import com.me.dine.dineme.ViewModel.LocalDatabase.DAO.MyGroupsDao;
import com.me.dine.dineme.ViewModel.LocalDatabase.DAO.RGroupsDao;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyEvent;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyGroup;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeRGroup;

@Database(entities = {DineMeMainUser.class, DineMeRGroup.class, DineMeMyGroup.class, DineMeMyEvent.class}, version = 1, exportSchema = false)
public abstract class DineMeRoomDB extends RoomDatabase {
    private static final String LOG_TAG = DineMeRoomDB.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "dine_me";
    private static DineMeRoomDB sInstance;

    public static DineMeRoomDB getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        DineMeRoomDB.class, DineMeRoomDB.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract MainUserDao mainUserDao();
    public abstract RGroupsDao rGroupsDao();
    public abstract MyGroupsDao myGroupsDao();
    public abstract MyEventsDao myEventsDao();
}
