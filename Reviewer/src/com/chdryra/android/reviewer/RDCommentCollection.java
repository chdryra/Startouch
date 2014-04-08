package com.chdryra.android.reviewer;

import java.util.Map;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class RDCommentCollection extends RCollection<RDComment> implements RDComment{
	private static final String COMMENTS = "Comments"; 
	private static final String DATA = "COMMENTS DATA";

	private Review mHoldingReview;
	
	public RDCommentCollection() {
	}

	@SuppressWarnings("unchecked")
	public RDCommentCollection(Parcel in) {
		Bundle args = in.readBundle();
		Map<RDId, RDComment> map = (Map<RDId, RDComment>) args.getSerializable(DATA);
		mData.putAll(map);
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
		return size() > 0;
	}
	
	@Override
	public String getCommentTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(size());
		sb.append(" ");
		sb.append(COMMENTS);
		
		return sb.toString();
	}

	@Override
	public String getCommentString() {
		return toString();
	}

	@Override
	public String toString() {
		return getCommentTitle();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle args = new Bundle();
		args.putSerializable(DATA, mData);
		dest.writeBundle(args);
	}
	
	public static final Parcelable.Creator<RDCommentCollection> CREATOR 
	= new Parcelable.Creator<RDCommentCollection>() {
	    public RDCommentCollection createFromParcel(Parcel in) {
	        return new RDCommentCollection(in);
	    }

	    public RDCommentCollection[] newArray(int size) {
	        return new RDCommentCollection[size];
	    }
	};
}
