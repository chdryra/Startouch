/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Utils.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonical;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemConfigLauncher<T extends GvData> extends GridItemLauncher<T> {
    private static final String TAG = GridItemConfigLauncher.class.getName();
    private final LaunchableConfig mDataConfig;
    private final ParcelablePacker<GvDataParcelable> mPacker;
    private final int mLaunchCode;

    public GridItemConfigLauncher(UiLauncher launcher,
                                  FactoryReviewView launchableFactory,
                                  LaunchableConfig dataConfig) {
        super(launcher, launchableFactory);
        mDataConfig = dataConfig;
        mPacker = new ParcelablePacker<>();
        mLaunchCode = RequestCodeGenerator.getCode(TAG + String.valueOf(mDataConfig.getDefaultRequestCode()));
    }

    @Override
    public void onLongClickExpandable(T item, int position, View v, ReviewViewAdapter<?>
            expanded) {
        GvData datum = item;
        //TODO get rid of this instanceof
        if (item instanceof GvCanonical) {
            GvCanonical canonical = (GvCanonical) item;
            datum = canonical.size() == 1 ? canonical.getItem(0) : canonical.getCanonical();
        }

        launchViewerIfPossible(datum);
    }

    @Override
    public void onClickNotExpandable(T item, int position, View v) {
        launchViewerIfPossible(item);
    }

    @Override
    public void onLongClickNotExpandable(T item, int position, View v) {
        onClickNotExpandable(item, position, v);
    }

    private void launchViewerIfPossible(GvData item) {
        if (item.isCollection() || mDataConfig == null || item.getParcelable() == null) return;
        Bundle bundle = new Bundle();
        mPacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, item.getParcelable(), bundle);
        mDataConfig.launch(new UiLauncherArgs(mLaunchCode).setBundle(bundle));
    }
}
