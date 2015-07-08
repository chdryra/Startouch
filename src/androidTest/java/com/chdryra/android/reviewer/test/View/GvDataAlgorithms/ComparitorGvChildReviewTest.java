/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAlgorithms.ComparitorGvChildReview;
import com.chdryra.android.reviewer.View.GvDataAlgorithms.DifferencePercentage;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 06/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvChildReviewTest extends TestCase {
    @SmallTest
    public void testCompare() {
        String lhsSubject = "kitten";
        String rhsSubject = "sitting";
        String empty = "";
        float lhsRating = RandomRating.nextRating();
        float rhsRating = 5f - lhsRating;

        ComparitorGvChildReview comparitor = new ComparitorGvChildReview();
        DifferencePercentage none = new DifferencePercentage(0.0);
        DifferencePercentage all = new DifferencePercentage(1.0);
        DifferencePercentage expected = new DifferencePercentage(3.0 / 7.0);
        DifferencePercentage expectedDelta = new DifferencePercentage(3.0 / 7.0 - 0.01);

        GvChildList.GvChildReview lhs = new GvChildList.GvChildReview(lhsSubject, lhsRating);
        GvChildList.GvChildReview rhs = new GvChildList.GvChildReview(lhsSubject, lhsRating);
        DifferencePercentage difference = comparitor.compare(lhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));

        rhs = new GvChildList.GvChildReview(lhsSubject, rhsRating);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));

        rhs = new GvChildList.GvChildReview(rhsSubject, lhsRating);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));

        rhs = new GvChildList.GvChildReview(rhsSubject, rhsRating);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));

        rhs = new GvChildList.GvChildReview(empty, lhsRating);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(expected));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(expected));

        rhs = new GvChildList.GvChildReview(empty, rhsRating);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(expected));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(expected));
    }
}