/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 June, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 24/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvAggregator<T extends GvData> {
    private GvDataList<T> mData;

    private GvAggregator(GvDataList<T> data) {
        mData = data;
    }

}
