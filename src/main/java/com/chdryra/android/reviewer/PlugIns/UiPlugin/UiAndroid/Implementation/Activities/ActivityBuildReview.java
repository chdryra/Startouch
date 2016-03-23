/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .GooglePlacesApi.CallBackSignaler;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PresenterReviewBuild;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .NewReviewListener;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityBuildReview extends ActivityReviewView {
    private static final String TAG = "BuildScreen";
    private static final String TEMPLATE_ID = "TemplateId";
    private static final int TIMEOUT = 10;
    private PresenterReviewBuild mPresenter;
    private CallBackSignaler mSignaler;

    @Override
    protected ReviewView createReviewView() {
        buildPresenterAsync();

        mSignaler.waitForSignal();
        if (mSignaler.timedOut()) {
            //TODO deal with this more elegantly
            finish();
            return null;
        } else {
            return mPresenter.getEditor();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), TEMPLATE_ID);
    }

    private void buildPresenterAsync() {
        ApplicationInstance app = ApplicationInstance.getInstance(this);
        Bundle args = getIntent().getBundleExtra(TEMPLATE_ID);
        String id = args != null ? args.getString(NewReviewListener.TEMPLATE_ID) : null;

        mSignaler = new CallBackSignaler(TIMEOUT);
        new PresenterReviewBuild.Builder(app, new FactoryReviewEditor())
                .setTemplateReview(id)
                .build(new PresenterReviewBuild.Builder.BuildCallback() {
                    @Override
                    public void onBuildFinished(PresenterReviewBuild<?> presenter) {
                        mPresenter = presenter;
                        mSignaler.signal();
                    }
                });
    }
}
