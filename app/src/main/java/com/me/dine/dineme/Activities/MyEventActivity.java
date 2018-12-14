package com.me.dine.dineme.Activities;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.me.dine.dineme.GUtils.DialogFragments.NewGroupFragment;
import com.me.dine.dineme.GUtils.FirebaseAuthUtils;
import com.me.dine.dineme.MainActivity;
import com.me.dine.dineme.R;
import com.me.dine.dineme.ViewModel.MainViewModel;
import com.me.dine.dineme.ViewModel.Models.Event;
import com.me.dine.dineme.ViewModel.Models.Group;
import com.me.dine.dineme.ViewModel.Models.User;
import com.me.dine.dineme.ViewModel.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyEventActivity extends AppCompatActivity {

    //info for firebase
    //sign in
    private static final int RC_SIGN_IN = 123;
    FirebaseUser mUser;
    MainViewModel mViewModel;
    User mMainUser;
    List<Event> mMyEvents;
    Event mTestEvent;

    //butterknife group
    @BindView(R.id.event_groupname)
    TextView mOwner;

    @BindView(R.id.event_description)
    TextView mDescription;

    @BindView(R.id.event_date)
    TextView mDate;

    @BindView(R.id.event_food)
    TextView mFood;

    @BindView(R.id.event_location)
    TextView mLocation;

    @BindView(R.id.event_image)
    ImageView mImage;

    @BindView(R.id.no_events)
    TextView mNoEvents;


    //nav listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                        startMyPageActivity();
                    return true;
                case R.id.navigation_dashboard:
                    startMyGroupActivity();
                    return true;
                case R.id.navigation_events:
                    if(mTestEvent != null){
                        setEventInfo();
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

    //navigation
    BottomNavigationView mNavigation;

    @Override
    protected void onStart() {
        super.onStart();
        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigation.getMenu().getItem(2).setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
        ButterKnife.bind(this);

        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //init fragment
//        mNewGroupDialog = new NewGroupFragment();

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

//        mViewModel.getMyGroups().observe(this, new Observer<List<Group>>() {
//            @Override
//            public void onChanged(@Nullable final List<Group> groups) {
//                Log.d("FBLoader", "OBSERRVED RUNNING ON MY GROUPSSS:~~~~ ");
//                // Update the cached copy of the words in the adapter.
//                if(groups != null){
//                    mMyGroups = groups;
//                    mTestGroup = groups.get(0);
//                    if(mTestGroup != null) setGroupInfo();
//                }
//            }
//        });

        setEventInfo();
    }

    private void loadModel(){
        mViewModel.setFirebaseUser(mUser);
        mViewModel.loadUser();
//        mViewModel.loadMyEvents();
    }

    //user homepage
    public void setEventInfo(){
        //if my group is null, setup the dialog
        if(mMyEvents == null){
            mNoEvents.setText("No events available. Click the button to add an event.");
            return;
        }
        else{
            mNoEvents.setText("");
        }
//
//        //Date
//        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//
//        mOwner.setText(mTestGroup.getOwnerEmail());
//        mDescription.setText(mTestGroup.getDescription());
//        mDate.setText(df.format(mTestGroup.getDate()));
//        mFood.setText(mTestGroup.getFoods().get(0));
//        mLocation.setText(mTestGroup.getLocation());
//        if(mTestGroup.getImageUrl() != null){
//            Picasso.get()
//                    .load(mTestGroup.getImageUrl())
//                    .into(mImage);
//        }
    }

    @OnClick(R.id.create_event_btn)
    public void onCreateClicked(View view) {
        mNewEventDialog.show(getSupportFragmentManager(), mNewEventDialog.TAG);
    }

    private void signOut(){
        FirebaseAuthUtils.firebaseAuthSignOut(this);
        mUser = null;
        finish();
    }

    public void startMyPageActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startMyGroupActivity(){
        if(mMainUser == null) return;
        // Go to the details page for the selected restaurant
        Intent intent = new Intent(this, MyGroupsActivity.class);
        intent.putExtra(MyGroupsActivity.KEY_OWNER_EMAIL, mMainUser.getEmail());
        startActivity(intent);
    }

}
