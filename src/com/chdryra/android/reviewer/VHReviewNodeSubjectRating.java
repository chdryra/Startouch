/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

class VHReviewNodeSubjectRating extends ViewHolderBasic {
    private static final int LAYOUT  = R.layout.grid_cell_review_subject_rating;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING  = R.id.review_rating_bar;

    private TextView  mSubject;
    private RatingBar mRating;

    public VHReviewNodeSubjectRating() {
        super(LAYOUT);
    }

    @Override
    public View updateView(GVData data) {
        GVReviewSubjectRating criterion = (GVReviewSubjectRating) data;
        if (criterion != null) {
            mSubject.setText(criterion.getSubject());
            mRating.setRating(criterion.getRating());
        }

        return mInflated;
    }

    @Override
    protected void initViewsToUpdate() {
        mSubject = (TextView) getView(SUBJECT);
        mRating = (RatingBar) getView(RATING);
    }
}