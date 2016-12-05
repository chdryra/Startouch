/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Algorithms.DataSorting;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.RatingDefinition;

/**
 * Created by: Rizwan Choudrey
 * On: 05/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class RatingRange extends BucketRange<Float> {
    public RatingRange(Float min, Float max, boolean closed) {
        super(min, max, closed);
        RatingDefinition.throwIfOutOfRange(min);
        RatingDefinition.throwIfOutOfRange(max);
    }

    @Override
    public boolean inRange(Float aFloat) {
        return RatingDefinition.isRating(aFloat) &&
                aFloat >= getMin() && isClosed() ? aFloat <= getMax() : aFloat < getMax();
    }
}
