/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class ViewerNodeBasic<T extends GvData> extends GridDataWrapperBasic<T>
        implements ReviewNode.NodeObserver {
    private final ReviewNode mNode;
    private GvDataList<T> mCache;
    private final GvDataType<?> mType;

    protected abstract GvDataList<T> makeGridData();

    ViewerNodeBasic(ReviewNode node, GvDataType<?> type) {
        mNode = node;
        mType = type;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        mCache = null;
        mNode.registerObserver(this);
    }

    @Override
    protected void onDetach() {
        mNode.unregisterObserver(this);
        nullifyCache();
        super.onDetach();
    }

    @Override
    public GvDataType<?> getGvDataType() {
        return mType;
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        nullifyCacheAndNotify();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        nullifyCacheAndNotify();
    }

    @Override
    public void onNodeChanged() {
        nullifyCacheAndNotify();
    }

    @Override
    public void onTreeChanged() {
        nullifyCacheAndNotify();
    }

    protected void nullifyCacheAndNotify() {
        nullifyCache();
        notifyDataObservers();
    }

    private void nullifyCache() {
        onNullifyCache();
        mCache = null;
    }

    protected void onNullifyCache() {

    }

    @Override
    public GvDataList<T> getGridData() {
        if(mCache == null) mCache = makeGridData();

        return mCache;
    }

    protected void setCache(GvDataList<T> cache) {
        mCache = cache;
        notifyDataObservers();;
    }

    ReviewNode getReviewNode() {
        return mNode;
    }

    @Nullable
    GvDataList<T> getCache() {
        return mCache;
    }
}
