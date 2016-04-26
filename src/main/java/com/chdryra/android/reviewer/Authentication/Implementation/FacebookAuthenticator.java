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
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLoginCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FacebookAuthenticator extends AuthenticationHandlerBasic<FacebookLoginCallback>
        implements FacebookLoginCallback {
    public FacebookAuthenticator(FacebookLogin provider, AuthenticatorCallback callback) {
        super(provider, callback);
    }

    @Override
    public void onSuccess(LoginResult result) {
        onSuccess(getProviderName(), CallbackMessage.ok("Facebook login successful"));
    }

    @Override
    public void onFailure(FacebookException result) {
        onFailure(getProviderName(), CallbackMessage.error("Facebook login unsuccessful: " + result.getMessage()));
    }

    @Override
    protected FacebookLoginCallback getProviderCallback() {
        return this;
    }
}
