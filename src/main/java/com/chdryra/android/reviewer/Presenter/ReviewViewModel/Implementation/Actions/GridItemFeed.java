/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemFeed extends GridItemReviewsList{

    public GridItemFeed(FactoryReviewView launchableFactory,
                        LaunchableUi shareEditUi) {
        super(launchableFactory, shareEditUi);
    }

    @Override
    public void onGridItemClick(GvNode item, int position, View v) {
        getApp().newReviewLauncher().launchReviews(item.getAuthorId());
    }
}
