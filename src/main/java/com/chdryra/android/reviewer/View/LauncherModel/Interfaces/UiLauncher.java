/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;

/**
 * Created by: Rizwan Choudrey
 * On: 03/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UiLauncher {
    void launch(LaunchableUi ui, UiLauncherArgs args);

    void launchBuildUi(@Nullable ReviewId template);

    void launchEditDataUi(GvDataType<? extends GvDataParcelable> dataType);

    void launchImageChooser(ImageChooser chooser, int requestCode);

    ReviewLauncher newReviewLauncher();
}
