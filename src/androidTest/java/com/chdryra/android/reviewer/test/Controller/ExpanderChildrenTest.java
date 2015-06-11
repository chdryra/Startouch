/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.test.Controller;

import com.chdryra.android.reviewer.Controller.ReviewAdapterModel.ExpanderChildren;
import com.chdryra.android.reviewer.Controller.ReviewAdapterModel.GridDataExpander;
import com.chdryra.android.reviewer.Controller.ReviewAdapterModel.ViewerChildList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderChildrenTest extends ExpanderChildNodeTest {
    protected GridDataExpander getExpander(ViewerChildList wrapper) {
        return new ExpanderChildren(getContext(), wrapper);
    }
}
