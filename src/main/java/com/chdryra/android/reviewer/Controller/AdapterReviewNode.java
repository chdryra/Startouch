/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer.Controller;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.View.GvDataCollection;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvReviewId;

/**
 * {@link ReviewViewAdapter} for {@link ReviewIdableList} data.
 */
public class AdapterReviewNode extends ReviewViewAdapterBasic {
    private ReviewNode mNode;

    public AdapterReviewNode(ReviewNode node, GridDataWrapper wrapper, GridDataExpander expander) {
        mNode = node;
        setWrapper(wrapper);
        setExpander(expander);
    }

    public AdapterReviewNode(ReviewNode node, GridDataWrapper wrapper) {
        this(node, wrapper, null);
    }

    public ReviewViewAdapter getTreeDataAdapter(Context context) {
        return FactoryReviewViewAdapter.newTreeDataAdapter(context, mNode);
    }

    @Override
    public String getSubject() {
        return mNode.getSubject().get();
    }

    @Override
    public float getRating() {
        return mNode.getRating().get();
    }

    @Override
    public float getAverageRating() {
        if (mNode.isRatingAverageOfChildren()) return getRating();
        VisitorRatingAverageOfChildren visitor = new VisitorRatingAverageOfChildren();
        mNode.acceptVisitor(visitor);
        return visitor.getRating();
    }

    @Override
    public GvImageList getCovers() {
        return MdGvConverter.convert(mNode.getImages().getCovers());
    }

    public static class DataAdapter extends AdapterReviewNode {
        public DataAdapter(Context context, ReviewNode node) {
            super(node, null, null);
            ReviewId id = node.getId();

            GvDataCollection data = new GvDataCollection(GvReviewId.getId(id.toString()));
            data.add(MdGvConverter.getTags(id.toString()));
            data.add(MdGvConverter.convertChildren(node));
            data.add(MdGvConverter.convert(node.getImages()));
            data.add(MdGvConverter.convert(node.getComments()));
            data.add(MdGvConverter.convert(node.getLocations()));
            data.add(MdGvConverter.convert(node.getFacts()));

            GridDataWrapper wrapper = new WrapperGvDataList(data);
            ReviewViewAdapter adapter = new AdapterReviewViewAdapter(context, this, wrapper);

            setWrapper(wrapper);
            setExpander(new ExpanderGridCell(context, adapter));
        }
    }
}
