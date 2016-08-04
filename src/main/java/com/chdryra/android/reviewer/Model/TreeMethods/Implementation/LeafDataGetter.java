/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class LeafDataGetter<T extends HasReviewId> implements NodeDataGetter<T> {
    public abstract T getDataIfLeaf(ReviewNode node);

    @Override
    @Nullable
    public T getData(@NonNull ReviewNode node) {
        return node.isLeaf() ? getDataIfLeaf(node) : null;
    }

    public static class LeafGetter extends LeafDataGetter<ReviewReference> {
        @Override
        @Nullable
        public ReviewReference getDataIfLeaf(@NonNull ReviewNode node) {
            return node.getReference();
        }
    }

    public static class AuthorGetter extends LeafDataGetter<DataAuthorId> {
        @Override
        public DataAuthorId getDataIfLeaf(@NonNull ReviewNode node) {
            return node.getAuthorId();
        }
    }

    public static class SubjectGetter extends LeafDataGetter<DataSubject> {
        @Override
        public DataSubject getDataIfLeaf(@NonNull ReviewNode node) {
            return node.getSubject();
        }
    }

    public static class DateGetter extends LeafDataGetter<DataDate> {
        @Override
        public DataDate getDataIfLeaf(@NonNull ReviewNode node) {
            return node.getPublishDate();
        }
    }
}
