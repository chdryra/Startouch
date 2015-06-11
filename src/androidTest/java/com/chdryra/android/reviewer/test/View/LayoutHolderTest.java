/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 December, 2014
 */

package com.chdryra.android.reviewer.test.View;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import com.chdryra.android.reviewer.View.Dialogs.LayoutHolder;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutHolderTest extends AndroidTestCase {
    private static final int   LAYOUT  = com.chdryra.android.reviewer.R.layout.dialog_holder_test;
    private static final int[] VIEWIDS = {com.chdryra.android.reviewer.R.id.edit_text1,
            com.chdryra.android.reviewer.R.id.edit_text2};

    private LayoutHolder mHolder;

    @SmallTest
    public void testInflateAndGetView() {
        assertNull(mHolder.getView());
        mHolder.inflate(getContext());
        View v = mHolder.getView();
        assertNotNull(v);
        for (int viewId : VIEWIDS) {
            assertNotNull(v.findViewById(viewId));
        }
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mHolder = getHolder();
    }

    private LayoutHolder getHolder() {
        return new LayoutHolder(LAYOUT, VIEWIDS);
    }
}
