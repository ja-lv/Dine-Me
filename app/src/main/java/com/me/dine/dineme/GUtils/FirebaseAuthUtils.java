package com.me.dine.dineme.GUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class FirebaseAuthUtils {
    //info for firebase
    //sign in
    private static final int RC_SIGN_IN = 123;

    public static boolean shouldStartSignIn() {
        return (FirebaseAuth.getInstance().getCurrentUser() == null);
    }

    public static void firebaseAuthSignIn(Activity activity){
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    public static FirebaseUser getUserFromActivityResult(Activity activity, int requestCode, int resultCode, Intent data){
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == activity.RESULT_OK) {
                Log.d("signin", "signin occured!!");

                // Successfully signed in
                return FirebaseAuth.getInstance().getCurrentUser();

            } else {
                Log.d("signin", "signin failure!!");
            }
        }

        return null;
    }

    public static void firebaseAuthSignOut(final Activity activity){
        AuthUI.getInstance()
                .signOut(activity)
                .addOnCompleteListener(new OnCompleteListener<Void>() { //possibly make this an input
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        activity.finish();
                    }
                });
        Log.d("signout", "USER SIGNED OUT!!");

    }
}
