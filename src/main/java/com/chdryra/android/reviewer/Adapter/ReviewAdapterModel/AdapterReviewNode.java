/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * {@link ReviewViewAdapter} for a {@link ReviewNode}.
 */

public class AdapterReviewNode<T extends GvData> extends ReviewViewAdapterBasic<T> {
    private ReviewNode mNode;
    private MdGvConverter mConverter;

    //Constructors
    public AdapterReviewNode(ReviewNode node,
                             MdGvConverter converter,
                             GridDataViewer<T> viewer) {
        this(node, converter);
        setViewer(viewer);
    }

    public AdapterReviewNode(ReviewNode node, MdGvConverter converter) {
        mNode = node;
        mConverter = converter;
    }

    //Overridden
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
        return mConverter.toGvDataList(mNode.getImages().getCovers());
    }
}
