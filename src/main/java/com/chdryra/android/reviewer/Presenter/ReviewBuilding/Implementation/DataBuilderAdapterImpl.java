/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.util.Log;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewAdapterImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderAdapterImpl<T extends GvDataParcelable> extends ReviewViewAdapterImpl<T>
        implements DataBuilderAdapter<T>, DataObservable.DataObserver {

    private final GvDataType<T> mType;
    private final ReviewBuilderAdapter<?> mParentBuilder;

    public DataBuilderAdapterImpl(GvDataType<T> type, ReviewBuilderAdapter<?> parentBuilder) {
        mType = type;
        mParentBuilder = parentBuilder;
    }

    public void attach() {
        Log.i("Detach", "Attaching DataBuilderAdapter " + mType.getDataName());
        registerObserver(mParentBuilder);
        getDataBuilder().registerObserver(this);
        notifyDataObservers();
    }

    public void detach() {
        Log.i("Detach", "Detaching DataBuilderAdapter " + mType.getDataName());
        unregisterObserver(mParentBuilder);
        getDataBuilder().unregisterObserver(this);
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public boolean isRatingAverage() {
        return getBuilder().isRatingAverage();
    }

    @Override
    public float getCriteriaAverage() {
        if (mType.equals(GvCriterion.TYPE)) {
            return ((GvCriterionList) getGridData()).getAverageRating();
        } else {
            return getBuilder().getAverageRating();
        }
    }

    @Override
    public boolean add(T datum) {
        DataBuilder.ConstraintResult res = getDataBuilder().add(datum);
        if (res == DataBuilder.ConstraintResult.PASSED) {
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
        getDataBuilder().delete(datum);
    }

    @Override
    public void deleteAll() {
        getDataBuilder().deleteAll();
    }

    @Override
    public void replace(T oldDatum, T newDatum) {
        DataBuilder.ConstraintResult res = getDataBuilder().replace(oldDatum, newDatum);
        if (res == DataBuilder.ConstraintResult.HAS_DATUM) makeToastHasItem(newDatum);
    }

    @Override
    public void commitData() {
        getDataBuilder().buildData();
    }

    @Override
    public void resetData() {
        getDataBuilder().resetData();
    }

    @Override
    public void onDataChanged() {
        notifyDataObservers();
    }

    @Override
    public void setRatingIsAverage(boolean ratingIsAverage) {
        mParentBuilder.setRatingIsAverage(ratingIsAverage);
    }

    @Override
    public GvDataList<T> getGridData() {
        return getDataBuilder().getData();
    }

    @Override
    public String getSubject() {
        return mParentBuilder.getSubject();
    }

    @Override
    public void setSubject(String subject) {
        mParentBuilder.setSubject(subject);
    }

    @Override
    public float getRating() {
        return mParentBuilder.getRating();
    }

    @Override
    public void setRating(float rating) {
        mParentBuilder.setRating(rating);
    }

    @Override
    public void getCover(CoverCallback callback) {
        if (mType.equals(GvImage.TYPE)) {
            callback.onAdapterCover(((GvImageList) getGridData()).getRandomCover());
        } else {
            mParentBuilder.getCover(callback);
        }
    }

    @Override
    public GvImage getCover() {
        return mType.equals(GvImage.TYPE) ? ((GvImageList) getGridData()).getCovers().getItem(0)
                : mParentBuilder.getCover();
    }

    @Override
    protected void onAttach() {
        attach();
    }

    @Override
    protected void onDetach() {
        detach();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataBuilderAdapterImpl)) return false;

        DataBuilderAdapterImpl<?> that = (DataBuilderAdapterImpl<?>) o;

        if (!mParentBuilder.equals(that.mParentBuilder)) return false;
        if (!getBuilder().equals(that.getBuilder())) return false;
        if (!getDataBuilder().equals(that.getDataBuilder())) return false;
        return mType.equals(that.mType);

    }

    @Override
    public int hashCode() {
        int result = mParentBuilder.hashCode();
        result = 31 * result + getBuilder().hashCode();
        result = 31 * result + getDataBuilder().hashCode();
        result = 31 * result + mType.hashCode();
        return result;
    }

    private DataBuilder<T> getDataBuilder() {
        return getBuilder().getDataBuilder(mType);
    }

    private ReviewBuilder getBuilder() {
        return mParentBuilder.getBuilder();
    }

    private void makeToastHasItem(GvData datum) {
        String toast = Strings.Toasts.HAS_DATA + " " + datum.getGvDataType().getDatumName();
        getReviewView().getCurrentScreen().showToast(toast);
    }
}

