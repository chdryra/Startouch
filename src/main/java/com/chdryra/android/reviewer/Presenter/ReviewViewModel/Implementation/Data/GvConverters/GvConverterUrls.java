package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrlList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterUrls extends GvConverterDataReview<DataUrl, GvUrl, GvUrlList> {
    public GvConverterUrls() {
        super(GvUrlList.class);
    }

    @Override
    public GvUrl convert(DataUrl datum) {
        return new GvUrl(newId(datum.getReviewId()), datum.getLabel(), datum.getUrl());
    }
}