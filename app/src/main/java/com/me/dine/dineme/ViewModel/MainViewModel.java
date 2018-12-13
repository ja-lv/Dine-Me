package com.me.dine.dineme.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyEvent;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyGroup;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeRGroup;
import com.me.dine.dineme.ViewModel.Models.User;
import com.me.dine.dineme.ViewModel.Network.FirebaseAdapterCalls;

import java.util.List;

import com.google.firebase.auth.FirebaseUser;

//connect Repository later
public class MainViewModel extends AndroidViewModel implements FirebaseAdapterCalls.FirebaseLoader {
    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    //set live data
    private MutableLiveData<User> mUser;
    private MutableLiveData<String> mIsUserInDb;

    //actual data
    private User mMainUser;

    //firebase user and adapter
    private FirebaseUser mFirebaseUser;
    private FirebaseAdapterCalls mFirebaseAdapter;

    public MainViewModel(Application application, FirebaseUser firebaseUser){
        super(application);

        //firebase data
        mFirebaseUser = firebaseUser;
        mFirebaseAdapter = new FirebaseAdapterCalls(mFirebaseUser);
        getUser();
        getIsUserInDb();
    }

    public void setUser(User user){
        //update firebase
        mFirebaseAdapter.setUser(user, this);
    }

    public LiveData<User> getUser() {
        if (mUser == null) {
            mUser = new MutableLiveData<User>();
            loadUser();
        }
        return mUser;
    }

    public LiveData<String> getIsUserInDb() {
        if (mIsUserInDb == null) {
            mIsUserInDb = new MutableLiveData<String>();
            loadIsUserInDb(true);
        }
        return mIsUserInDb;
    }

    public void loadIsUserInDb(boolean b){
        if(b) mIsUserInDb.postValue("True");
        else mIsUserInDb.postValue("False");

        return;
    }

    public void loadUser(){
        Log.d("FBLoader", "fbloaderpasseed ");

        if(mMainUser != null){
            mUser.postValue(mMainUser);
        }

        DocumentReference ref = mFirebaseAdapter.getUser();
        if(ref == null){
            mUser.postValue(null);
            return;
        }

        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("FBLoader", "DocumentSnapshot data: " + document.getData());
                        mMainUser = document.toObject(User.class);
                        mUser.postValue(mMainUser);
                    } else {
                        Log.d("FBLoader", "No such document");
                        loadIsUserInDb(false);
                    }
                } else {
                    Log.d("FBLoader", "get failed with ", task.getException());
                }
            }
        });

        return;
    }

    public void setFirebaseUser(FirebaseUser mFirebaseUser) {
        this.mFirebaseUser = mFirebaseUser;
        mFirebaseAdapter.setFirebaseUser(mFirebaseUser);
    }


    @Override
    public void onSetUser() {
        Log.d("FBLoader", "SET CALLLEDD ");
        loadUser();
    }
}
