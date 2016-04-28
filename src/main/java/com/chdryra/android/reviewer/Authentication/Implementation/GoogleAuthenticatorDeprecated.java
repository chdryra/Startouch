/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Authentication.Interfaces.Authenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsCallback;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleAuthenticatorDeprecated implements CredentialsCallback<GoogleSignInAccount> {
    private final Context mContext;
    private final Authenticator mAuthenticator;
    private final AuthenticatorCallback mCallback;
    private final UserRecoverableExceptionHandler mHandler;

    public interface UserRecoverableExceptionHandler {
        void onAuthorisationRequired(String provider, GoogleSignInAccount credentials,
                                     UserRecoverableAuthException e);
    }

    public GoogleAuthenticatorDeprecated(Context context, Authenticator authenticator,
                                         AuthenticatorCallback callback, UserRecoverableExceptionHandler handler) {
        mContext = context;
        mAuthenticator = authenticator;
        mCallback = callback;
        mHandler = handler;
    }

    @Override
    public void onCredentialsObtained(String provider, GoogleSignInAccount credentials) {
        new TokenGetter(provider, credentials).execute();
    }

    @Override
    public void onCredentialsFailure(String provider, AuthenticationError error) {
        mCallback.onFailure(error);
    }

    private void doAuthentication(String provider, @Nullable String token) {
//        if (token != null) {
//            mAuthenticator.authenticateGoogleCredentials(token, mCallback);
//        } else {
//            onCredentialsFailure(provider, new AuthenticationError(provider,
//                    AuthenticationError.Reason.INVALID_CREDENTIALS));
//        }
    }

    private class TokenGetter extends AsyncTask<Void, Void, String> {
        private String mProvider;
        private GoogleSignInAccount mAccount;
        private boolean mAuthorisationRequired = false;

        public TokenGetter(String provider, GoogleSignInAccount account) {
            mProvider = provider;
            mAccount = account;
        }

        @Override
        @Nullable
        protected String doInBackground(Void... params) {
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(mContext, mAccount.getEmail(), scopes);
            } catch (UserRecoverableAuthException e) {
                mAuthorisationRequired = true;
                mHandler.onAuthorisationRequired(mProvider, mAccount, e);
            } catch (IOException | GoogleAuthException e) {
                e.printStackTrace();
            }

            return token;
        }

        @Override
        protected void onPostExecute(String token) {
            if(!mAuthorisationRequired) doAuthentication(mProvider, token);
        }
    }
}
