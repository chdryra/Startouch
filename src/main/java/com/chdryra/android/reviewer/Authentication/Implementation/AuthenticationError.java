/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthenticationError {
    public enum Reason {
        PROVIDER_ERROR("Authenticator error"),
        INVALID_EMAIL("Email doesn't make sense"),
        INVALID_PASSWORD("Password is invalid"),
        INVALID_CREDENTIALS("Credentials are invalid"),
        AUTHORISATION_REFUSED("Authorisation refused"),
        UNKNOWN_USER("Unknown user"),
        EMAIL_TAKEN("Email is already taken"),
        NETWORK_ERROR("Internet is having a rest"),
        UNKNOWN_ERROR("Beats me...");


        private String mMessage;

        Reason(String message) {
            mMessage = message;
        }

        public String getMessage() {
            return mMessage;
        }
    }

    private String mProvider;
    private Reason mReason;
    private String mDetail;

    public AuthenticationError(String provider, Reason reason) {
        this(provider, reason, "");
    }

    public AuthenticationError(String provider, Reason reason, String detail) {
        mProvider = provider;
        mReason = reason;
        mDetail = detail;
    }

    public String getProvider() {
        return mProvider;
    }

    @Override
    public String toString() {
        return "Error authenticating with " + mProvider + ": " + mReason.getMessage()
                + (mDetail != null && mDetail.length() > 0 ? "(" + mDetail + ")" : mDetail);
    }

    public boolean is(Reason reason) {
        return reason.equals(mReason);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthenticationError)) return false;

        AuthenticationError that = (AuthenticationError) o;

        return mReason == that.mReason;

    }

    @Override
    public int hashCode() {
        return mReason != null ? mReason.hashCode() : 0;
    }
}