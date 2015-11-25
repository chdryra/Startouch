/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalAuthor;
import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvAuthor;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalAuthorTest extends CanonicalGvDataTest<GvAuthor> {
    private static final GvAuthor AUTHOR1 = GvDataMocker.newAuthor(null);
    private static final GvAuthor AUTHOR2 = GvDataMocker.newAuthor(null);
    private static final GvAuthor AUTHOR3 = GvDataMocker.newAuthor(null);
    private static final GvAuthor AUTHOR4 = GvDataMocker.newAuthor(null);

    public void testGetCanon() {
        super.testGetCanonical();
    }

    //protected methods
    @Override
    protected GvAuthor getTestDatum() {
        return AUTHOR1;
    }

    @Override
    protected CanonicalDatumMaker<GvAuthor> getCanonicalMaker() {
        return new CanonicalAuthor();
    }

    private void checkDifferentAuthorsNotValid() {
        mData = newDataList();
        mData.add(AUTHOR1);
        mData.add(AUTHOR2);
        mData.add(AUTHOR3);
        mData.add(AUTHOR4);
        GvAuthor canon = mCanonical.getCanonical(mData);
        assertFalse(canon.isValidForDisplay());
    }

    //Overridden
    @Override
    protected void additionalTests() {
        checkDifferentAuthorsNotValid();
    }
}
