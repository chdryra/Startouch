/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewAdapterImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderAdapterImpl <T extends GvDataParcelable> extends ReviewViewAdapterImpl<T>
    implements DataBuilderAdapter<T> {

    private final ReviewBuilderAdapter mParentBuilder;
    private final ReviewBuilder mBuilder;
    private final DataBuilder<T> mDataBuilder;
    private final GvDataType<T> mType;

    public DataBuilderAdapterImpl(GvDataType<T> type, ReviewBuilderAdapter parentBuilder) {
        mType = type;
        mParentBuilder = parentBuilder;
        mBuilder = parentBuilder.getBuilder();
        mDataBuilder = mBuilder.getDataBuilder(mType);
        resetData();
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public ReviewBuilderAdapter getParentBuilder() {
        return mParentBuilder;
    }

    @Override
    public boolean isRatingAverage() {
        return mBuilder.isRatingAverage();
    }

    @Override
    public float getAverageRating() {
        if (mType.equals(GvCriterion.TYPE)) {
            return ((GvCriterionList) getGridData()).getAverageRating();
        } else {
            return mBuilder.getAverageRating();
        }
    }

    @Override
    public boolean add(T datum) {
        DataBuilder.ConstraintResult res = mDataBuilder.add(datum);
        if (res == DataBuilder.ConstraintResult.PASSED) {
            this.notifyDataObservers();
            return true;
        } else {
            if (res == DataBuilder.ConstraintResult.HAS_DATUM) {
                makeToastHasItem(datum);
            }
            return false;
        }
    }

    @Override
    public void delete(T datum) {
        mDataBuilder.delete(datum);
        this.notifyDataObservers();
    }

    @Override
    public void deleteAll() {
        mDataBuilder.deleteAll();
        this.notifyDataObservers();
    }

    @Override
    public void replace(T oldDatum, T newDatum) {
        DataBuilder.ConstraintResult res = mDataBuilder.replace(oldDatum, newDatum);
        if (res == DataBuilder.ConstraintResult.PASSED) {
            this.notifyDataObservers();
        } else {
            if (res == DataBuilder.ConstraintResult.HAS_DATUM) {
                makeToastHasItem(newDatum);
            }
        }
    }

    @Override
    public void commitData() {
        mDataBuilder.buildData();
        getParentBuilder().notifyDataObservers();
    }

    @Override
    public void resetData() {
        mDataBuilder.resetData();
        this.notifyDataObservers();
    }

    @Override
    public void setRatingIsAverage(boolean ratingIsAverage) {
        getParentBuilder().setRatingIsAverage(ratingIsAverage);
    }

    @Override
    public GvDataList<T> getGridData() {
        return mDataBuilder.getData();
    }

    @Override
    public String getSubject() {
        return getParentBuilder().getSubject();
    }

    @Override
    public void setSubject(String subject) {
        getParentBuilder().setSubject(subject);
    }

    @Override
    public float getRating() {
        return getParentBuilder().getRating();
    }

    @Override
    public void setRating(float rating) {
        getParentBuilder().setRating(rating);
    }

    @Override
    public void getCover(CoverCallback callback) {
        if(mType.equals(GvImage.TYPE)) {
            callback.onAdapterCover(((GvImageList)getGridData()).getRandomCover());
        } else {
            getParentBuilder().getCover(callback);
        }
    }

    @Override
    public GvImage getCover() {
        return mType.equals(GvImage.TYPE) ? ((GvImageList)getGridData()).getCovers().getItem(0)
                : getParentBuilder().getCover();
    }

    private void makeToastHasItem(GvData datum) {
        String toast = Strings.Toasts.HAS_DATA + " " + datum.getGvDataType().getDatumName();
        getReviewView().getCurrentScreen().showToast(toast);
    }
}

