/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterReviewsList;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewsList {
    private static final String TAG = TagKeyGenerator.getTag(ActivityFeed.class);

    @Override
    protected PresenterReviewsList newPresenter() {
        ParcelablePacker<GvAuthor> packer = new ParcelablePacker<>();
        Bundle args = getIntent().getBundleExtra(getLaunchTag());
        GvAuthor bundledAuthor = args != null ?
                packer.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args) : null;
        if(bundledAuthor == null) throw new IllegalArgumentException("No author!");

        return new PresenterFeed.Builder(ApplicationInstance.getInstance(this)).build(bundledAuthor);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }
}
