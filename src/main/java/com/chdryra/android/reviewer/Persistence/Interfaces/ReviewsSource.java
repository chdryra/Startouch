/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsSource extends ReviewsRepository{
    void asMetaReview(ReviewId id, ReviewsSourceCallback callback);

    void asMetaReview(VerboseDataReview datum, String subject, ReviewsSourceCallback callback);

    void getMetaReview(VerboseIdableCollection data, String subject, ReviewsSourceCallback callback);

    interface ReviewsSourceCallback {
        void onMetaReviewCallback(RepositoryResult result);
    }
}
