package com.chdryra.android.reviewer.Interfaces.Data;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataRating extends DataReview, Validatable {
    float getRating();

    int getWeight();

    @Override
    String getReviewId();
}