/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;



import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterSignUp;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.SignUpArgs;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentSignUp extends Fragment implements PresenterSignUp.SignUpListener {
    private static final String ARGS = TagKeyGenerator.getKey(FragmentSignUp.class, "Args");

    private static final int LAYOUT = R.layout.fragment_sign_up;

    private static final int EMAIL_SIGN_UP = R.id.email_sign_up;
    private static final int EMAIL_EDIT_TEXT = R.id.edit_text_sign_up_email;
    private static final int PASSWORD_EDIT_TEXT = R.id.edit_text_sign_up_password;
    private static final int PASSWORD_CONFIRM_EDIT_TEXT = R.id.edit_text_confirm_password;

    private static final int NAME_EDIT_TEXT = R.id.edit_text_author_name;

    private static final int SIGN_UP_BUTTON = R.id.sign_up_button;

    private PresenterSignUp mPresenter;
    private AuthenticatedUser mUser;

    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;

    public static FragmentSignUp newInstance(@Nullable SignUpArgs args) {
        FragmentSignUp fragment = new FragmentSignUp();
        if(args != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ARGS, args);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        LinearLayout emailSignUp = (LinearLayout) view.findViewById(EMAIL_SIGN_UP);
        mEmail = (EditText) emailSignUp.findViewById(EMAIL_EDIT_TEXT);
        mPassword = (EditText) emailSignUp.findViewById(PASSWORD_EDIT_TEXT);
        mPasswordConfirm = (EditText) emailSignUp.findViewById(PASSWORD_CONFIRM_EDIT_TEXT);

        mName = (EditText) view.findViewById(NAME_EDIT_TEXT);

        initWithArgs(emailSignUp);

        Button signUpButton = (Button) view.findViewById(SIGN_UP_BUTTON);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndRequestCreateUser();
            }
        });

        return view;
    }

    private void initWithArgs(LinearLayout emailSignUp) {
        Bundle bundle = getArguments();
        if(bundle != null) {
            SignUpArgs args = bundle.getParcelable(ARGS);
            if (args != null) {
                if (args.isEmailPassword()) {
                    EmailAddress emailAddress = args.getEmail();
                    if (emailAddress != null) mEmail.setText(emailAddress.toString());
                } else {
                    mUser = args.getUser();
                    if(mUser == null) throw new IllegalStateException("User should not be null!");
                    String text = mUser.getProvider() + " log in";
                    mEmail.setText(text);
                    emailSignUp.setActivated(false);
                }
            }
        }
    }

    @Override
    public void onSignUpComplete(AuthorProfile profile, @Nullable AuthenticationError error) {
        if(error != null) {
            makeToast(error.getMessage());
        } else {
            mPresenter.onSignUpSuccessful(profile, getActivity());
            getActivity().finish();
        }
    }

    private void validateAndRequestCreateUser() {
        String name = mName.getText().toString();
        if(mUser != null) {
            mPresenter.signUpNewAuthor(mUser, name);
        } else {
            String password = mPassword.getText().toString();
            String passwordConfirm = mPasswordConfirm.getText().toString();
            if(!password.equals(passwordConfirm)) {
                makeToast("Passwords don't match");
                return;
            }

            String email = mEmail.getText().toString();
            mPresenter.signUpNewAuthor(email, password, name);
        }
    }

    private void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
