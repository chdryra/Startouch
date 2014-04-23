package com.chdryra.android.reviewer;

public class RDTitle implements RData {
	private Review mHoldingReview;
	private String mTitle;
	
	public RDTitle(String title, Review review) {
		mTitle = title;
		mHoldingReview = review;
	}
	
	public String get() {
		return mTitle;
	}

	public void set(String title) {
		mTitle = title;
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