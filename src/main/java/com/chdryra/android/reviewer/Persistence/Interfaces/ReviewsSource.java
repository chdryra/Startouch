/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsSource extends ReviewsRepository {
    void asMetaReview(ReviewId id, ReviewsSourceCallback callback);

    ReviewNode asMetaReview(AuthorId id);

    void asMetaReview(VerboseDataReview datum, String subject, ReviewsSourceCallback callback);

    void getMetaReview(IdableCollection<?> data, String subject, ReviewsSourceCallback callback);

    //As review node in result
    interface ReviewsSourceCallback {
        void onMetaReviewCallback(RepositoryResult result);
    }
}
