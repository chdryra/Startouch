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

import com.chdryra.android.reviewer.ActivityReviewView;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.FragmentReviewView;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.ReviewView;
import com.chdryra.android.reviewer.ReviewViewAdapter;
import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ActivityReviewViewTest extends
        ActivityInstrumentationTestCase2<ActivityReviewView> {
    protected ReviewViewAdapter mAdapter;
    protected Activity          mActivity;
    protected Solo              mSolo;

    protected abstract void setAdapter();

    protected abstract ReviewView getView();

    public ActivityReviewViewTest() {
        super(ActivityReviewView.class);
    }

    @SmallTest
    public void testSubjectRating() {
        FragmentReviewView fragment = getFragmentViewReview();
        assertEquals(mAdapter.getSubject(), fragment.getSubject());
        assertEquals(mAdapter.getRating(), fragment.getRating());
    }

    @Override
    protected void setUp() {
        getInstrumentation().setInTouchMode(false);

        setAdapter();

        Intent i = new Intent();
        Administrator.get(getInstrumentation().getTargetContext()).packView(getView(), i);
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

    protected FragmentReviewView getFragmentViewReview() {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentById(ActivityReviewView.FRAGMENT_ID);
        return (FragmentReviewView) f;
    }
}