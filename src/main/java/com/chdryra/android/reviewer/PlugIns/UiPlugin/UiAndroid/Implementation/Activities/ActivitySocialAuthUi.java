/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentFacebookLogin;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentGoogleLogin;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentOAuthLogin;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentTwitterLogin;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Social.Implementation.OAuthRequest;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PlatformGoogle;
import com.chdryra.android.reviewer.Social.Implementation.PlatformTwitterFabric;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivitySocialAuthUi extends ActivitySingleFragment
        implements
        FragmentFacebookLogin.FacebookLoginListener,
        FragmentTwitterLogin.TwitterLoginListener,
        FragmentGoogleLogin.GoogleLoginListener,
        LaunchableUi {
    private static final String TAG = "ActivitySocialLogin";
    private static final String PLATFORM = "ActivitySocialLogin.platform";

    private Fragment mFragment;

    @Override
    protected Fragment createFragment() {
        OAuthRequest request = getBundledRequest();
        if(request != null) {
            return FragmentOAuthLogin.newInstance(request);
        }

        String platform = getBundledPlatform();
        if (platform.equals(PlatformFacebook.NAME)) {
            return new FragmentFacebookLogin();
        } else if (platform.equals(PlatformGoogle.NAME)) {
            mFragment = new FragmentGoogleLogin();
            return mFragment;
        } else if (platform.equals(PlatformTwitterFabric.NAME)) {
            mFragment = new FragmentTwitterLogin();
            return mFragment;
        }

        return null;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        success();
    }

    @Override
    public void onError(FacebookException error) {
        failure();
    }

    @Override
    public void onSuccess(Result<TwitterSession> result) {
        success();
    }

    @Override
    public void onFailure(TwitterException error) {
        failure();
    }

    @Override
    public void onSuccess(GoogleSignInResult result) {
        ApplicationInstance app = ApplicationInstance.getInstance(this);
        SocialPlatformList platforms = app.getSocialPlatformList();
        PlatformGoogle google = (PlatformGoogle) platforms.getPlatform(PlatformGoogle.NAME);
        GoogleSignInAccount signInAccount = result.getSignInAccount();
        if(google != null && signInAccount != null) {
            String id = signInAccount.getId();
            if(id != null) google.setAccessToken(id);
        }

        success();
    }

    @Override
    public void onFailure(GoogleSignInResult result) {
        failure();
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), PLATFORM);
    }

    @Nullable
    private OAuthRequest getBundledRequest() {
        Bundle args = getIntent().getBundleExtra(PLATFORM);
        ParcelablePacker<OAuthRequest> unpacker = new ParcelablePacker<>();
        return unpacker.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args);
    }

    private String getBundledPlatform() {
        Bundle args = getIntent().getBundleExtra(PLATFORM);
        if (args == null) throwError();
        String platform = args.getString(TAG);
        if (platform == null || platform.length() == 0) throwError();

        return platform;
    }

    private void success() {
        setResult(RESULT_OK);
        finish();
    }

    private void failure() {
        setResult(RESULT_FIRST_USER);
        finish();
    }

    private String throwError() {
        throw new RuntimeException("No platform specified!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mFragment != null) {
            mFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
