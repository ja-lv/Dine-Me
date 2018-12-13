package com.me.dine.dineme.ViewModel.Network;

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
    //folder_tags
    public final String USER_COLLECTION = "users";
    public final String TAG = "firebase-adapter-logs";

//    FirebaseFirestore mDb;
    FirebaseUser mUser;

    public FirebaseAdapterCalls(FirebaseUser user){
        mUser = user;
    }

    public void setUser(User user){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("DineMe Firebase", user.getFoods().toString() + " Main User is: " + user.getEmail());
        db.collection(USER_COLLECTION).document(mUser.getEmail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
