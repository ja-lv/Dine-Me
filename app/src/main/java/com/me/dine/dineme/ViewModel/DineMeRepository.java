package com.me.dine.dineme.ViewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.me.dine.dineme.ViewModel.LocalDatabase.DAO.MainUserDao;
import com.me.dine.dineme.ViewModel.LocalDatabase.DAO.RGroupsDao;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeRGroup;
import com.me.dine.dineme.ViewModel.LocalDatabase.DineMeRoomDB;

import java.util.List;

public class DineMeRepository {
    private MainUserDao mMainUserDao;
    private RGroupsDao mRGroupsDao;
    private LiveData<DineMeMainUser> mMainUser;
    private LiveData<List<DineMeRGroup>> mDineMeRGroups;

    public DineMeRepository(Application application){
        DineMeRoomDB db = DineMeRoomDB.getInstance(application.getApplicationContext());
        mMainUserDao = db.mainUserDao();
        mRGroupsDao = db.rGroupsDao();
    }
}
