/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 12/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLauncherImpl implements ReviewLauncher {
    private AuthorId mSessionAuthor;
    private ReviewsSource mReviewsSource;
    private FactoryReviewView mFactoryReviewView;
    private UiLauncher mLauncher;

    public ReviewLauncherImpl(ReviewsSource reviewsSource, UiLauncher launcher, FactoryReviewView factoryReviewView, AuthorId sessionAuthor) {
        mSessionAuthor = sessionAuthor;
        mReviewsSource = reviewsSource;
        mFactoryReviewView = factoryReviewView;
        mLauncher = launcher;
    }

    @Override
    public void launchReview(ReviewId reviewId) {
        mReviewsSource.asMetaReview(reviewId, new ReviewsSource.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepositoryResult result) {
                ReviewNode node = result.getReviewNode();
                if (!result.isError() && node != null) launchReview(node);
            }
        });
    }

    @Override
    public void launchReviews(AuthorId authorId) {
        launchReview(mReviewsSource.asMetaReview(authorId));
    }

    private void launchReview(ReviewNode reviewNode) {
        int requestCode = RequestCodeGenerator.getCode(reviewNode.getSubject().getSubject());
        DataAuthorId authorId = reviewNode.getAuthorId();
        boolean menu = !authorId.toString().equals(mSessionAuthor.toString());
        mLauncher.launch(mFactoryReviewView.newReviewsListView(reviewNode, menu ? authorId : null), requestCode);
    }
}