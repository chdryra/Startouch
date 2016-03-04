/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 14/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

import java.util.ArrayList;

/**
 * Handles user inputs of review data. Checks validity of data and compares user input to current
 *
 * @param <T>: {@link GvData} type.
 */
public class DataBuilderImpl<T extends GvData> implements DataBuilder<T> {
    private final AddConstraint<T> mAddConstraint;
    private final ReplaceConstraint<T> mReplaceConstraint;
    private final ArrayList<DataBuilderObserver> mObservers;
    
    private GvDataList<T> mResetData;
    private GvDataList<T> mData;
    private FactoryGvData mCopier;

    //Constructors
    public DataBuilderImpl(GvDataList<T> data, FactoryGvData copier) {
        this(data, copier, new AddConstraintDefault<T>());
    }

    public DataBuilderImpl(GvDataList<T> data,
                           FactoryGvData copier,
                           AddConstraint<T> addConstraint) {
        this(data, copier, addConstraint, new ReplaceConstraintDefault<T>());
    }

    public DataBuilderImpl(GvDataList<T> data,
                           FactoryGvData copier,
                           AddConstraint<T> addConstraint,
                           ReplaceConstraint<T> replaceConstraint) {
        mAddConstraint = addConstraint;
        mReplaceConstraint = replaceConstraint;
        mObservers = new ArrayList<>();
        mResetData = data;
        mCopier = copier;
        resetData();
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mData.getGvDataType();
    }

    @Override
    public GvDataList<T> getData() {
        return mData;
    }

    @Override
    public ConstraintResult add(T newDatum) {
        ConstraintResult res;
        if (isValid(newDatum)) {
            res = mAddConstraint.passes(mData, newDatum);
            if (res == ConstraintResult.PASSED) mData.add(newDatum);
        } else {
            res = ConstraintResult.INVALID_DATUM;
        }

        return res;
    }

    @Override
    public ConstraintResult replace(T oldDatum, T newDatum) {
        ConstraintResult res;
        if(oldDatum.equals(newDatum)) {
            res = ConstraintResult.OLD_EQUALS_NEW;
        } else if(!isValid(oldDatum) || !isValid(newDatum)) {
            res = ConstraintResult.INVALID_DATUM;
        } else {
            res = mReplaceConstraint.passes(mData, oldDatum, newDatum);
            if (res == ConstraintResult.PASSED) {
                mData.remove(oldDatum);
                mData.add(newDatum);
            }
        }

        return res;
    }

    @Override
    public void delete(T data) {
        mData.remove(data);
    }

    @Override
    public void deleteAll() {
        mData.clear();
    }

    @Override
    public void resetData() {
        mData = mCopier.copy(mResetData);
    }

    @Override
    public void registerObserver(DataBuilderObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void buildData() {
        mResetData = mData;
        for(DataBuilderObserver observer : mObservers) {
            observer.onDataPublished(this);
        }
        resetData();
    }

    private boolean isValid(T datum) {
        return datum != null && datum.isValidForDisplay();
    }
}
