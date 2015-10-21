package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DefaultParameters {
    //Static methods
    public static ReviewViewParams getParams(GvDataType dataType) {
        ReviewViewParams params = new ReviewViewParams();
        if (dataType.equals(GvImageList.GvImage.TYPE)) {
            ReviewViewParams.CellDimension half = ReviewViewParams.CellDimension.HALF;
            params.setCellHeight(half).setCellWidth(half);
        }

        return params;
    }
}
