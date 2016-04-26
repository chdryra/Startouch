/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.PluginTemp;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation.FirebaseBackend;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.EmailLoginImpl;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailLoginCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseEmailLogin extends EmailLoginImpl {
    private static final String NAME = "Firebase";

    public FirebaseEmailLogin(EmailPassword getter) {
        super(NAME, getter);
    }

    @Override
    public void requestCredentials(final EmailLoginCallback callback) {
        Firebase ref = new Firebase(FirebaseBackend.ROOT);
        ref.authWithPassword(getEmail().toString(), getPassword().toString(), getHandler(callback));
    }

    @NonNull
    private Firebase.AuthResultHandler getHandler(final EmailLoginCallback callback) {
        return new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                callback.onSuccess(new DatumUserId(authData.getUid()));
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                callback.onFailure(getError(firebaseError));
            }
        };
    }

    private AuthenticationError getError(FirebaseError error) {
        if(error.getCode() == FirebaseError.EMAIL_TAKEN) {
            return new AuthenticationError(NAME, AuthenticationError.Reason.EMAIL_TAKEN);
        } else if(error.getCode() == FirebaseError.INVALID_EMAIL){
            return new AuthenticationError(NAME, AuthenticationError.Reason.INVALID_EMAIL);
        } else if(error.getCode() == FirebaseError.INVALID_PASSWORD){
            return new AuthenticationError(NAME, AuthenticationError.Reason.INVALID_PASSWORD);
        } else if(error.getCode() == FirebaseError.USER_DOES_NOT_EXIST){
            return new AuthenticationError(NAME, AuthenticationError.Reason.UNKNOWN_USER);
        } else if(error.getCode() == FirebaseError.INVALID_CREDENTIALS){
            return new AuthenticationError(NAME, AuthenticationError.Reason.INVALID_CREDENTIALS);
        } else if(error.getCode() == FirebaseError.DISCONNECTED){
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else if(error.getCode() == FirebaseError.MAX_RETRIES){
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else if(error.getCode() == FirebaseError.NETWORK_ERROR){
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else if(error.getCode() == FirebaseError.UNAVAILABLE){
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else {
            return new AuthenticationError(NAME, AuthenticationError.Reason.PROVIDER_ERROR, error.getDetails());
        }
    }

}
