package com.chdryra.android.reviewer.View.DataAggregation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumLocation implements DataLocation {
    private final String mReviewId;
    private final LatLng mLatLng;
    private final String mName;

    public DatumLocation(String reviewId, LatLng latLng, String name) {
        mReviewId = reviewId;
        mLatLng = latLng;
        mName = name;
    }

    @Override
    public LatLng getLatLng() {
        return mLatLng;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
