/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 31/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsFeedMutable extends ReviewsRepositoryMutable, ReviewsFeed {
    @Override
    DataAuthor getAuthor();

    @Override
    void addReview(Review review, CallbackRepositoryMutable callback);

    @Override
    void removeReview(ReviewId reviewId, CallbackRepositoryMutable callback);

    @Override
    void getReview(ReviewId id, CallbackRepository callback);

    @Override
    void getReviews(CallbackRepository callback);

    @Override
    TagsManager getTagsManager();

    @Override
    void registerObserver(ReviewsRepositoryObserver observer);

    @Override
    void unregisterObserver(ReviewsRepositoryObserver observer);
}