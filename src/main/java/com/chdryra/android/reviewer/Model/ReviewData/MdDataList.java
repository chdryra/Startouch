/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.mygenerallibrary.SortableList;

/**
 * Review Data: Sortable collection of {@link MdData} objects that itself is considered Review Data
 * <p>
 * {@link #hasData()}: at least 1 object in collection.
 * </p>
 *
 * @param <T>: {@link MdData} type in collection.
 */
public class MdDataList<T extends MdData> extends SortableList<T> implements MdData {
    private final ReviewId mReviewId;

    public MdDataList(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData() {
        return mData.size() > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdDataList)) return false;

        MdDataList that = (MdDataList) o;

        if (!mReviewId.equals(that.mReviewId)) {
            return false;
        }
        if (size() != that.size()) return false;

        for (int i = 0; i < size(); ++i) {
            if (!getItem(i).equals(that.getItem(i))) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        for (T datum : this) {
            result = 31 * result + datum.hashCode();
        }

        return result;
    }
}