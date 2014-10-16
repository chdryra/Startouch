/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;
import com.chdryra.android.mygenerallibrary.GVData;

/**
 * Created by: Rizwan Choudrey
 * On: 16/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogEditReviewDataFragment<T extends GVData> extends
        DialogCancelDeleteDoneFragment {
    private GVReviewDataList.GVType   mDataType;
    private InputHandlerReviewData<T> mHandler;
    private T                         mDatum;

    protected DialogEditReviewDataFragment(GVReviewDataList.GVType dataType) {
        mDataType = dataType;
    }

    protected abstract View createDialogUI(ViewGroup parent);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new InputHandlerReviewData<T>(mDataType);
        mDatum = mHandler.unpack(InputHandlerReviewData.CurrentNewDatum.CURRENT,
                getArguments());
        setDialogTitle(getResources().getString(R.string.edit) + " " + mHandler.getGVType()
                .getDatumString());
    }

    @Override
    protected void onDeleteButtonClick() {
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, mDatum,
                createNewReturnData());
    }

    @Override
    protected boolean hasDataToDelete() {
        return mDatum != null;
    }

    protected abstract T createGVData();

    protected GVReviewDataList.GVType getGVType() {
        return mDataType;
    }

    protected T getDatum() {
        return mDatum;
    }

    @Override
    protected void onDoneButtonClick() {
        Intent data = createNewReturnData();
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, mDatum, data);
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.NEW, createGVData(), data);
    }
}
