/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

/**
 * UI Activity holding {@link FragmentReviewURLBrowser}: browsing and searching URLs (currently
 * disabled).
 */
public class ActivityReviewURLBrowser extends ActivitySingleFragment implements ReviewDataUI {

    @Override
    protected Fragment createFragment() {
        return new FragmentReviewURLBrowser();
    }

    @Override
    public void launch(ReviewDataUILauncher launcher) {
        launcher.launch(this);
    }
}
