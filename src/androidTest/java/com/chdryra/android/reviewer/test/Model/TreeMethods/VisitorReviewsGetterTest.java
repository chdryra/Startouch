/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 June, 2015
 */

package com.chdryra.android.reviewer.test.Model.TreeMethods;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorReviewsGetter;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorReviewsGetterTest extends TestCase {
    @SmallTest
    public void testVisit() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        VisitorReviewsGetter visitor = new VisitorReviewsGetter();
        node.acceptVisitor(visitor);

        IdableList<Review> nodes = visitor.getReviews();
        IdableList<Review> flattened = flatten(node);
        assertEquals(flattened.size(), nodes.size());
        for (Review item : flattened) {
            assertTrue(nodes.containsId(item.getId()));
        }
    }

    private IdableList<Review> flatten(ReviewNode node) {
        IdableList<Review> reviews = new IdableList<>();
        reviews.add(node.getReview());

        for (ReviewNode child : node.getChildren()) {
            reviews.add(flatten(child));
        }

        return reviews;
    }
}