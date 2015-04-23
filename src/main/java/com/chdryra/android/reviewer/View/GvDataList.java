/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The View layer (V) data equivalent of the Model layer (M) data {@link com.chdryra.android
 * .reviewer.Model.MdDataList}.
 * Implementation of {@link ViewHolderDataList} tailored for data accessed via a {@link
 * com.chdryra.android.reviewer.Controller.ReviewViewAdapter} (A) (Model-View-Adapter pattern).
 * <p/>
 *
 * @param <T>: {@link GvData} type.GvDataList
 */
public class GvDataList<T extends GvData> extends ViewHolderDataList<T> implements GvData {
    public static final Parcelable.Creator<GvDataList> CREATOR = new Parcelable
            .Creator<GvDataList>() {
        public GvDataList createFromParcel(Parcel in) {
            return new GvDataList(in);
        }

        public GvDataList[] newArray(int size) {
            return new GvDataList[size];
        }
    };

    private final GvDataType mType;
    private final Class<T>   mDataClass;
    private       GvReviewId mReviewId;

    public GvDataList(Class<T> dataClass, GvDataType mDataType) {
        mDataClass = dataClass;
        mType = mDataType;
    }

    //TODO come up with a more robust way of doing this.
    public GvDataList(GvReviewId reviewId, GvDataList<T> data) {
        this(reviewId, data.mDataClass, data.getGvDataType());
        super.add(data);
    }

    public GvDataList(GvReviewId reviewId, Class<T> dataClass, GvDataType mDataType) {
        mDataClass = dataClass;
        mType = mDataType;
        mReviewId = reviewId;
    }

    public GvDataList(GvDataList<T> data) {
        mDataClass = data.mDataClass;
        mType = data.getGvDataType();
        super.add(data);
    }

    //TODO make type safe
    public GvDataList(Parcel in) {
        mDataClass = (Class<T>) in.readValue(Class.class.getClassLoader());
        mType = (GvDataType) in.readSerializable();
        T[] data = (T[]) in.readParcelableArray(mDataClass.getClassLoader());
        mData = new ArrayList<>(Arrays.asList(data));
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    public GvDataType getGvDataType() {
        return mType;
    }

    public boolean contains(GvData datum) {
        return super.contains(mDataClass.cast(datum));
    }

    @Override
    public String getStringSummary() {
        int num = size();
        String dataString = num == 1 ? mType.getDatumName() : mType.getDataName();
        return String.valueOf(size()) + " " + dataString;
    }

    @Override
    public boolean hasHoldingReview() {
        return mReviewId != null;
    }

    @Override
    public GvReviewId getHoldingReviewId() {
        return mReviewId;
    }

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mDataClass);
        dest.writeSerializable(mType);
        dest.writeParcelableArray((T[]) mData.toArray(), flags);
        dest.writeParcelable(mReviewId, flags);
    }

    @Override
    public ViewHolder newViewHolder() {
        return new VhDataList();
    }

    @Override
    public boolean isValidForDisplay() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataList)) return false;

        GvDataList that = (GvDataList) o;

        if (!mDataClass.equals(that.mDataClass)) return false;
        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null) {
            return false;
        }
        if (!mType.equals(that.mType)) return false;
        if (size() != that.size()) return false;

        for (int i = 0; i < size(); ++i) {
            if (!getItem(i).equals(that.getItem(i))) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mType.hashCode();
        result = 31 * result + mDataClass.hashCode();
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        for (T datum : this) {
            result = 31 * result + datum.hashCode();
        }

        return result;
    }

    protected boolean isModifiable() {
        return !hasHoldingReview();
    }
}
