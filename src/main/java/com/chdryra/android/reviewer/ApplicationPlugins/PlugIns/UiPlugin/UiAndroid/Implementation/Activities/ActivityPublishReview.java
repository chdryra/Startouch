/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities;


import android.content.Intent;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PresenterReviewPublish;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityPublishReview extends ActivityReviewView
        implements PlatformAuthoriser, PublishAction.PublishCallback {
    private PresenterReviewPublish mPresenter;

    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = AndroidAppInstance.getInstance(this);

        PresenterReviewPublish.Builder builder = new PresenterReviewPublish.Builder(app);
        mPresenter = builder.build(app.getReviewBuilderAdapter(), this, this);

        return mPresenter.getView();
    }

    @Override
    public void seekAuthorisation(SocialPlatform<?> platform, AuthorisationListener listener) {
        mPresenter.seekAuthorisation(platform, new ActivitySocialAuthUi(), listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onQueuedToPublish(ReviewId id, CallbackMessage message) {
        mPresenter.onQueuedToPublish(this);
    }

    @Override
    public void onFailedToQueue(@Nullable Review review,
                                @Nullable ReviewId id,
                                CallbackMessage message) {
        mPresenter.onFailedToQueue(message);
    }
}
