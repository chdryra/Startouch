/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;

/**
 * Created by: Rizwan Choudrey
 * On: 13/12/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataExpandableUi<T extends HasReviewId> extends DataSectionUi<T, RefDataList<T>> {
    private final Context mContext;
    private final ViewHolderFactory<?> mFactory;
    private final DataConverter<T, ? extends ViewHolderData, ?> mConverter;

    public DataExpandableUi(Context context,
                            LinearLayout view,
                            String title,
                            ValueGetter<RefDataList<T>> getter,
                            ViewHolderFactory<?> factory,
                            DataConverter<T, ? extends ViewHolderData, ?> converter) {
        super(view, getter, title);
        mContext = context;
        mFactory = factory;
        mConverter = converter;
    }

    @Override
    protected void setEmpty() {
        TextView placeholder = getValueView();
        placeholder.setTypeface(placeholder.getTypeface(), Typeface.ITALIC);
        placeholder.setText(Strings.Formatted.NONE);
    }

    @Override
    protected void updateView(IdableList<T> data) {
        getValueView().setVisibility(View.GONE);
        LinearLayout layout = new LinearLayout(getView().getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        for (T datum : data) {
            ViewHolder vh = mFactory.newViewHolder();
            vh.inflate(mContext, null);
            vh.updateView(mConverter.convert(datum));
            layout.addView(vh.getView());
        }
        getView().addView(layout);
    }
}
