package com.me.dine.dineme.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyEvent;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyGroup;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeRGroup;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private DineMeRepository mDineMeRepository;

    //items we will work with
    private LiveData<DineMeMainUser> mMainUser;
    private LiveData<List<DineMeRGroup>> mDineMeRGroups;
    private LiveData<List<DineMeMyGroup>> mDineMeMyGroups;
    private LiveData<List<DineMeMyEvent>> mDineMeMyEvents;

    public MainViewModel(Application application){
        super(application);
        mDineMeRepository = new DineMeRepository(application);

        //initialize data
        mMainUser = mDineMeRepository.getMainUser();
        mDineMeRGroups = mDineMeRepository.getRecommendedGroups();
        mDineMeMyGroups = mDineMeRepository.getMyGroups();
        mDineMeMyEvents = mDineMeRepository.getMyEvents();
    }

    //getters
    public LiveData<DineMeMainUser> getMainUser() { return mMainUser; }
    public LiveData<List<DineMeRGroup>> getmDineMeRGroups() { return mDineMeRGroups; }
    public LiveData<List<DineMeMyGroup>> getmDineMeMyGroups() { return mDineMeMyGroups; }
    public LiveData<List<DineMeMyEvent>> getmDineMeMyEvents() { return mDineMeMyEvents; }

    //init sync
    //clears all the local databases, grabs all data from the network and inserts them in to the databases.
    public void initSync() {
        mDineMeRepository.emptyDataBase();
        //network update
        mDineMeRepository.networkFillDataBase();
    }
}
