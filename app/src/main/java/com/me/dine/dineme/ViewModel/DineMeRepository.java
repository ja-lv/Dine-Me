package com.me.dine.dineme.ViewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.me.dine.dineme.ViewModel.LocalDatabase.DAO.MainUserDao;
import com.me.dine.dineme.ViewModel.LocalDatabase.DAO.MyEventsDao;
import com.me.dine.dineme.ViewModel.LocalDatabase.DAO.MyGroupsDao;
import com.me.dine.dineme.ViewModel.LocalDatabase.DAO.RGroupsDao;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyEvent;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyGroup;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeRGroup;
import com.me.dine.dineme.ViewModel.LocalDatabase.DineMeRoomDB;

import java.util.List;

public class DineMeRepository {
    //daos
    private MainUserDao mMainUserDao;
    private RGroupsDao mRGroupsDao;
    private MyGroupsDao mMyGroupsDao;
    private MyEventsDao mMyEventsDao;

    //data
    private LiveData<DineMeMainUser> mMainUser;
    private LiveData<List<DineMeRGroup>> mDineMeRGroups;
    private LiveData<List<DineMeMyGroup>> mDineMeMyGroups;
    private LiveData<List<DineMeMyEvent>> mDineMeMyEvents;

    public DineMeRepository(Application application){
        DineMeRoomDB db = DineMeRoomDB.getInstance(application.getApplicationContext());
        //set up all daos
        mMainUserDao = db.mainUserDao();
        mRGroupsDao = db.rGroupsDao();
        mMyGroupsDao = db.myGroupsDao();
        mMyEventsDao = db.myEventsDao();

        //initiate data, loads the first user
        mMainUser = mMainUserDao.loadMainUser(mMainUserDao.loadAllMainUsers().getValue().get(0).getId());
        mDineMeRGroups = mRGroupsDao.loadAllRGroups();
        mDineMeMyGroups = mMyGroupsDao.loadAllMyGroups();
        mDineMeMyEvents = mMyEventsDao.loadAllMyEvents();
    }

    //CRUD - Create, Read, Update, Delete
    //CREATE
    public void insertMainUser(DineMeMainUser mainUser){
        mMainUserDao.insertMainUser(mainUser);
    }
    public void insertRGroups(List<DineMeRGroup> rGroups){
        mRGroupsDao.insertRGroups(rGroups);
    }
    public void insertMyGroups(List<DineMeMyGroup> myGroups){
        mMyGroupsDao.insertMyGroups(myGroups);
    }
    public void insertMyEvents(List<DineMeMyEvent> myEvents){
        mMyEventsDao.insertMyEvents(myEvents);
    }

    //READ getters
    public LiveData<DineMeMainUser> getMainUser(){ return mMainUser; }
    public LiveData<List<DineMeRGroup>> getRecommendedGroups(){ return mDineMeRGroups; }
    public LiveData<List<DineMeMyGroup>> getMyGroups(){ return mDineMeMyGroups;}
    public LiveData<List<DineMeMyEvent>> getMyEvents(){ return mDineMeMyEvents;}

    //UPDATE
    public void updateMainUser(DineMeMainUser mainUser){
        mMainUserDao.updateMainUser(mainUser);
    }
    public void updateRGroup(DineMeRGroup rGroup){
        mRGroupsDao.updateRGroup(rGroup);
    }
    public void updateMyGroup(DineMeMyGroup myGroup){
        mMyGroupsDao.updateMyGroup(myGroup);
    }
    public void updateMyEvent(DineMeMyEvent myEvent){
        mMyEventsDao.updateMyEvent(myEvent);
    }


    //DELETE
    //delete all rGroups
    public void deleteMainUser(DineMeMainUser mainUser){
        mMainUserDao.deleteMainUser(mainUser);
    }
    public void deleteMainUser(){
        mMainUserDao.deleteMainUser();
    }
    public void deleteRGroups(){
        mRGroupsDao.deleteAllRGroups();
    }
    public void deleteMyGroups(){
        mMyGroupsDao.deleteAllMyGroups();
    }
    public void deleteMyEvents(){
        mMyEventsDao.deleteAllMyEvents();
    }

    //empty the database
    public void emptyDataBase(){
        deleteMainUser();
        deleteRGroups();
        deleteMyGroups();
        deleteMyEvents();
    }

    public void networkFillDataBase(){

    }
}
