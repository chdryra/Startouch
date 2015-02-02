/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityViewReview {

    @Override
    protected Fragment createFragment() {
        return FragmentViewReview.newInstance(GvDataList.GvType.FEED, false);
    }

}
