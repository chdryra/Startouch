/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataHolderImpl implements ReviewDataHolder {
    private final ReviewId mId;
    private final DataAuthor mAuthor;
    private final DataDate mPublishDate;
    private final String mSubject;
    private final float mRating;
    private final int mRatingWeight;
    private final Iterable<? extends DataComment> mComments;
    private final Iterable<? extends DataImage> mImages;
    private final Iterable<? extends DataFact> mFacts;
    private final Iterable<? extends DataLocation> mLocations;
    private final Iterable<? extends DataCriterion> mCriteria;

    public ReviewDataHolderImpl(ReviewId id, DataAuthor author, DataDate publishDate,
                                String subject, float rating, int ratingWeight,
                                Iterable<? extends DataComment> comments,
                                Iterable<? extends DataImage> images,
                                Iterable<? extends DataFact> facts,
                                Iterable<? extends DataLocation> locations,
                                Iterable<? extends DataCriterion> criteria) {
        mId = id;
        mAuthor = author;
        mPublishDate = publishDate;
        mSubject = subject;
        mRating = rating;
        mRatingWeight = ratingWeight;
        mComments = comments;
        mImages = images;
        mFacts = facts;
        mLocations = locations;
        mCriteria = criteria;
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }

    @Override
    public DataAuthor getAuthor() {
        return mAuthor;
    }

    @Override
    public DataDate getPublishDate() {
        return mPublishDate;
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public float getRating() {
        return mRating;
    }

    @Override
    public int getRatingWeight() {
        return mRatingWeight;
    }

    @Override
    public Iterable<? extends DataComment> getComments() {
        return mComments;
    }

    @Override
    public Iterable<? extends DataImage> getImages() {
        return mImages;
    }

    @Override
    public Iterable<? extends DataFact> getFacts() {
        return mFacts;
    }

    @Override
    public Iterable<? extends DataLocation> getLocations() {
        return mLocations;
    }

    @Override
    public Iterable<? extends DataCriterion> getCriteria() {
        return mCriteria;
    }

    @Override
    public boolean isValid(DataValidator validator) {
        return validator.validate(mId) && validator.validate(mAuthor)
                && validator.validateString(mSubject) && mRatingWeight > 0;
    }
}
