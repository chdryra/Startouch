/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Factories;

import android.app.Activity;
import android.os.Bundle;

import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchableUiLauncher {
    private FactoryLauncherUi mLauncherFactory;

    public LaunchableUiLauncher(FactoryLauncherUi launcherFactory) {
        mLauncherFactory = launcherFactory;
    }

    public void launch(LaunchableUi ui, Activity commissioner, int requestCode, Bundle args) {
        ui.launch(mLauncherFactory.newLauncher(commissioner, requestCode, ui.getLaunchTag(), args));
    }

    public void launch(LaunchableUi ui, Activity commissioner, int requestCode){
        launch(ui, commissioner, requestCode, new Bundle());
    }

    public void launch(LaunchableConfig config, Activity commissioner, int requestCode, Bundle args) {
        launch(config.getLaunchable(), commissioner, requestCode, args);
    }

    public void launch(LaunchableConfig config, Activity commissioner, int requestCode) {
        launch(config.getLaunchable(), commissioner, requestCode, new Bundle());
    }
}
