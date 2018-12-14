package com.me.dine.dineme.GUtils.DialogFragments;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.Spinner;

import com.me.dine.dineme.R;
import com.me.dine.dineme.ViewModel.Models.Event;
import com.me.dine.dineme.ViewModel.Models.Group;

import butterknife.BindView;

public class NewEventFragment extends DialogFragment {

    //the following will run after it is gets all user information
    public interface NewEventListener {
        void onNewGroup(Event newEvent);
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

    private NewGroupFragment.NewGroupListener mNewGroupListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof NewEventFragment.NewEventListener) {
            mNewEventListener = (NewEventFragment.NewEventListener) context;
        }
    }
}

