/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 23/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncherAndroid;

/**
 * For UIs that can be launched by a {@link UiLauncherAndroid.LauncherUiImpl}.
 * Usually by calling "launcher.launch(this)".
 */
public interface LaunchableUi {
    String getLaunchTag();

    void launch(LauncherUi launcher);
}
