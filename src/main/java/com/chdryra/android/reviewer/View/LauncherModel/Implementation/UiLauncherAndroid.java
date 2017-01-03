/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.CacheUtils.ItemPacker;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.PermissionResult;
import com.chdryra.android.reviewer.Application.Interfaces.PermissionsSuite;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityEditData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogShower;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 * <p/>
 * Knows how to launch a {@link LaunchableUi} depending on whether
 * it is a Dialog or Activity underneath.
 */

public class UiLauncherAndroid implements UiLauncher {
    private Activity mCommissioner;

    private final EditUiLauncher mEditUiLauncher;
    private final ReviewLauncherImpl mReviewLauncher;
    private final Class<? extends Activity> mDefaultActivity;
    private final ItemPacker<ReviewView<?>> mViewPacker;

    public UiLauncherAndroid(EditUiLauncher editUiLauncher,
                             ReviewLauncherImpl reviewLauncher,
                             Class<? extends Activity> defaultActivity) {
        mEditUiLauncher = editUiLauncher;
        mReviewLauncher = reviewLauncher;

        mEditUiLauncher.setUiLauncher(this);
        mReviewLauncher.setUiLauncher(this);

        mDefaultActivity = defaultActivity;

        mViewPacker = new ItemPacker<>();
    }

    public void setActivity(Activity activity) {
        mCommissioner = activity;
    }

    public void setSession(UserSession session) {
        mReviewLauncher.setSessionAuthor(session.getAuthorId());
    }

    @Override
    public void launch(LaunchableUi ui, UiLauncherArgs args) {
        ui.launch(new AndroidTypeLauncher(mCommissioner, ui.getLaunchTag(), args));
    }

    @Override
    public void launchEditUi(@Nullable ReviewId template) {
        mEditUiLauncher.launch(template);
    }

    @Override
    public void launchEditDataUi(GvDataType<? extends GvDataParcelable> dataType) {
        ActivityEditData.start(mCommissioner, dataType);
    }

    @Override
    public void launchImageChooser(final ImageChooser chooser, final int requestCode) {
        PermissionsSuite permissions = AppInstanceAndroid.getInstance(mCommissioner)
                .getPermissions();
        if(permissions.hasPermissions(PermissionsSuite.Permission.CAMERA)) {
            launchChooser(chooser, requestCode);
        } else {
            permissions.requestPermissions(new PermissionsSuite.PermissionsCallback() {
                @Override
                public void onPermissionsResult(List<PermissionResult> results) {
                    if(results.size() ==1) {
                        PermissionResult result = results.get(0);
                        if(result.isPermission(PermissionsSuite.Permission.CAMERA)
                                && result.isGranted()) {
                            launchChooser(chooser, requestCode);
                        }
                    }
                }
            }, PermissionsSuite.Permission.CAMERA);
        }
    }

    private void launchChooser(ImageChooser chooser, int requestCode) {
        Intent chooserIntents = chooser.getChooserIntents();
        mCommissioner.startActivityForResult(chooserIntents, requestCode);
    }

    @Override
    public ReviewLauncher getReviewLauncher() {
        return mReviewLauncher;
    }

    @Nullable
    public Review unpackTemplate(Bundle args) {
        return mEditUiLauncher.unpack(args);
    }

    @Nullable
    public ReviewNode unpackNode(Bundle args) {
        return mReviewLauncher.unpack(args);
    }

    @Nullable
    public ReviewView<?> unpackView(Intent i) {
        return mViewPacker.unpack(i);
    }

    private class AndroidTypeLauncher implements UiTypeLauncher {
        private final Activity mCommissioner;
        private final String mTag;
        private final UiLauncherArgs mArgs;

        AndroidTypeLauncher(Activity commissioner, String tag, UiLauncherArgs args) {
            mCommissioner = commissioner;
            mTag = tag;
            mArgs = args;
        }

        @Override
        public void launch(DialogFragment launchableUI) {
            DialogShower.show(launchableUI, mCommissioner, mArgs.getRequestCode(), mTag, mArgs.getBundle());
        }

        @Override
        public void launch(Class<? extends Activity> activityClass, String argsKey) {
            Intent i = new Intent(mCommissioner, activityClass);
            i.putExtra(argsKey, mArgs.getBundle());
            if(mArgs.isClearBackStack()) i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mCommissioner.startActivityForResult(i, mArgs.getRequestCode());
        }

        @Override
        public void launch(ReviewView<?> view) {
            launchReviewView(view, mDefaultActivity);
        }

        private void launchReviewView(ReviewView<?> view, Class<? extends Activity> activity) {
            Intent i = new Intent(mCommissioner, activity);
            mViewPacker.pack(view, i);
            mCommissioner.startActivityForResult(i, mArgs.getRequestCode());
        }
    }
}
