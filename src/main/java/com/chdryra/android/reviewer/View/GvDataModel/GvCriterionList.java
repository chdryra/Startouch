/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.View.Utils.RatingFormatter;

/**
 * Used for review children (sub-reviews).
 */
public class GvCriterionList extends GvDataList<GvCriterionList.GvCriterion> {
    public GvCriterionList() {
        super(GvCriterion.TYPE, null);
    }

    public GvCriterionList(GvReviewId id) {
        super(GvCriterion.TYPE, id);
    }

    public GvCriterionList(GvCriterionList data) {
        super(data);
    }

    public boolean contains(String subject) {
        for (GvCriterion review : this) {
            if (review.getSubject().equals(subject)) return true;
        }

        return false;
    }

    public float getAverageRating() {
        float rating = 0;
        for (GvCriterion review : this) {
            rating += review.getRating() / size();
        }

        return rating;
    }

    /**
     * {@link GvData} version of: no equivalent as used
     * for review children (sub-reviews).
     * {@link ViewHolder}: {@link VhChild}
     */
    public static class GvCriterion extends GvDataBasic<GvCriterion> {
        public static final GvDataType<GvCriterion> TYPE =
                new GvDataType<>(GvCriterion.class, "criterion", "criteria");
        public static final Parcelable.Creator<GvCriterion> CREATOR = new Parcelable
                .Creator<GvCriterion>() {
            public GvCriterion createFromParcel(Parcel in) {
                return new GvCriterion(in);
            }

            public GvCriterion[] newArray(int size) {
                return new GvCriterion[size];
            }
        };

        private final String mSubject;
        private final float  mRating;

        public GvCriterion() {
            this(null, 0f);
        }

        public GvCriterion(String subject, float rating) {
            this(null, subject, rating);
        }

        public GvCriterion(GvReviewId id, String subject, float rating) {
            super(GvCriterion.TYPE, id);
            mSubject = subject;
            mRating = rating;
        }

        public GvCriterion(GvCriterion child) {
            this(child.getReviewId(), child.getSubject(), child.getRating());
        }

        GvCriterion(Parcel in) {
            super(in);
            mSubject = in.readString();
            mRating = in.readFloat();
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VhChild();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validateString(mSubject);
        }

        @Override
        public String getStringSummary() {
            return getSubject() + ": " + RatingFormatter.outOfFive(getRating());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(mSubject);
            parcel.writeFloat(mRating);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvCriterion)) return false;
            if (!super.equals(o)) return false;

            GvCriterion that = (GvCriterion) o;

            if (Float.compare(that.mRating, mRating) != 0) return false;
            return !(mSubject != null ? !mSubject.equals(that.mSubject) : that.mSubject != null);

        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (mSubject != null ? mSubject.hashCode() : 0);
            result = 31 * result + (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
            return result;
        }

        public String getSubject() {
            return mSubject;
        }

        public float getRating() {
            return mRating;
        }
    }
}