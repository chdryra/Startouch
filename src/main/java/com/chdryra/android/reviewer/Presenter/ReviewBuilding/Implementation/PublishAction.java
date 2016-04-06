/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation.ReviewPublisher;
import com.chdryra.android.reviewer.Utils.CallbackMessage;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PublishAction implements ReviewPublisher.QueueCallback {
    private ApplicationInstance mApp;

    protected abstract void onQueuedToPublish(ReviewId id, CallbackMessage message);

    protected abstract void onQueueingFailed(@Nullable Review review, @Nullable ReviewId id,
                                             CallbackMessage message);

    public PublishAction(ApplicationInstance app) {
        mApp = app;
    }

    public void publish(Review toPublish, ArrayList<String> selectedPublishers) {
        mApp.getPublisher().addToQueue(toPublish, selectedPublishers, this);
    }

    @Override
    public void onAddedToQueue(ReviewId id, CallbackMessage message) {
        onQueuedToPublish(id, message);
    }

    @Override
    public void onRetrievedFromQueue(Review review, CallbackMessage message) {
        throw new UnsupportedOperationException("Should never be called");
    }

    @Override
    public void onFailed(@Nullable Review review, @Nullable ReviewId id, CallbackMessage message) {
        onQueueingFailed(review, id, message);
    }
}