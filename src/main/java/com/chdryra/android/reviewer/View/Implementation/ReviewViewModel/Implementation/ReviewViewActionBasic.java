/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewViewAction;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewViewActionBasic<T extends GvData> implements ReviewViewAction<T> {
    private ReviewView<T> mReviewView;

    //public methods
    @Override
    public ReviewView<T> getReviewView() {
        return mReviewView;
    }

    @Override
    public ReviewViewAdapter<T> getAdapter() {
        return mReviewView.getAdapter();
    }

    @Override
    public Activity getActivity() {
        return mReviewView != null ? mReviewView.getActivity() : null;
    }

    @Override
    public void attachReviewView(ReviewView<T> reviewView) {
        if (mReviewView != null) onUnattachReviewView();
        mReviewView = reviewView;
        onAttachReviewView();
    }

    @Override
    public void onUnattachReviewView() {

    }

    @Override
    public void onAttachReviewView() {

    }

    //protected methods
    protected GvDataList<T> getGridData() {
        return getReviewView().getGridData();
    }
}
