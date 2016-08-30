/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Used for Review summaries in published feed
 *
 * @see AndroidAppInstance
 */
public class GvReviewList extends GvDataListParcelable<GvReview> {
    public static final Creator<GvReviewList> CREATOR = new Creator<GvReviewList>() {
        @Override
        public GvReviewList createFromParcel(Parcel in) {
            return new GvReviewList(in);
        }

        @Override
        public GvReviewList[] newArray(int size) {
            return new GvReviewList[size];
        }
    };

    //Constructors
    public GvReviewList() {
        super(GvReview.TYPE, null);
    }

    public GvReviewList(Parcel in) {
        super(in);
    }

    public GvReviewList(GvReviewId parentId) {
        super(GvReview.TYPE, parentId);
    }

    public GvReviewList(GvReviewList data) {
        super(data);
    }

    private boolean contains(ReviewId id) {
        for (GvReview review : this) {
            if (review.getReviewId().equals(id)) return true;
        }

        return false;
    }

    @Override
    public boolean add(GvReview overview) {
        return !contains(overview.getReviewId()) && super.add(overview);
    }

    @Override
    public boolean contains(Object object) {
        try {
            GvReview item = (GvReview) object;
            return contains(item.getReviewId());
        } catch (ClassCastException e) {
            return false;
        }
    }
}
