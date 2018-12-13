package com.me.dine.dineme;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

//gen java
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//firebase auth and ui
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//our classes
import com.me.dine.dineme.GUtils.DialogFragments.NewUserFragment;
import com.me.dine.dineme.GUtils.FirebaseAuthUtils;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.MainViewModel;
import com.me.dine.dineme.ViewModel.ViewModelFactory;

public class MainActivity extends AppCompatActivity implements NewUserFragment.NewUserListener {

    //info for firebase
    //sign in
    private static final int RC_SIGN_IN = 123;
    FirebaseUser mUser;
    MainViewModel mViewModel;
    DineMeMainUser mMainUser;

    //activity variables
    private TextView mTextMessage;

    //butterknife data

    //dialogs
    NewUserFragment mNewUserDialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(mMainUser != null){
                        setUserHome();
                    }
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_signout:
                    mTextMessage.setText(R.string.title_signout);
                    //sign us out
                    signOut();
                    return true;
            }
            return false;
        }
    };

    //on start
    @Override
    public void onStart() {
        super.onStart();

        // Start sign in if necessary
        if (FirebaseAuthUtils.shouldStartSignIn()) {
            signIn();
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewModelSetup();

        //init fragment
        mNewUserDialog = new NewUserFragment();
    }

    //firebase authenticate calls this
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUser = FirebaseAuthUtils.getUserFromActivityResult(this, requestCode, resultCode, data);

        Log.d("Model-check ", "");
        //create a user if there is no main user
        if(mUser != null && mViewModel.getMainUser() == null){
            mNewUserDialog.show(getSupportFragmentManager(), mNewUserDialog.TAG);
        }
        else if(mUser != null){
            mTextMessage.setText("Your email is: " + mUser.getEmail());
        }
    }

    private void viewModelSetup(){
        //setup modelview
        mViewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getApplication(), mUser)).get(MainViewModel.class);
        if(mViewModel.getMainUser() == null) return;
        mMainUser = mViewModel.getMainUser().getValue();
        mViewModel.getMainUser().observe(this, new Observer<DineMeMainUser>() {
            @Override
            public void onChanged(@Nullable final DineMeMainUser user) {
                // Update the cached copy of the member var.
                mMainUser = user;
            }
        });
    }

    private void signIn(){
        FirebaseAuthUtils.firebaseAuthSignIn(this);
    }

    private void signOut(){
        FirebaseAuthUtils.firebaseAuthSignOut(this);
        mUser = null;
        finish();
    }

    //setup dialog interface for new user
    @Override
    public void onNewUser(DineMeMainUser mainUser) {
        //call the view model to initialize a new user
        mViewModel.initNewUser(mainUser);
    }

    //user homepage
    public void setUserHome(){
        mTextMessage.setText("Your email is: " + mMainUser.getGmail());
    }
}
