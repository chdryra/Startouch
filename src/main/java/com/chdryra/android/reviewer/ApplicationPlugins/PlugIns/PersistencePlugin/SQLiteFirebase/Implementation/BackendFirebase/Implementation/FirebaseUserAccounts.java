/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.UserProfileConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories.FactoryUserAccount;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Implementation.UserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.Utils.Password;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseUserAccounts implements UserAccounts {
    private static final DbUpdater.UpdateType INSERT_OR_UPDATE
            = DbUpdater.UpdateType.INSERT_OR_UPDATE;
    private static final AuthenticationError NAME_TAKEN_ERROR =
            new AuthenticationError(ApplicationInstance.APP_NAME, AuthenticationError.Reason
                    .NAME_TAKEN);
    private static final AuthenticationError UNKNOWN_USER_ERROR = new AuthenticationError
            (FirebaseBackend.NAME, AuthenticationError.Reason.UNKNOWN_USER);

    private Firebase mDataRoot;
    private FbUsersStructure mStructure;
    private UserProfileConverter mConverter;
    private FactoryUserAccount mAccountFactory;

    public FirebaseUserAccounts(Firebase dataRoot,
                                FbUsersStructure structure,
                                UserProfileConverter converter,
                                FactoryUserAccount accountFactory) {
        mDataRoot = dataRoot;
        mStructure = structure;
        mConverter = converter;
        mAccountFactory = accountFactory;
    }

    @Override
    public void createUser(EmailPassword emailPassword, UserAccounts.CreateUserCallback callback) {
        EmailAddress email = emailPassword.getEmail();
        Password password = emailPassword.getPassword();
        mDataRoot.createUser(email.toString(), password.toString(), createUserCallback(callback));
    }

    @Override
    public AuthorProfile newProfile(String name) {
        return mConverter.newProfile(name);
    }

    @Override
    public void createAccount(final AuthenticatedUser authUser, final AuthorProfile profile,
                              final CreateAccountCallback callback) {
        Firebase db = mStructure.getAuthorNameMappingDb(mDataRoot, profile.getAuthor().getName());
        doSingleEvent(db, createAccountIfNoNameConflict(authUser, profile, callback));
    }

    @Override
    public void getAccount(final AuthenticatedUser authUser, final GetAccountCallback callback) {
        Firebase db = mStructure.getUserAuthorMappingDb(mDataRoot, authUser.getProvidersId());
        String authorId = authUser.getAuthorId();
        if (authorId != null) {
            callback.onAccount(getUserAccount(authUser), null);
        } else {
            doSingleEvent(db, getAuthorIdThenAccount(authUser, callback));
        }
    }

    @NonNull
    private ValueEventListener getAuthorIdThenAccount(final AuthenticatedUser authUser, final
    GetAccountCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String authorId = (String) dataSnapshot.getValue();
                if (authorId == null) {
                    callback.onAccount(mAccountFactory.newNullAccount(), UNKNOWN_USER_ERROR);
                } else {
                    authUser.setAuthorId(authorId);
                    callback.onAccount(getUserAccount(authUser), null);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onAccount(mAccountFactory.newNullAccount(), newError(firebaseError));
            }
        };
    }

    @NonNull
    private UserAccount getUserAccount(@Nullable AuthenticatedUser user) {
        return user == null ? mAccountFactory.newNullAccount() :
                mAccountFactory.newAccount(user, mDataRoot, mStructure, mConverter);
    }

    @NonNull
    private ValueEventListener createAccountIfNoNameConflict(final AuthenticatedUser authUser, final
    AuthorProfile profile, final CreateAccountCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    addNewProfile(authUser, profile, callback);
                } else {
                    callback.onAccountCreated(getUserAccount(null), NAME_TAKEN_ERROR);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onAccountCreated(getUserAccount(null), newError(firebaseError));
            }
        };
    }

    private void addNewProfile(final AuthenticatedUser authUser, final AuthorProfile profile,
                               final CreateAccountCallback callback) {
        final User user = mConverter.toUser(authUser, profile);
        DbUpdater<User> usersUpdater = mStructure.getUsersUpdater();
        Map<String, Object> map = usersUpdater.getUpdatesMap(user, INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                authUser.setAuthorId(profile.getAuthor().getAuthorId().toString());
                callback.onAccountCreated(getUserAccount(authUser), newError(firebaseError));
            }
        });
    }


    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }

    @NonNull
    private Firebase.ValueResultHandler<Map<String, Object>>
    createUserCallback(final CreateUserCallback callback) {
        return new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                callback.onUserCreated(mConverter.newAuthenticatedUser(FirebaseBackend.NAME,
                        (String) result.get("uid")), null);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                callback.onUserCreated(mConverter.newNullAuthenticatedUser(), newError
                        (firebaseError));
            }
        };
    }

    @Nullable
    private AuthenticationError newError(@Nullable FirebaseError firebaseError) {
        return firebaseError == null ? null : FirebaseBackend.authenticationError(firebaseError);
    }
}
