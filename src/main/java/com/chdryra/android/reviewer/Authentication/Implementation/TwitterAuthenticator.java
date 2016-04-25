/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLoginCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TwitterAuthenticator extends AuthenticationHandlerBasic<TwitterLoginCallback>
        implements TwitterLoginCallback {
    public TwitterAuthenticator(TwitterLogin provider, AuthenticatorCallback callback) {
        super(provider, callback);
    }

    @Override
    protected TwitterLoginCallback getProviderCallback() {
        return this;
    }

    @Override
    public void onSuccess(CallbackMessage result) {

    }

    @Override
    public void onFailure(CallbackMessage result) {

    }
}
