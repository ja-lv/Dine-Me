package com.me.dine.dineme.GUtils.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.me.dine.dineme.R;
import com.me.dine.dineme.ViewModel.Models.Group;
import com.me.dine.dineme.ViewModel.Models.User;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewGroupFragment extends DialogFragment {

    //the following will run after it is gets all user information
    public interface NewGroupListener {
        void onNewGroup(Group newGroup);
    }

    private NewGroupFragment.NewGroupListener mNewGroupListener;

    public static final String TAG = "GroupDialog";

    public static final String DEFAULT_URL = "";

    @BindView(R.id.new_group_name)
    EditText mName;

    @BindView(R.id.new_group_description)
    EditText mDescription;

    @BindView(R.id.new_group_favorite_food)
    EditText mFood;

    @BindView(R.id.new_group_location)
    EditText mLocation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof NewGroupFragment.NewGroupListener) {
            mNewGroupListener = (NewGroupFragment.NewGroupListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_new_group, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.new_group_confirm_signup)
    public void onSubmitClicked(View view) {

        FirebaseUser gUser =  FirebaseAuth.getInstance().getCurrentUser();

        Group group = new Group(
                gUser.getEmail(),
                mName.getText().toString(),
                mDescription.getText().toString(),
                new Date(),
                gUser.getPhotoUrl().toString(),
                Lists.newArrayList(mFood.getText().toString()),
                mLocation.getText().toString());

        if (mNewGroupListener != null) {
            mNewGroupListener.onNewGroup(group);
        }

        dismiss();
    }

    @OnClick(R.id.new_group_cancel_signup)
    public void onCancelClicked(View view) {
        dismiss();
    }
}
