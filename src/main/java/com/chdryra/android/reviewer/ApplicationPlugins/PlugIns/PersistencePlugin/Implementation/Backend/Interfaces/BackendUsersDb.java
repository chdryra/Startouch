/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface BackendUsersDb {
    interface CreateUserCallback {
        void onUserCreated(User user);

        void onUserCreationError(AuthenticationError error);
    }

    interface AddProfileCallback {
        void onProfileAdded(User user);

        void onProfileAddedError(AuthenticationError error);
    }

    interface GetProfileCallback {
        void onProfile(Profile profile);

        void onProfileError(AuthenticationError error);
    }

    interface UpdateProfileCallback {
        void onProfileUpdated(User user);

        void onProfileUpdatedError(AuthenticationError error);
    }

    String getProviderName();

    void createUser(EmailPassword emailPassword, CreateUserCallback callback);

    void addProfile(User user, AddProfileCallback callback);

    void getProfile(User user, GetProfileCallback callback);

    void updateProfile(User user, UpdateProfileCallback callback);
}