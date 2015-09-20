/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;

import java.util.ArrayList;
import java.util.Date;

/**
 * Review Data: Wrapper for a UUID
 * <p>
 * Use static methods <code>generateId(.)</code> to return a unique RDId.
 * </p>
 * <p/>
 * <p>
 * {@link #hasData()}: true
 * </p>
 * <p/>
 * //TODO There's a reason couldn't use holding review but can't remember. Find out.
 */
public class ReviewId3 implements MdData {
    private static final String SPLITTER = "-T-";
    private static ReviewIdGenerator mGenerator;
    private final UserId mId;
    private long mTime;

    private ReviewId3(UserId id, long time) {
        mId = id;
        mTime = time;
    }

    private ReviewId3(String rdId) {
        String[] split = rdId.split(SPLITTER);
        mId = UserId.fromString(split[0]);
        mTime = Long.parseLong(split[1]);
    }

    public synchronized static ReviewId3 generateId(Author author) {
        if (mGenerator == null) mGenerator = new ReviewIdGenerator();
        return mGenerator.generateId(author);
    }

    public static ReviewId3 fromString(String rdId) {
        return new ReviewId3(rdId);
    }

    @Override
    public ReviewId getReviewId() {
        return null;
    }

    @Override
    public boolean hasData() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewId3)) return false;

        ReviewId3 reviewId = (ReviewId3) o;

        if (mTime != reviewId.mTime) return false;
        return mId.equals(reviewId.mId);
    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + (int) (mTime ^ (mTime >>> 32));
        return result;
    }

    public String toString() {
        return mId.toString() + SPLITTER + String.valueOf(mTime);
    }

    /**
     * To facilitate RCollectionReview
     */
    public interface IdAble {
        ReviewId3 getId();
    }

    private static class ReviewIdGenerator {
        private ArrayList<UserId> mUserIds = new ArrayList<>();
        private long mLastTime;

        private ReviewId3 generateId(Author author) {
            UserId id = author.getUserId();
            long time = new Date().getTime();
            if (mUserIds.contains(id)) {
                if (time <= mLastTime) time = mLastTime + 1;
            } else {
                mUserIds.add(id);
            }

            mLastTime = time;

            return new ReviewId3(id, mLastTime);
        }
    }
}