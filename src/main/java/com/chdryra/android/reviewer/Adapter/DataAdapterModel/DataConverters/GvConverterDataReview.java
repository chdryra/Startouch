package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataReview;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvConverterDataReview <T1 extends DataReview, T2 extends GvData, T3 extends GvDataList<T2>> 
        extends GvConverterBasic<T1, T2, T3>{

    public GvConverterDataReview(Class<T3> listClass) {
        super(listClass);
    }

    @Override
    public T2 convert(T1 datum, String reviewId) {
        if(!datum.getReviewId().equals(reviewId)) {
            throw new IllegalArgumentException("reviewId must equal datum's getReviewId!");
        }

        return convert(datum);
    }

    @Override
    public abstract T2 convert(T1 datum);
}