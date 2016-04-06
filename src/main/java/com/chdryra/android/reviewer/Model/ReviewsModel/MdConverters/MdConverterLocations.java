/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterLocations extends MdConverterDataReview<DataLocation, MdLocation> {
    @Override
    public MdLocation convert(DataLocation datum, ReviewId reviewId) {
        MdReviewId id = newMdReviewId(reviewId);
        return new MdLocation(id, datum.getLatLng(), datum.getName());
    }
}