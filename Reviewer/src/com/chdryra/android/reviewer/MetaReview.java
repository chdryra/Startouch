package com.chdryra.android.reviewer;

import android.os.Parcel;

public class MetaReview implements Review {
	private static final String REVIEWS = "Reviews";
	
	private ReviewNode mNode;
	
	public MetaReview(String title, ReviewNodeCollection reviewNodes) {
		mNode = ReviewFactory.createSimpleReviewNode(title);
		mNode.addChildren(reviewNodes);
	}

	//Review methods
	@Override
	public ReviewID getID() {
		return mNode.getID();
	}

	@Override
	public float getRating() {
		return getAverageRating();
	}
	
	@Override
	public String getTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(getTitle());
		sb.append(": ");
		sb.append(mNode.getChildren().size());
		sb.append(" ");
		sb.append(REVIEWS);
		
		return sb.toString();
	}
	
	@Override
	public void setRating(float rating) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTitle(String title) {
		mNode.setTitle(title);
	}
	
	@Override
	public void setComment(ReviewComment comment) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReviewComment getComment() {
		return null;
	}

	@Override
	public void deleteComment() {
	}

	@Override
	public boolean hasComment() {
		return false;
	}

	@Override
	public ReviewImage getImage() {
		return null;
	}

	@Override
	public void setImage(ReviewImage image) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteImage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasImage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReviewLocation getLocation() {
		return null;
	}

	@Override
	public void setLocation(ReviewLocation location) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteLocation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLocation() {
		return false;
	}

	@Override
	public ReviewFacts getFacts() {
		return null;
	}

	@Override
	public void setFacts(ReviewFacts data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFacts() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasFacts() {
		return false;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mNode, flags);
	}

	private float getAverageRating() {
		VisitorRatingAverager averager = new VisitorRatingAverager();
		ReviewNodeCollection reviews = mNode.getChildren();
		for(ReviewNode r : reviews)
			r.acceptVisitor(averager);
		return averager.getRating();
	}
}
