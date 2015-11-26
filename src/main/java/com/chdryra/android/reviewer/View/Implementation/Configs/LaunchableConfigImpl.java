package com.chdryra.android.reviewer.View.Implementation.Configs;

import android.util.Log;

import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.LauncherUiImpl;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;

/**
 * Encapsulates a configuration for a UI that can add, edit, view review data of a certain
 * {@link GvDataType}. Packages together:
 * <ul>
 * <li>A {@link LaunchableUi} implementation for
 * adding/editing review data of a certain type</li>
 * <li>An integer request code (required when one activity launches another)</li>
 * <li>A String tag that may be used (if ultimately launching a dialog)</li>
 * </ul>
 * The {@link LaunchableUi} is launched using a
 * {@link LauncherUiImpl}
 */
public class LaunchableConfigImpl implements LaunchableConfig {
    private static final String TAG = "LaunchableConfigImpl";
    private final Class<? extends LaunchableUi> mUiClass;
    private final int mRequestCode;
    private final String mTag;

    public LaunchableConfigImpl(Class<? extends LaunchableUi> UiClass,
                                int requestCode, String tag) {
        mUiClass = UiClass;
        mRequestCode = requestCode;
        mTag = tag;
    }


    //public methods
    @Override
    public LaunchableUi getLaunchable(LaunchableUiLauncher launchableFactory) throws RuntimeException {
        if (mUiClass == null) return null;
        try {
            return mUiClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "Couldn't create UI for " + mUiClass.getName(), e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "IllegalAccessException: trying to create " + mUiClass.getName(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRequestCode() {
        return mRequestCode;
    }
}