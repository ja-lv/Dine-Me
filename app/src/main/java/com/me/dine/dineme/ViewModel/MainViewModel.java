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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyEvent;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMyGroup;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeRGroup;
import com.me.dine.dineme.ViewModel.Models.Event;
import com.me.dine.dineme.ViewModel.Models.Group;
import com.me.dine.dineme.ViewModel.Models.User;
import com.me.dine.dineme.ViewModel.Network.FirebaseAdapterCalls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.firebase.auth.FirebaseUser;

//connect Repository later
public class MainViewModel extends AndroidViewModel implements FirebaseAdapterCalls.FirebaseLoader {
    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    //set live data
    private MutableLiveData<User> mUser;
    private MutableLiveData<String> mIsUserInDb;
    private MutableLiveData<List <Group>> mMyGroups;
    private MutableLiveData<List <Event>> mMyEvents;

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

    //groups functions
    public void setGroup(Group group){
        //update firebase
        mFirebaseAdapter.setGroup(group, this);
    }

    public LiveData<List<Group>> getMyGroups() {
        if (mMyGroups == null) {
            mMyGroups = new MutableLiveData<List<Group>>();
            loadMyGroups();
        }
        return mMyGroups;
    }

    public void loadMyGroups(){
        Query ref = mFirebaseAdapter.getMyGroups();
        if(ref == null){
            mMyGroups.postValue(null);
            return;
        }

        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Group> tempGroups = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("FBLoader", "SETTING UP GROUPS!!!!");
                        Log.d("FBLoader", document.getId() + " => " + document.getData().get("description"));

                        Group tempGroup = new Group(
                                (String) document.getData().get("ownerEmail"),
                                (String) document.getData().get("name"),
                                (String) document.getData().get("description"),
                                (Date) document.getData().get("date"),
                                (String) document.getData().get("imageUrl"),
                                (List<String>) document.getData().get("foods"),
                                (String) document.getData().get("location")
                                );
                        //set group data here, should had been uploaded to firestore
                        tempGroups.add(tempGroup);
                    }
                    mMyGroups.postValue(tempGroups);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    public void onSetGroup() {
        Log.d("FBLoader", "SET GROUP CALLLEDD ");
        loadMyGroups();
    }

    //set events
    //groups functions
    public void setEvent(Event event){
        //update firebase
        mFirebaseAdapter.setEvent(event, this);
    }

    public void onSetEvents(){
        loadMyEvents();
    }

    public LiveData<List<Event>> getMyEvents() {
        if (mMyEvents == null) {
            mMyEvents = new MutableLiveData<List<Event>>();
            loadMyEvents();
        }
        return mMyEvents;
    }

    public void loadMyEvents(){
        Query ref = mFirebaseAdapter.getMyEvents();
        if(ref == null){
            mMyEvents.postValue(null);
            return;
        }

        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Event> tempEvents = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("FBLoader", "SETTING UP EVENTS!!!!");
                        Log.d("FBLoader", document.getId() + " => " + document.getData().get("description"));

                        Event tempEvent = new Event(
                                (String) document.getData().get("groupName"),
                                (String) document.getData().get("name"),
                                (String) document.getData().get("description"),
                                (String) document.getData().get("date"),
                                (String) document.getData().get("imageUrl"),
                                (List<String>) document.getData().get("foods"),
                                (String) document.getData().get("location"),
                                (List<String>) document.getData().get("usersEmails")
                        );
                        //set group data here, should had been uploaded to firestore
                        tempEvents.add(tempEvent);
                        Log.d("FBLoader", document.getId() + " by!! => " + tempEvent.getGroupName());
                    }
                    mMyEvents.postValue(tempEvents);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

    }

}
