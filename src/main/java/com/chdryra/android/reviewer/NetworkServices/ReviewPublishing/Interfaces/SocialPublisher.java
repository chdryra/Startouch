/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SocialPublisher extends NetworkPublisher<SocialPublisherListener>{
    @Override
    void registerListener(SocialPublisherListener socialPublisherListener);

    @Override
    void unregisterListener(SocialPublisherListener socialPublisherListener);

    @Override
    void publishReview();
}