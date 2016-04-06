/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Interfaces;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Visitor pattern for {@link ReviewNode}s
 */
public interface VisitorReviewNode {
    void visit(@NonNull ReviewNode reviewNode);
}