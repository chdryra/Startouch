/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeDataGetter;
import com.chdryra.android.reviewer.View.GvChildList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CriteriaCollector {
    private ReviewNode mNode;

    public CriteriaCollector(ReviewNode node) {
        mNode = node;
    }

    public GvChildList collectCriteria() {
        ReviewIdableList<ReviewNode> nodes = collectNodes(mNode);
        ReviewIdableList<ReviewNode> criteriaNodes = new ReviewIdableList<>();
        for (ReviewNode node : nodes) {
            criteriaNodes.add(collectNodes(node.getReview().getTreeRepresentation()));
        }

        GvChildList criteria = new GvChildList(MdGvConverter.convert(mNode.getId()));
        for (ReviewNode node : criteriaNodes) {
            GvChildList children = MdGvConverter.convertChildren(node);
            for (GvChildList.GvChildReview child : children) {
                if (!criteria.contains(child.getSubject())) criteria.add(child);
            }
        }

        return criteria;
    }

    private ReviewIdableList<ReviewNode> collectNodes(ReviewNode node) {
        TreeDataGetter getter = new TreeDataGetter(node);
        return getter.getNodes();
    }
}