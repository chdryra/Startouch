/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.widget.LinearLayout;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class DataSectionUi<T extends HasReviewId, Ref extends ReviewListReference<T, ?>>
        extends FormattedSectionUi<Ref> implements ViewUiBinder.BindableViewUi<IdableList<T>>{

    private final ViewUiBinder<IdableList<T>> mBinder;

    protected abstract void updateView(IdableList<T> data);

    protected abstract void setEmpty(String label);

    public DataSectionUi(LinearLayout view, ValueGetter<Ref> getter, String title) {
        super(view, getter, title);
        mBinder = new ViewUiBinder<>(this);
    }

    protected void setView(IdableList<T> data) {
        if(data.size() > 0) {
            updateView(data);
        } else {
            setEmpty(Strings.Formatted.NONE);
        }
    }

    @Override
    public void update(IdableList<T> value) {
        setView(value);
    }

    @Override
    public void onInvalidated() {
        setView(new IdableDataList<T>(getValue().getReviewId()));
    }

    @Override
    public void update() {
        if(!mBinder.isBound()) {
            setEmpty(Strings.Formatted.LOADING);
            mBinder.bind();
        }
    }
}
