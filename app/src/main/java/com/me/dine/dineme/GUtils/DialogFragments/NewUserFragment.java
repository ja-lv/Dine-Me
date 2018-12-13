package com.me.dine.dineme.GUtils.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.me.dine.dineme.R;
import com.me.dine.dineme.ViewModel.Models.User;

public class NewUserFragment extends DialogFragment{
    //the following will run after it is gets all user information
    public interface NewUserListener {
        void onNewUser(User mainUser);
    }
    private NewUserListener mNewUserListener;

    public static final String TAG = "RatingDialog";

    @BindView(R.id.new_user_username)
    EditText mUsername;

    @BindView(R.id.new_user_description)
    EditText mDescription;

    @BindView(R.id.new_user_age)
    EditText mAge;

    @BindView(R.id.new_user_favorite_food)
    EditText mFood;

    @BindView(R.id.new_user_location)
    EditText mLocation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof NewUserListener) {
            mNewUserListener = (NewUserListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_new_user, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.new_user_confirm_signup)
    public void onSubmitClicked(View view) {

        FirebaseUser gUser =  FirebaseAuth.getInstance().getCurrentUser();

        User user = new User(
                mUsername.getText().toString(),
                gUser.getEmail(),
                mDescription.getText().toString(),
                Integer.parseInt(mAge.getText().toString()),
                mLocation.getText().toString(),
                gUser.getPhotoUrl().toString(),
                Lists.newArrayList(mFood.getText().toString()));

        if (mNewUserListener != null) {
            mNewUserListener.onNewUser(user);
        }

        dismiss();
    }

    @OnClick(R.id.new_user_cancel_signup)
    public void onCancelClicked(View view) {
        dismiss();
    }
}
