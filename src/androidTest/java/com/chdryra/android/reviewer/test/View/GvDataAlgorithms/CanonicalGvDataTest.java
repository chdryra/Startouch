/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class CanonicalGvDataTest<T extends GvData> extends TestCase {
    protected GvDataList<T>          mData;
    protected GvReviewId             mId;
    protected CanonicalDatumMaker<T> mCanonical;

    protected abstract T getTestDatum();

    protected abstract CanonicalDatumMaker<T> getCanonicalMaker();

    protected abstract void additionalTests();

    @SmallTest
    public void testGetCanonical() {
        checkEmpty();
        checkSame();
        additionalTests();
    }

    @Override
    protected void setUp() throws Exception {
        mId = RandomReviewId.nextGvReviewId();
        mCanonical = getCanonicalMaker();
        //TODO make type safe
        mData = newDataList();
    }

    protected GvDataList<T> newDataList() {
        return FactoryGvData.newDataList(getTestDatum().getGvDataType(), mId);
    }

    protected void checkSame() {
        T datum = getTestDatum();
        mData.add(datum);
        T canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        checkEquality(datum, canon);

        mData.add(datum);
        mData.add(datum);

        canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        checkEquality(datum, canon);
    }

    protected void checkEquality(T lhs, T rhs) {
        assertEquals(lhs, rhs);
    }

    private void checkEmpty() {
        T canon = mCanonical.getCanonical(mData);
        assertFalse(canon.isValidForDisplay());
    }
}
