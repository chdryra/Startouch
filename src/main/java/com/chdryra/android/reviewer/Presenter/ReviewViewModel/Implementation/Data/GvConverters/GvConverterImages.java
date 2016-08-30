/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterImages extends GvConverterReviewData.RefDataList<DataImage, GvImage, GvImageList, GvImage.Reference> {

    private DataConverter<DateTime, GvDate, ?> mConverter;

    public GvConverterImages(DataConverter<DateTime, GvDate, ?> converter) {
        super(GvImageList.class, GvImage.Reference.TYPE);
        mConverter = converter;
    }

    @Override
    public GvImage convert(DataImage datum, ReviewId reviewId) {
        GvReviewId id = getGvReviewId(datum, reviewId);
        GvDate gvDate = mConverter.convert(datum.getDate());
        return new GvImage(id, datum.getBitmap(), gvDate, datum.getCaption(),
                datum.isCover());
    }

    @Override
    protected GvImage.Reference convertReference(ReviewItemReference<DataImage> reference) {
        return new GvImage.Reference(reference, this);
    }
}
