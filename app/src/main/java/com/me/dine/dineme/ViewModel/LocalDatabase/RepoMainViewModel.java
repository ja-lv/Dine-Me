package com.me.dine.dineme.ViewModel.LocalDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.me.dine.dineme.ViewModel.DineMeRepository;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyEvent;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyGroup;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeRGroup;
import com.me.dine.dineme.ViewModel.MainViewModel;
import com.me.dine.dineme.ViewModel.Network.FirebaseAdapterCalls;

import java.util.List;

import com.google.firebase.auth.FirebaseUser;

//connect Repository later
public class RepoMainViewModel extends AndroidViewModel {
    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private DineMeRepository mDineMeRepository;

    //items we will work with
    private LiveData<DineMeMainUser> mMainUser;
    private LiveData<List<DineMeRGroup>> mDineMeRGroups;
    private LiveData<List<DineMeMyGroup>> mDineMeMyGroups;
    private LiveData<List<DineMeMyEvent>> mDineMeMyEvents;

    //firebase user and adapter
    private FirebaseUser mFirebaseUser;
    private FirebaseAdapterCalls mFirebaseAdapter;

    public RepoMainViewModel(Application application, FirebaseUser firebaseUser){
        super(application);
        mDineMeRepository = new DineMeRepository(application);

        //initialize data
        mMainUser = mDineMeRepository.getMainUser();
        mDineMeRGroups = mDineMeRepository.getRecommendedGroups();
        mDineMeMyGroups = mDineMeRepository.getMyGroups();

        //firebase data
        mFirebaseUser = firebaseUser;
        mFirebaseAdapter = new FirebaseAdapterCalls(mFirebaseUser);
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

    public void initNewUser(DineMeMainUser mainUser){
        //update firebase
//        mFirebaseAdapter.setUser(mainUser);
        mDineMeRepository.insertMainUser(mainUser);
    }
}
