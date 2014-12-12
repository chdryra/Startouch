/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 October, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.VHDDualString;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Parcelable version of {@link com.chdryra.android.mygenerallibrary.VHDDualString} to comply with
 * {@link GvDataList.GvData}
 */
class GvDualText extends VHDDualString implements GvDataList.GvData {
    public static final Parcelable.Creator<GvDualText> CREATOR = new Parcelable
            .Creator<GvDualText>() {
        public GvDualText createFromParcel(Parcel in) {
            return new GvDualText(in);
        }

        public GvDualText[] newArray(int size) {
            return new GvDualText[size];
        }
    };

    GvDualText(String upper, String lower) {
        super(upper, lower);
    }

    GvDualText(Parcel in) {
        super(in.readString(), in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getUpper());
        parcel.writeString(getLower());
    }
}