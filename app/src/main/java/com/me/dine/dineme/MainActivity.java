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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.me.dine.dineme.Activities.MyGroupsActivity;
import com.me.dine.dineme.GUtils.DialogFragments.NewUserFragment;
import com.me.dine.dineme.GUtils.FirebaseAuthUtils;
import com.me.dine.dineme.ViewModel.LocalDatabase.DBClasses.DineMeMainUser;
import com.me.dine.dineme.ViewModel.MainViewModel;
import com.me.dine.dineme.ViewModel.Models.User;
import com.me.dine.dineme.ViewModel.ViewModelFactory;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NewUserFragment.NewUserListener {

    //info for firebase
    //sign in
    private static final int RC_SIGN_IN = 123;
    FirebaseUser mUser;
    MainViewModel mViewModel;
    User mMainUser;

    //activity variables
    private TextView mTextMessage;

    //butterknife data
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
                    startMyGroupActivity();
                    return true;
                case R.id.navigation_events:
                    if(mMainUser != null){
                        setUserHome();
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

        ButterKnife.bind(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //init fragment
        mNewUserDialog = new NewUserFragment();

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
                    setUserHome();
                    Log.d("FBLoader", "OBSERRVED RUNNING DocumentSnapshot data 2: " + mainUser.getDescription());
                }
            }
        });

        //check if user is in database or not
        mViewModel.getIsUserInDb().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String trueFalse) {
                Log.d("FBLoader", "OBSERRVED STROLLIN: ");
                if(trueFalse == "False") {
                    mNewUserDialog.show(getSupportFragmentManager(), mNewUserDialog.TAG);
                }
            }
        });
    }

    //firebase authenticate calls this
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUser = FirebaseAuthUtils.getUserFromActivityResult(this, requestCode, resultCode, data);
        loadModel();

        Log.d("Model-check ", "");
//        //create a user if there is no main user
//        if(mUser != null){
//            mNewUserDialog.show(getSupportFragmentManager(), mNewUserDialog.TAG);
//        }
//        else if(mUser != null){
//            mTextMessage.setText("Your email is: " + mUser.getEmail());
//        }
    }

    private void loadModel(){
        mViewModel.setFirebaseUser(mUser);
        mViewModel.loadUser();
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
    public void onNewUser(User newUser) {
        //call the view model to initialize a new user
        mViewModel.setUser(newUser);
    }

    //user homepage
    public void setUserHome(){
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

    public void startMyGroupActivity(){
        if(mMainUser == null) return;
        // Go to the details page for the selected restaurant
        Intent intent = new Intent(this, MyGroupsActivity.class);
        intent.putExtra(MyGroupsActivity.KEY_OWNER_EMAIL, mMainUser.getEmail());
        startActivity(intent);
    }
}
