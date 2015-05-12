/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvReviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ChildrenExpander implements GridDataExpander {
    private Context    mContext;
    private ReviewNode mNode;

    public ChildrenExpander(Context context, ReviewNode node) {
        mContext = context;
        mNode = node;
    }

    @Override
    public boolean isExpandable(GvData datum) {
        GvReviewList.GvReviewOverview overview = (GvReviewList.GvReviewOverview) datum;
        return mNode.getChildren().containsId(ReviewId.fromString(overview.getId()));
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        if (isExpandable(datum)) {
            GvReviewList.GvReviewOverview overview = (GvReviewList.GvReviewOverview) datum;
            ReviewId id = ReviewId.fromString(overview.getId());
            ReviewIdableList<ReviewNode> children = mNode.getChildren();
            if (children.containsId(id)) {
                ReviewNode child = children.get(id);
                GridDataWrapper wrapper = new ChildListWrapper(child);
                GridDataExpander expander = new ChildrenExpander(mContext, child);
                return new ReviewNodeAdapter(children.get(id), wrapper, expander);
            }
        }

        return null;
    }
}
