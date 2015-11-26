/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.GvImageConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Interfaces
        .GridDataViewer;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImageList;

/**
 * {@link ReviewViewAdapter} for a {@link ReviewNode}.
 */

public class AdapterReviewNode<T extends GvData> extends ReviewViewAdapterBasic<T> {
    private ReviewNode mNode;
    private GvImageConverter mCoversConverter;

    //Constructors
    public AdapterReviewNode(ReviewNode node,
                             GvImageConverter coversConverter,
                             GridDataViewer<T> viewer) {
        this(node, coversConverter);
        setViewer(viewer);
    }

    public AdapterReviewNode(ReviewNode node,
                             GvImageConverter coversConverter) {
        mNode = node;
        mCoversConverter = coversConverter;
    }

    //Overridden
    @Override
    public GvDataType<T> getGvDataType() {
        return getGridData().getGvDataType();
    }

    @Override
    public String getSubject() {
        return mNode.getSubject().getSubject();
    }

    @Override
    public float getRating() {
        return mNode.getRating().getRating();
    }

    @Override
    public GvImageList getCovers() {
        return mCoversConverter.convert(mNode.getCovers());
    }
}