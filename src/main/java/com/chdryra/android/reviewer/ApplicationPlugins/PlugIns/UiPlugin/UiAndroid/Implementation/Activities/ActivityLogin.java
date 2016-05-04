/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.app.Fragment;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentLogin;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationLaunch;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

public class ActivityLogin extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = "ActivityLogin";
    private static final String KEY = "ActivityLogin";
    private FragmentLogin mFragment;

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), KEY);
    }

    @Override
    protected Fragment createFragment() {
        ApplicationLaunch.launchIfNecessary(this, ApplicationLaunch.LaunchState.TEST);
        mFragment = FragmentLogin.newInstance();
        return mFragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mFragment != null) mFragment.cancelAuthentication();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFragment.onActivityResult(requestCode, resultCode, data);
    }
}