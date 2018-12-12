package com.me.dine.dineme.ViewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private FirebaseUser mFirebaseUser;


    public ViewModelFactory(Application application, FirebaseUser firebaseUser) {
        mApplication = application;
        mFirebaseUser = firebaseUser;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainViewModel(mApplication, mFirebaseUser);
    }
}
