package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleReview implements Review {
	private ReviewID mID;
	private RDTitle mTitle;
	private RDRating mRating;

	public SimpleReview(String title) {
		mID = ReviewID.generateID();
		mTitle = new RDTitle(title, this);
		mRating = new RDRating(0, this);
	}
	
	public SimpleReview(Parcel in) {
		mID = in.readParcelable(ReviewID.class.getClassLoader());
		mTitle = in.readParcelable(RDTitle.class.getClassLoader());
		mTitle.setHoldingReview(this);
		mRating = in.readParcelable(RDRating.class.getClassLoader());
		mRating.setHoldingReview(this);
	}

	@Override
	public ReviewID getID() {
		return mID;
	}

	@Override
	public RDTitle getTitle() {
		return mTitle;
	}

	@Override
	public void setTitle(String title) {
		mTitle.set(title);
	}

	@Override
	public RDRating getRating() {
		return mRating;
	}

	@Override
	public void setRating(float rating) {
		mRating.set(rating);
	}

	@Override
	public void setComment(RDComment comment) {
	}

	@Override
	public RDComment getComment() {
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
	public RDImage getImage() {
		return null;
	}

	@Override
	public void setImage(RDImage image) {
	}

	@Override
	public void deleteImage() {
	}

	@Override
	public boolean hasImage() {
		return false;
	}

	@Override
	public RDLocation getLocation() {
		return null;
	}

	@Override
	public void setLocation(RDLocation location) {
	}

	@Override
	public void deleteLocation() {
	}

	@Override
	public boolean hasLocation() {
		return false;
	}

	@Override
	public RDFacts getFacts() {
		return null;
	}

	@Override
	public void setFacts(RDFacts facts) {
	}

	@Override
	public void deleteFacts() {
	}

	@Override
	public boolean hasFacts() {
		return false;
	}

	@Override
	public RDUrl getURL() {
		return null;
	}

	@Override
	public void setURL(RDUrl url) {
	}

	@Override
	public void deleteURL() {
	}

	@Override
	public boolean hasURL() {
		return false;
	}

	@Override
	public RDDate getDate() {
		return null;
	}
	
	@Override
	public void setDate(RDDate date) {
	}

	@Override
	public void deleteDate() {
	}

	@Override
	public boolean hasDate() {
		return false;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mID, flags);
		dest.writeParcelable(mTitle, flags);
		dest.writeParcelable(mRating, flags);
	}
	
	public static final Parcelable.Creator<SimpleReview> CREATOR 
	= new Parcelable.Creator<SimpleReview>() {
	    public SimpleReview createFromParcel(Parcel in) {
	        return new SimpleReview(in);
	    }

	    public SimpleReview[] newArray(int size) {
	        return new SimpleReview[size];
	    }
	};
}
