/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DifferenceComparitor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.NullUserId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalAuthor implements CanonicalDatumMaker<DataAuthorReview> {
    private DifferenceComparitor<? super DataAuthorReview, DifferenceBoolean> mComparitor;

    public CanonicalAuthor(DifferenceComparitor<? super DataAuthorReview, DifferenceBoolean> comparitor) {
        mComparitor = comparitor;
    }

    @Override
    public DataAuthorReview getCanonical(IdableList<? extends DataAuthorReview> data) {
        ReviewId id = data.getReviewId();
        DatumAuthorReview nullAuthor = new DatumAuthorReview(id, "", NullUserId.ID);
        if (data.size() == 0) return nullAuthor;

        DataAuthorReview reference = data.getItem(0);
        DifferenceBoolean none = new DifferenceBoolean(false);
        for (DataAuthorReview author : data) {
            DifferenceBoolean difference = mComparitor.compare(reference, author);
            if (!difference.lessThanOrEqualTo(none)) return nullAuthor;
        }

        return new DatumAuthorReview(id, reference.getName(), reference.getUserId());
    }
}
