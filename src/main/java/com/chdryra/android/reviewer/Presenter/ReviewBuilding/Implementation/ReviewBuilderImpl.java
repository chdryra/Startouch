/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderImpl implements ReviewBuilder {
    private final Map<GvDataType<?>, DataBuilder<?>> mDataBuilders;

    private String mSubject;
    private float mRating;
    private boolean mIsAverage = false;

    private final TagsManager mTagsManager;
    private final FactoryReviews mReviewFactory;
    private final FactoryDataBuilder mDataBuilderFactory;
    private final DataValidator mDataValidator;

    //Constructors
    public ReviewBuilderImpl(TagsManager tagsManager,
                             FactoryReviews reviewFactory,
                             FactoryDataBuilder dataBuilderFactory,
                             DataValidator dataValidator) {
        mTagsManager = tagsManager;
        mReviewFactory = reviewFactory;
        mDataValidator = dataValidator;

        mDataBuilders = new HashMap<>();
        mDataBuilderFactory = dataBuilderFactory;

        mSubject = "";
        mRating = 0f;
    }

    //public methods
    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public void setSubject(String subject) {
        mSubject = subject;
    }

    @Override
    public boolean isRatingAverage() {
        return mIsAverage;
    }

    @Override
    public float getRating() {
        return isRatingAverage() ? getAverageRating() : mRating;
    }

    @Override
    public void setRating(float rating) {
        mRating = rating;
    }

    @Override
    public float getAverageRating() {
        GvCriterionList criteria = (GvCriterionList) getData(GvCriterion.TYPE);
        return criteria.getAverageRating();
    }

    @Override
    public void setRatingIsAverage(boolean ratingIsAverage) {
        mIsAverage = ratingIsAverage;
        if (ratingIsAverage) setRating(getAverageRating());
    }

    @Override
    public <T extends GvData> DataBuilder<T> getDataBuilder(GvDataType<T> dataType) {
        //TODO make type safe
        DataBuilder<T> builder = (DataBuilder<T>) mDataBuilders.get(dataType);
        if(builder == null) builder = createDataBuilder(dataType);
        return builder;
    }

    private <T extends GvData> GvDataList<T> getData(GvDataType<T> dataType) {
        return getDataBuilder(dataType).getData();
    }

    @Override
    public GvImage getCover() {
        GvDataList<GvImage> images = getData(GvImage.TYPE);
        for(GvImage image : images) {
            if(image.isCover()) return image;
        }

        return new GvImage();
    }

    @Override
    public boolean hasTags() {
        return getData(GvTag.TYPE).size() > 0;
    }

    @Override
    public Review buildReview() {
        if (!isValidForPublication()) {
            throw new IllegalStateException("Review is not valid for publication!");
        }

        Review review = mReviewFactory.createUserReview(getSubject(), getRating(),
                getData(GvCriterion.TYPE),
                getData(GvComment.TYPE),
                getData(GvImage.TYPE),
                getData(GvFact.TYPE),
                getData(GvLocation.TYPE),
                mIsAverage);

        GvTagList tags = (GvTagList) getData(GvTag.TYPE);
        mTagsManager.tagItem(review.getReviewId().toString(), tags.toStringArray());

        return review;
    }

    //private methods
    private <T extends GvData> DataBuilder<T> createDataBuilder(GvDataType<T> dataType) {
        DataBuilder<T> db = mDataBuilderFactory.newDataBuilder(dataType, this);
        mDataBuilders.put(dataType, db);
        return db;
    }

    private boolean isValidForPublication() {
        return mDataValidator.validateString(mSubject) && hasTags();
    }
}
