package com.me.dine.dineme.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.me.dine.dineme.GUtils.DialogFragments.NewUserFragment;
import com.me.dine.dineme.GUtils.FirebaseAuthUtils;
import com.me.dine.dineme.R;
import com.me.dine.dineme.ViewModel.MainViewModel;
import com.me.dine.dineme.ViewModel.Models.Group;
import com.me.dine.dineme.ViewModel.Models.User;
import com.me.dine.dineme.ViewModel.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyGroupsActivity extends AppCompatActivity {
//    mBtmView.getMenu().findItem(R.id.action_yoga).setChecked(true);

    //info for firebase
    //sign in
    private static final int RC_SIGN_IN = 123;
    FirebaseUser mUser;
    MainViewModel mViewModel;
    User mMainUser;
    List<Group> mMyGroups;

    //butterknife group
    @BindView(R.id.user_username)
    TextView mUsername;

    @BindView(R.id.user_description)
    TextView mDescription;

    @BindView(R.id.user_age)
    TextView mAge;

    @BindView(R.id.user_food)
    TextView mFood;

    @BindView(R.id.user_location)
    TextView mLocation;

    @BindView(R.id.user_image)
    ImageView mImage;

    //nav listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(mMainUser != null){
                        setGroupInfo();
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if(mMainUser != null){
                        setGroupInfo();
                    }
                    return true;
                case R.id.navigation_events:
                    if(mMainUser != null){
                        setGroupInfo();
                    }
                    return true;
                case R.id.navigation_signout:
                    //sign us out
                    signOut();
                    return true;
            }
            return false;
        }
    };

    //dialogs
    NewUserFragment mNewUserDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);
        ButterKnife.bind(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //init fragment
        mNewUserDialog = new NewUserFragment();

        //set main user
        //set user
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        //setup modelview
        mViewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getApplication(), mUser)).get(MainViewModel.class);
        loadModel();

        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable final User mainUser) {
                Log.d("FBLoader", "OBSERRVED RUNNING RAAAAAAAAAAAAAAAAAAAA: ");
                // Update the cached copy of the words in the adapter.
                mMainUser = mainUser;
                if(mMainUser != null){
//                    setUserHome();
                    Log.d("FBLoader", "OBSERRVED RUNNING DocumentSnapshot data 2: " + mainUser.getDescription());
                }
            }
        });

//        mViewModel.getMyGroups();
    }

    //user homepage
    public void setGroupInfo(){
        mUsername.setText(mMainUser.getUsername());
        mDescription.setText(mMainUser.getDescription());
        mAge.setText(Integer.toString(mMainUser.getAge()));
        mFood.setText(mMainUser.getFoods().get(0));
        mLocation.setText(mMainUser.getLocation());
        if(mMainUser.getPhotoUrl() != null){
            Picasso.get()
                    .load(mMainUser.getPhotoUrl())
                    .into(mImage);
        }
    }

    private void loadModel(){
        mViewModel.setFirebaseUser(mUser);
        mViewModel.loadUser();
    }

    private void signOut(){
        FirebaseAuthUtils.firebaseAuthSignOut(this);
        mUser = null;
        finish();
    }
}