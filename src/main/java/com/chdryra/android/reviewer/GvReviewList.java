/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.util.Comparator;
import java.util.Date;

/**
 * Used for Review summaries in published feed
 *
 * @see com.chdryra.android.reviewer.Administrator
 * @see com.chdryra.android.reviewer.FragmentFeed
 */
public class GvReviewList extends GvDataList<GvReviewList.GvReviewOverview> {
    public static final GvType TYPE = GvType.REVIEW;

    public GvReviewList() {
        super(TYPE);
    }

    public void add(String id, String subject, float rating, Bitmap coverImage, String headline,
            String locationName, String author, Date publishDate) {
        if (!contains(id)) {
            add(new GvReviewOverview(id, subject, rating, coverImage, headline, locationName,
                    author, publishDate));
        }
    }

    @Override
    protected Comparator<GvReviewOverview> getDefaultComparator() {

        return new Comparator<GvReviewOverview>() {
            @Override
            public int compare(GvReviewOverview lhs, GvReviewOverview rhs) {
                return lhs.getPublishDate().compareTo(rhs.getPublishDate());
            }
        };
    }

    boolean contains(String id) {
        GvReviewOverview review = new GvReviewOverview(id);
        return contains(review);
    }

    /**
     * {@link GvDataList.GvData} version of: {@link Review}
     * {@link ViewHolder): {@link VHReviewNodeOverview}
     */
    public static class GvReviewOverview implements GvDataList.GvData {
        public static final Parcelable.Creator<GvReviewOverview> CREATOR = new Parcelable
                .Creator<GvReviewOverview>() {
            public GvReviewOverview createFromParcel(Parcel in) {
                return new GvReviewOverview(in);
            }

            public GvReviewOverview[] newArray(int size) {
                return new GvReviewOverview[size];
            }
        };
        private final String mId;
        private       String mSubject;
        private       float  mRating;
        private       Bitmap mCoverImage;
        private       String mHeadline;
        private       String mLocationName;
        private       String mAuthor;
        private       Date   mPublishDate;

        private GvReviewOverview(String id) {
            mId = id;
        }

        private GvReviewOverview(String id, String subject, float rating, Bitmap coverImage,
                String headline, String locationName, String Author, Date publishDate) {
            mId = id;
            mSubject = subject;
            mRating = rating;
            mCoverImage = coverImage;
            mHeadline = headline;
            mLocationName = locationName;
            mAuthor = Author;
            mPublishDate = publishDate;
        }

        private GvReviewOverview(Parcel in) {
            mId = in.readString();
            mSubject = in.readString();
            mRating = in.readFloat();
            mCoverImage = in.readParcelable(Bitmap.class.getClassLoader());
            mHeadline = in.readString();
            mLocationName = in.readString();
            mAuthor = in.readString();
            mPublishDate = (Date) in.readSerializable();
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VHReviewNodeOverview();
        }

        @Override
        public boolean isValidForDisplay() {
            return mId != null && mId.length() > 0 && mSubject != null && mSubject.length() > 0
                    && mAuthor != null && mAuthor.length() > 0 && mPublishDate != null;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(mId);
            parcel.writeString(mSubject);
            parcel.writeFloat(mRating);
            parcel.writeParcelable(mCoverImage, i);
            parcel.writeString(mHeadline);
            parcel.writeString(mLocationName);
            parcel.writeString(mAuthor);
            parcel.writeSerializable(mPublishDate);
        }

        public String getSubject() {
            return mSubject;
        }

        public float getRating() {
            return mRating;
        }

        public Bitmap getCoverImage() {
            return mCoverImage;
        }

        public String getLocationName() {
            return mLocationName;
        }

        public String getHeadline() {
            return mHeadline;
        }

        public String getAuthor() {
            return mAuthor;
        }

        public Date getPublishDate() {
            return mPublishDate;
        }
    }
}
