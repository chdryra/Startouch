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

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvDataParcelableBasic<T extends GvDataParcelable> extends GvDataBasic<T> implements GvDataParcelable{
    public GvDataParcelableBasic(Parcel in) {
        super((GvDataType<T>) in.readParcelable(GvDataType.class.getClassLoader()),
                (GvReviewId) in.readParcelable(GvReviewId.class.getClassLoader()));
    }

    protected GvDataParcelableBasic(GvDataType<T> type) {
        this(type, null);
    }

    protected GvDataParcelableBasic(GvDataType<T> type, @Nullable GvReviewId reviewId) {
        super(type, reviewId);
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
    public GvDataParcelable getParcelable() {
        return this;
    }
}