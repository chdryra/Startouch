package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewCommentSingle implements ReviewComment{
	private static final String DEFAULT_TITLE = "Comment";
	
	private Review mHoldingReview;
	private String mComment;
	
	public ReviewCommentSingle(String comment) {
		mComment = comment;
	}
	
	public ReviewCommentSingle(Parcel in) {
		mComment = in.readString();
	}

	@Override
	public void setHoldingReview(Review review) {
		mHoldingReview = review;
	}
	
	@Override
	public Review getHoldingReview() {
		return mHoldingReview;
	}
	
	public String getCommentTitle() {
		return mHoldingReview == null? DEFAULT_TITLE : mHoldingReview.getTitle().get();
	}
	
	public String getCommentString() {
		return mComment;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCommentTitle());
		sb.append(": ");
		sb.append(mComment);
		
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mComment);
	}
	
	public static final Parcelable.Creator<ReviewCommentSingle> CREATOR 
	= new Parcelable.Creator<ReviewCommentSingle>() {
	    public ReviewCommentSingle createFromParcel(Parcel in) {
	        return new ReviewCommentSingle(in);
	    }

	    public ReviewCommentSingle[] newArray(int size) {
	        return new ReviewCommentSingle[size];
	    }
	};
}
