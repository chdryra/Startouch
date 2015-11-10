package com.chdryra.android.reviewer.Database;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerDbObserver {
    //abstract
    void onReviewAdded(Review review);

    void onReviewDeleted(String reviewId);
}
