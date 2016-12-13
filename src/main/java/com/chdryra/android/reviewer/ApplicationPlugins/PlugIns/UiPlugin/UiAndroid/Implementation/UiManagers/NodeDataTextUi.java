/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NodeDataTextUi<T extends HasReviewId, R extends ReviewListReference<T, ?>>
        extends NodeDataUi<T, R, TextView> {
    private final ValueFormatter<T> mFormatter;

    public interface ValueFormatter<T extends HasReviewId> {
        String format(IdableList<T> data);
    }

    public NodeDataTextUi(TextView view,
                          ValueGetter<R> getter,
                          ValueFormatter<T> formatter,
                          @Nullable final Command onClick) {
        super(view, getter, onClick);
        mFormatter = formatter;
    }

    @Override
    protected void updateView(IdableList<T> data) {
        getView().setText(mFormatter.format(data));
    }

    @Override
    protected void setEmpty() {
        getView().setTypeface(getView().getTypeface(), Typeface.ITALIC);
        getView().setText(Strings.FORMATTED.NONE);
    }
}
