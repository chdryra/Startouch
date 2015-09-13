/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 March, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataType<T extends GvData> implements Parcelable {
    public static final Parcelable.Creator<GvDataType> CREATOR = new Parcelable
            .Creator<GvDataType>() {
        public GvDataType createFromParcel(Parcel in) {
            return new GvDataType(in);
        }

        public GvDataType[] newArray(int size) {
            return new GvDataType[size];
        }
    };

    private final Class<T> mDataClass;
    private final String mDatumName;
    private final String mDataName;

    public GvDataType(Class<T> dataClass, String datum) {
        this(dataClass, datum, datum + "s");
    }

    public GvDataType(Class<T> dataClass, String datum, String data) {
        mDataClass = dataClass;
        mDatumName = datum;
        mDataName = data;
    }

    //TODO make typesafe
    public GvDataType(Parcel in) {
        mDataClass = (Class<T>) in.readValue(Class.class.getClassLoader());
        mDatumName = in.readString();
        mDataName = in.readString();
    }

    public String getDatumName() {
        return mDatumName;
    }

    public String getDataName() {
        return mDataName;
    }

    public Class<T> getDataClass() {
        return mDataClass;
    }

    public T cast(GvData datum) {
        return mDataClass.cast(datum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataType)) return false;

        GvDataType<?> that = (GvDataType<?>) o;

        if (!mDataClass.equals(that.mDataClass)) return false;
        if (!mDatumName.equals(that.mDatumName)) return false;
        return mDataName.equals(that.mDataName);

    }

    @Override
    public int hashCode() {
        int result = mDataClass.hashCode();
        result = 31 * result + mDatumName.hashCode();
        result = 31 * result + mDataName.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mDataClass);
        dest.writeString(mDatumName);
        dest.writeString(mDataName);
    }
}
