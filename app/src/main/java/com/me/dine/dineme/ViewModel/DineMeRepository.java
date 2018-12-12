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
    //daos
    private MainUserDao mMainUserDao;
    private RGroupsDao mRGroupsDao;

    //data
    private LiveData<DineMeMainUser> mMainUser;
    private LiveData<List<DineMeRGroup>> mDineMeRGroups;

    public DineMeRepository(Application application){
        DineMeRoomDB db = DineMeRoomDB.getInstance(application.getApplicationContext());
        mMainUserDao = db.mainUserDao();
        mRGroupsDao = db.rGroupsDao();

        //initiate data, loads the first user
        mMainUser = mMainUserDao.loadMainUser(mMainUserDao.loadAllMainUsers().getValue().get(0).getId());
        mDineMeRGroups = mRGroupsDao.loadAllRGroups();
    }

    //CRUD - Create, Read, Update, Delete
    //CREATE
    public void insertRGroups(List<DineMeRGroup> rGroups){
        mRGroupsDao.insertRGroups(rGroups);
    }
    public void insertMainUser(DineMeMainUser mainUser){
        mMainUserDao.insertMainUser(mainUser);
    }

    //READ getters
    public LiveData<List<DineMeRGroup>> getRecommendedGroups(){ return mDineMeRGroups; }
    public LiveData<DineMeMainUser> getmMainUser(){ return mMainUser; }

    //UPDATE
    public void updateRGroup(DineMeRGroup rGroup){
        mRGroupsDao.updateRGroup(rGroup);
    }
    public void updateMainUser(DineMeMainUser mainUser){
        mMainUserDao.updateMainUser(mainUser);
    }

    //DELETE
    //delete all rGroups
    public void deleteRGroups(){
        mRGroupsDao.deleteAllRGroups();
    }
    public void deleteMainUser(DineMeMainUser mainUser){
        mMainUserDao.deleteMainUser(mainUser);
    }


}
