/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailLoginCallback;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailAuthenticator extends CredentialsHandlerBasic<EmailLoginCallback>
        implements EmailLoginCallback {
    public EmailAuthenticator(EmailLogin provider, AuthenticatorCallback callback) {
        super(provider, callback);
    }

    @Override
    protected EmailLoginCallback getProviderCallback() {
        return this;
    }

    @Override
    public void onSuccess(UserId result) {
        notifyOnSuccess(getProviderName());
    }

    @Override
    public void onFailure(AuthenticationError error) {
        notifyOnFailure(getProviderName(), error);
    }
}
