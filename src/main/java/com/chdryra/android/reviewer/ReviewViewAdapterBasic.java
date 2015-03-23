/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 February, 2015
 */

package com.chdryra.android.reviewer;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewViewAdapterBasic implements ReviewViewAdapter {
    final ArrayList<GridDataObserver> mObservers = new ArrayList<>();

    @Override
    public void registerGridDataObserver(GridDataObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void notifyGridDataObservers() {
        for (GridDataObserver observer : mObservers) {
            observer.onGridDataChanged();
        }
    }
}
