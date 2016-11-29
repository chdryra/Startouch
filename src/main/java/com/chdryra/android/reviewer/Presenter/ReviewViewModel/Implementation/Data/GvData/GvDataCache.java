/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.AsyncSortable;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class GvDataCache<T extends GvData> implements AsyncSortable<T>{
    private final GvDataList<T> mData;

    public GvDataCache(GvDataList<T> data) {
        mData = data;
    }

    public GvDataList<T> getData() {
        return mData;
    }

    @Override
    public void sort(Comparator<? super T> comparator, AsyncSortable.OnSortedCallback callback) {
        mData.sort(comparator);
        callback.onSorted(CallbackMessage.ok());
    }
}
