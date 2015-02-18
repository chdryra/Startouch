/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.GridView;

import com.chdryra.android.reviewer.ActivityViewReview;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.FragmentViewReview;
import com.chdryra.android.reviewer.GvAdapter;
import com.chdryra.android.reviewer.GvDataList;
import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ActivityViewReviewTest extends
        ActivityInstrumentationTestCase2<ActivityViewReview> {
    protected GvDataList.GvType mDataType;
    protected GvAdapter         mAdapter;
    protected Activity          mActivity;
    protected Solo              mSolo;
    private   boolean           mIsEditable;

    protected abstract void setAdapter();

    public ActivityViewReviewTest(GvDataList.GvType dataType, boolean isEditable) {
        super(ActivityViewReview.class);
        mDataType = dataType;
        mIsEditable = isEditable;
    }

    @SmallTest
    public void testSubjectRating() {
        FragmentViewReview fragment = getFragmentViewReview();
        assertEquals(mAdapter.getSubject(), fragment.getSubject());
        assertEquals(mAdapter.getRating(), fragment.getRating());
    }

    @SmallTest
    public void testActivityLaunches() {
        setUp();
        assertTrue(mSolo.searchText(mDataType.getDatumString()));
    }

    @Override
    protected void setUp() {
        getInstrumentation().setInTouchMode(false);

        Intent i = new Intent();
        ActivityViewReview.packParameters(mDataType, mIsEditable, i);

        setAdapter();

        Administrator admin = Administrator.get(getInstrumentation().getTargetContext());
        admin.pack(mAdapter, i);

        setActivityIntent(i);
        mActivity = getActivity();
        mSolo = new Solo(getInstrumentation(), mActivity);
    }

    protected int getGridSize() {
        return getGridView().getAdapter().getCount();
    }

    protected GvDataList.GvData getGridItem(int position) {
        return (GvDataList.GvData) getGridView().getItemAtPosition(position);
    }

    protected GridView getGridView() {
        ArrayList views = mSolo.getCurrentViews(GridView.class);
        assertEquals(1, views.size());
        return (GridView) views.get(0);
    }

    protected FragmentViewReview getFragmentViewReview() {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentById(ActivityViewReview.FRAGMENT_ID);
        return (FragmentViewReview) f;
    }
}
