/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.net.URL;

public class RDUrl implements RData {
    private final URL    mUrl;
    private       Review mHoldingReview;

    public RDUrl(URL url, Review holdingReview) {
        mUrl = url;
        mHoldingReview = holdingReview;
    }

    public URL get() {
        return mUrl;
    }

    @Override
    public Review getHoldingReview() {
        return mHoldingReview;
    }

    @Override
    public void setHoldingReview(Review review) {
        mHoldingReview = review;
    }

    @Override
    public boolean hasData() {
        return mUrl != null;
    }
}