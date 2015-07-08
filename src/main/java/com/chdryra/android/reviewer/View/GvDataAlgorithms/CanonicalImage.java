/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalImage implements CanonicalDatumMaker<GvImageList.GvImage> {
    @Override
    public GvImageList.GvImage getCanonical(GvDataList<GvImageList.GvImage> data) {
        GvImageList.GvImage nullImage = new GvImageList.GvImage(data.getReviewId(), null, null,
                "", false);
        if (data.size() == 0) return nullImage;

        GvImageList.GvImage reference = data.getItem(0);
        ComparitorGvImageBitmap comparitor = new ComparitorGvImageBitmap();
        DifferenceBoolean none = new DifferenceBoolean(false);
        int numCaptions = 0;
        Date finalDate = reference.getDate();
        for (GvImageList.GvImage image : data) {
            if (!comparitor.compare(reference, image).lessThanOrEqualTo(none)) return nullImage;
            if (DataValidator.validateString(image.getCaption())) numCaptions++;
            Date imageDate = image.getDate();
            if (imageDate.after(finalDate)) finalDate = imageDate;
        }

        String caption = String.valueOf(numCaptions) + " captions";
        return new GvImageList.GvImage(data.getReviewId(), reference.getBitmap(), finalDate,
                caption,
                true);
    }
}