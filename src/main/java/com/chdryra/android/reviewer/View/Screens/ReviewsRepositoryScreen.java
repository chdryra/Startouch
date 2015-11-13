/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.PublishDate;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.ReviewPublisher;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Interfaces.Data.DataConverter;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNodeComponent;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProviderObserver;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProvider;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsRepositoryScreen implements ReviewsProviderObserver {
    private ReviewsProvider mRepository;
    private ReviewNodeComponent mNode;
    private FactoryReview mReviewFactory;
    private FactoryReviewNodeComponent mNodeFactory;
    private ReviewView mReviewView;

//Constructors
    public ReviewsRepositoryScreen(ReviewsProvider repository,
                                   FactoryReview reviewFactory,
                                   FactoryReviewNodeComponent nodeFactory,
                                   String title,
                                   PublishDate publishDate) {
        mRepository = repository;
        mReviewFactory = reviewFactory;

        Author author = repository.getAuthor();
        ReviewPublisher publisher = new ReviewPublisher(author, publishDate);
        Review root = mReviewFactory.createUserReview(publisher, title, 0f);

        mNodeFactory = nodeFactory;
        mNode = mNodeFactory.createReviewNodeComponent(root, true);
        for (Review review : repository.getReviews()) {
            addReview(review);
        }

        repository.registerObserver(this);
    }

    public ReviewView createView(DataConverter<Review, GvReviewOverviewList.GvReviewOverview> reviewConverter,
                                 DataConverter<DataImage, GvImageList.GvImage> imageConverter,
                           BuilderChildListScreen childListFactory,
                           FactoryReviewViewAdapter adapterFactory,
                           ReviewViewAction.GridItemAction giAction,
                           ReviewViewAction.MenuAction menuAction) {
        mReviewView = childListFactory.createView(mNode, reviewConverter, imageConverter,
                adapterFactory, giAction, menuAction);
        return mReviewView;
    }

    //private methods
    private void addReview(Review review) {
        mNode.addChild(mNodeFactory.createReviewNodeComponent(review, false));
    }

    private void removeReview(String reviewId) {
        mNode.removeChild(reviewId);
    }

    //Overridden
    @Override
    public void onReviewAdded(Review review) {
        addReview(review);
        if(mReviewView != null) mReviewView.onGridDataChanged();
    }

    @Override
    public void onReviewRemoved(String reviewId) {
        removeReview(reviewId);
        if(mReviewView != null) mReviewView.onGridDataChanged();
    }
}
