/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.RCollectionReview;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorRatingAverageOfChildrenTest extends TestCase {
    @SmallTest
    public void testGetRating() {
        ReviewNode node = ReviewMocker.newReviewNode();
        VisitorRatingAverageOfChildren visitor = new VisitorRatingAverageOfChildren();
        visitor.visit(node);

        float nodeAverage = 0;
        RCollectionReview<ReviewNode> children = node.getChildren();
        assertTrue(children.size() > 0);
        for (ReviewNode child : children) {
            nodeAverage += child.getRating().get();
        }

        nodeAverage /= children.size();
        assertEquals(nodeAverage, visitor.getRating());
    }
}
