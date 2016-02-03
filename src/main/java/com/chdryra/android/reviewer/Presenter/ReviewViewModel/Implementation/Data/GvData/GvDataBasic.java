/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvDataBasic<T extends GvData> implements GvData {
    private GvDataType<T> mType;
    private GvReviewId mReviewId;

    //Constructors
    public GvDataBasic(Parcel in) {
        mType = in.readParcelable(GvDataType.class.getClassLoader());
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    protected GvDataBasic(GvDataType<T> type) {
        mType = type;
    }

    protected GvDataBasic(GvDataType<T> type, @Nullable GvReviewId reviewId) {
        mType = type;
        mReviewId = reviewId;
    }

    //Overridden
    @Override
    public GvReviewId getGvReviewId() {
        return mReviewId;
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasElements() {
        return false;
    }

    @Override
    public boolean isVerboseCollection() {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mType, i);
        parcel.writeParcelable(mReviewId, i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataBasic)) return false;

        GvDataBasic<?> that = (GvDataBasic<?>) o;

        if (!mType.equals(that.mType)) return false;
        return !(mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null);

    }

    @Override
    public int hashCode() {
        int result = mType.hashCode();
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        return result;
    }
}
