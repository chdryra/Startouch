/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

public class RDSubject implements RData {
	private Review mHoldingReview;
	private final String mTitle;
	
	public RDSubject(String title, Review review) {
		mTitle = title;
		mHoldingReview = review;
	}

	public String get() {
		return mTitle;
	}

	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview = review;
	}

	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}

	@Override
	public boolean hasData() {
		return mTitle != null && mTitle.length() > 0;
	}
}
