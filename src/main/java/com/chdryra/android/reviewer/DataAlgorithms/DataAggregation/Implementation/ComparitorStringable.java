/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ItemGetter;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DifferenceComparitor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorStringable<T> implements DifferenceComparitor<T, DifferencePercentage> {
    private static final ComparitorString COMPARITOR = new ComparitorString();
    private ItemGetter<T, String> mGetter;

    public ComparitorStringable(ItemGetter<T, String> getter) {
        mGetter = getter;
    }

    @Override
    public DifferencePercentage compare(T lhs, T rhs) {
        return COMPARITOR.compare(mGetter.getItem(lhs), mGetter.getItem(rhs));
    }
}
