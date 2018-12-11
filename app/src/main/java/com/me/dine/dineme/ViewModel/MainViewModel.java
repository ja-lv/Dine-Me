package com.me.dine.dineme.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private DineMeRepository mDineMeRepository;

    //items we will work with
    private LiveData<DineMeMainUser> mainUser;

    public MainViewModel(Application application){
        super(application);
        mDineMeRepository = new DineMeRepository(application);
    }
}
