package com.me.dine.dineme.ViewModel.Network;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.Models.User;

import java.util.HashMap;
import java.util.Map;

public class FirebaseAdapterCalls {
    //interfaces for callbacks
    public interface FirebaseLoader {
        void onSetUser();
    }

    //folder_tags
    public final String USER_COLLECTION = "users";
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
}
