/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.DialogShower;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentGoogleLogin extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final int LAYOUT = R.layout.login_google;
    private static final int LOGIN = R.id.login_button_google;

    private static final int SIGN_IN = RequestCodeGenerator.getCode("SignIn");
    private static final int REQUEST_RESOLVE_ERROR = RequestCodeGenerator.getCode("ResolveError");
    private static final String DIALOG_ERROR = "dialog_error";
    private boolean mResolvingError = false;

    private CallbackManager mCallbackManager;
    private GoogleSignInOptions mOptions;
    private GoogleLoginListener mListener;
    private GoogleApiClient mGoogleApiClient;

    public interface GoogleLoginListener {
        void onSuccess(GoogleSignInResult result);
        void onFailure(GoogleSignInResult result);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, mOptions)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        try {
            mListener = (GoogleLoginListener) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("Activity should be a FacebookLoginListener!", e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        if (!mResolvingError) {
            if (result.hasResolution()) {
                try {
                    mResolvingError = true;
                    result.startResolutionForResult(getActivity(), REQUEST_RESOLVE_ERROR);
                } catch (IntentSender.SendIntentException e) {
                    mGoogleApiClient.connect();
                }
            } else {
                showErrorDialog(result.getErrorCode());
                mResolvingError = true;
            }
        }
    }

    private void showErrorDialog(int errorCode) {
        ErrorDialogFragment dialog = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialog.setTargetFragment(this, REQUEST_RESOLVE_ERROR);
        DialogShower.show(dialog, getActivity(), REQUEST_RESOLVE_ERROR, DIALOG_ERROR, args);
    }

    public void onDialogDismissed() {
        mResolvingError = false;
    }

    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((FragmentGoogleLogin)getTargetFragment()).onDialogDismissed();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);

        SignInButton button = (SignInButton) view.findViewById(LOGIN);
        button.setSize(SignInButton.SIZE_STANDARD);
        button.setScopes(mOptions.getScopeArray());

        button.setOnClickListener(newSignInListener());

        return view;
    }

    private View.OnClickListener newSignInListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        };
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == Activity.RESULT_OK) {
                if (!mGoogleApiClient.isConnecting() && !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        }

        if (requestCode == SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            mListener.onSuccess(result);
        } else {
            mListener.onFailure(result);
        }
    }
}
