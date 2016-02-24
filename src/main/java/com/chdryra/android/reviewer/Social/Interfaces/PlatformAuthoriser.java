/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 14/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PlatformAuthoriser {
    void seekAuthorisation(SocialPlatform<?> platform, AuthorisationListener listener);

    interface AuthorisationListener {
        void onAuthorisationGiven(SocialPlatform<?> platform);

        void onAuthorisationRefused(SocialPlatform<?> platform);
    }
}