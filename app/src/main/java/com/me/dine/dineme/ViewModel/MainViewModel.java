package com.me.dine.dineme.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyEvent;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyGroup;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeRGroup;
import com.me.dine.dineme.ViewModel.Models.User;
import com.me.dine.dineme.ViewModel.Network.FirebaseAdapterCalls;

import java.util.List;

import com.google.firebase.auth.FirebaseUser;

//connect Repository later
public class MainViewModel extends AndroidViewModel {
    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    //firebase user and adapter
    private FirebaseUser mFirebaseUser;
    private FirebaseAdapterCalls mFirebaseAdapter;

    public MainViewModel(Application application, FirebaseUser firebaseUser){
        super(application);

        //firebase data
        mFirebaseUser = firebaseUser;
        mFirebaseAdapter = new FirebaseAdapterCalls(mFirebaseUser);
    }

    public void setUser(User user){
        //update firebase
        mFirebaseAdapter.setUser(user);
    }


}
