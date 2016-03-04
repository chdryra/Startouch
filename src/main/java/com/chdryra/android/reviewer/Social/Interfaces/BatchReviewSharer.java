/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Implementation.BatchSocialPublisher;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 25/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface BatchReviewSharer extends BatchSocialPublisher.BatchPublisherListener {
    void shareReview(Review review, TagsManager tagsManager, Collection<SocialPlatform<?>> platforms);
}
