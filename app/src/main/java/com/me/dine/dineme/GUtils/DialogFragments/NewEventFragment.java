package com.me.dine.dineme.GUtils.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.me.dine.dineme.R;
import com.me.dine.dineme.ViewModel.Models.Event;
import com.me.dine.dineme.ViewModel.Models.Group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewEventFragment extends DialogFragment {

    public static final String TAG = "EventDialog";

    //the following will run after it is gets all user information
    public interface NewEventListener {
        List<Group> getMyGroups();
        void onNewEvent(Event newEvent);
    }

    private NewEventFragment.NewEventListener mNewEventListener;

    @BindView(R.id.new_event_options)
    Spinner mGroupOptions;

    @BindView(R.id.new_event_name)
    EditText mName;

    @BindView(R.id.new_event_description)
    EditText mDescription;

    @BindView(R.id.new_event_date)
    EditText mDate;

    @BindView(R.id.new_event_food)
    EditText mFood;

    @BindView(R.id.new_event_location)
    EditText mLocation;

    //list of my groups
    List<Group> mMyGroups;

    //setup

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof NewEventFragment.NewEventListener) {
            mNewEventListener = (NewEventFragment.NewEventListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_new_event, container, false);
        ButterKnife.bind(this, v);

        //set up the view model
        mMyGroups = mNewEventListener.getMyGroups();

        //setup the list of strings
        List<String> groupNames = new ArrayList<>();
        for(Group group: mMyGroups){
            groupNames.add(group.getName());
        }

//        //setup our Spinner options
//        mGroupOptions = container.findViewById(R.id.new_event_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, groupNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mGroupOptions.setAdapter(adapter);

        return v;
    }

    @OnClick(R.id.new_event_confirm_signup)
    public void onSubmitClicked(View view) {

        FirebaseUser gUser =  FirebaseAuth.getInstance().getCurrentUser();

        Event event = new Event(
                mGroupOptions.getSelectedItem().toString(),
                mName.getText().toString(),
                mDescription.getText().toString(),
                mDate.getText().toString(),
                gUser.getPhotoUrl().toString(),
                Lists.newArrayList(mFood.getText().toString()),
                mLocation.getText().toString(),
                Lists.newArrayList(gUser.getEmail()));

        if ( mNewEventListener != null) {
            mNewEventListener.onNewEvent(event);
        }

        dismiss();
    }

    @OnClick(R.id.new_event_cancel_signup)
    public void onCancelClicked(View view) {
        dismiss();
    }
}

