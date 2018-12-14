package com.me.dine.dineme.ViewModel.Network;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.Models.Event;
import com.me.dine.dineme.ViewModel.Models.Group;
import com.me.dine.dineme.ViewModel.Models.User;

import java.util.HashMap;
import java.util.Map;

public class FirebaseAdapterCalls {
    //interfaces for callbacks
    public interface FirebaseLoader {
        void onSetUser();
        void onSetGroup();
        void onSetEvents();
    }

    //folder_tags
    public final String USER_COLLECTION = "users";
    public final String GROUP_COLLECTION = "groups";
    public final String EVENT_COLLECTION = "events";
    public final String EVENT_USERS_COLLECTION = "event_users";
    //event id
    public final String EVENT_USERS_EVENT_ID = "event_id";
    public final String EVENT_USERS_USER_EMAIL = "user_email";

    public final String TAG = "firebase-adapter-logs";

//    FirebaseFirestore mDb;
    FirebaseUser mFirebaseUser;

    public FirebaseAdapterCalls(FirebaseUser user){
        mFirebaseUser = user;
    }

    public void setUser(User user, final FirebaseLoader vm){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("DineMe Firebase", user.getFoods().toString() + " Main User is: " + user.getEmail());
        db.collection(USER_COLLECTION).document(mFirebaseUser.getEmail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        vm.onSetUser();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public DocumentReference getUser(){
        if(mFirebaseUser == null) return null;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection(USER_COLLECTION).document(mFirebaseUser.getEmail());
    }

    public void setFirebaseUser(FirebaseUser mFirebaseUser) {
        this.mFirebaseUser = mFirebaseUser;
    }

    public void setGroup(Group group, final FirebaseLoader vm){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("DineMe Firebase", group.getFoods().toString());
        db.collection(GROUP_COLLECTION).document()
                .set(group)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        vm.onSetGroup();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public Query getMyGroups(){
        if(mFirebaseUser == null) return null;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection(GROUP_COLLECTION).whereEqualTo("ownerEmail", mFirebaseUser.getEmail());
    }

    //events
    public void setEvent(Event event, final FirebaseLoader vm){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("DineMe Firebase", event.getGroupName());
        db.collection(EVENT_COLLECTION).document()
                .set(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        vm.onSetEvents();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

//        Log.d(TAG, "DocumentSnapshot Event successfully written!");
//
//        //set hashmaps
//        Map<String, Object> userEvent = new HashMap<>();
//        userEvent.put(EVENT_USERS_EVENT_ID,documentReference.getId());
//        userEvent.put(EVENT_USERS_USER_EMAIL,mFirebaseUser.getEmail());
//
//        db.collection(EVENT_USERS_COLLECTION).document()
//                .set(userEvent)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully written!");
//                        vm.onSetEvents();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });
    }

//    public void getEventById(String eventId){
//        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference docRef = db.collection(EVENT_COLLECTION).document(eventId);
//    }

    public Query getMyEvents(){
        if(mFirebaseUser == null) return null;
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(EVENT_COLLECTION).whereArrayContains("usersEmails", mFirebaseUser.getEmail());
    }

    public Query getLatestEvents(){
        if(mFirebaseUser == null) return null;
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(EVENT_COLLECTION).orderBy("date", Query.Direction.DESCENDING);
    }
}
