/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.ReviewUser;
import com.chdryra.android.reviewer.Model.UserId;
import com.chdryra.android.testutils.RandomString;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewMocker {
    private static final int NUM = 3;

    private ReviewMocker() {
    }

    public static Review newReview() {
        return getNewReview();
    }

    public static ReviewNode newReviewNode(boolean ratingIsAverage) {
        return getNewNode(ratingIsAverage);
    }

    private static ReviewNode getNewNode(boolean ratingIsAverage) {
        Review root = new MockReview();
        Review parent = new MockReview();
        ReviewTreeNode rootNode = new ReviewTreeNode(root, ratingIsAverage);
        ReviewTreeNode parentNode = new ReviewTreeNode(parent, false);
        rootNode.setParent(parentNode);

        for (int i = 0; i < NUM; ++i) {
            rootNode.addChild(new ReviewTreeNode(new MockReview(), false));
        }

        return rootNode;
    }

    private static Review getNewReview() {
        return new MockReview();
    }

    static class MockReview extends ReviewUser {
        private MockReview() {
            super(ReviewId.generateId(), new Author(RandomString.nextWord(), UserId.generateId()),
                    new Date(), RandomString.nextWord(),
                    RandomRating.nextRating(), GvDataMocker.newCommentList(NUM, false),
                    GvDataMocker.newImageList(NUM, false), GvDataMocker.newFactList(NUM, false),
                    GvDataMocker.newLocationList(NUM, false));
        }
    }
}
