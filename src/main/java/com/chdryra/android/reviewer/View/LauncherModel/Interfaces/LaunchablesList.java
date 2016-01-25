/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Interfaces;

import com.chdryra.android.reviewer.View.Configs.AddEditViewClasses;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LaunchablesList {
    Class<? extends LaunchableUi> getReviewBuilderUi();

    Class<? extends LaunchableUi> getMapEditorUi();

    Class<? extends LaunchableUi> getShareReviewUi();

    ArrayList<AddEditViewClasses<?>> getDataLaunchableUis();
}