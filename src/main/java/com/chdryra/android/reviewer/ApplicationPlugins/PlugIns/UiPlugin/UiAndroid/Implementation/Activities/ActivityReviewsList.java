/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AndroidAppInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityReviewsList extends ActivityReviewView {
    private static final String TAG = TagKeyGenerator.getTag(ActivityReviewsList.class);

    private PresenterReviewsList mPresenter;

    @Override
    protected ReviewView createReviewView() {
        mPresenter = newPresenter();
        mPresenter.attach();
        return mPresenter.getView();
    }

    PresenterReviewsList newPresenter() {
        ReviewsListView view = (ReviewsListView) super.createReviewView();
        return new PresenterReviewsList.Builder().build(AndroidAppInstance.getInstance(this), view);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    protected void onResume() {
        AndroidAppInstance.getInstance(this).discardReviewEditor();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}
