/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.DataConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Grid data is {@link GvReviewOverviewList}.
 */
public class ViewerChildList implements GridDataViewer<GvReviewOverviewList.GvReviewOverview> {
    private ReviewNode mNode;
    private DataConverter<Review, GvReviewOverviewList.GvReviewOverview, GvReviewOverviewList> mConverter;
    private FactoryReviewViewAdapter mAdapterFactory;

    //Constructors
    public ViewerChildList(ReviewNode node,
                           DataConverter<Review, GvReviewOverviewList.GvReviewOverview, GvReviewOverviewList> converter,
                           FactoryReviewViewAdapter adapterFactory) {
        mNode = node;
        mConverter = converter;
        mAdapterFactory = adapterFactory;
    }

    private ReviewViewAdapter newNodeDataAdapter(ReviewNode node) {
        return mAdapterFactory.newNodeDataAdapter(node);
    }

    //Overridden
    @Override
    public GvReviewOverviewList getGridData() {
        return mConverter.convert(mNode.getChildren());
    }

    @Override
    public boolean isExpandable(GvReviewOverviewList.GvReviewOverview datum) {
        return mNode.hasChild(datum.getReviewId());
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvReviewOverviewList.GvReviewOverview datum) {
        if (isExpandable(datum)) {
            return newNodeDataAdapter(mNode.getChild(datum.getReviewId()));
        } else {
            return null;
        }
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return newNodeDataAdapter(mNode);
    }
}