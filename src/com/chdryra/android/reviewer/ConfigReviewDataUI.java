/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.chdryra.android.reviewer.GVReviewDataList.GVType;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the range of dialogs and activities available to return to the user when the user
 * chooses to add, edit or display the data.
 *
 * <p>
 *     Retrieves relevant add, edit and display UIs for each {@link com.chdryra.android.reviewer
 *     .GVReviewDataList.GVType} from {@link com.chdryra.android.reviewer.ConfigAddEditDisplay}
 *     and packages them with request codes and tags so that they can be appropriately launched
 *     by whichever UI needs them in response to a user interaction.
 * </p>
 *
 * @see com.chdryra.android.reviewer.FragmentReviewBuild;
 */
class ConfigReviewDataUI {
    private final static String TAG             = "ConfigReviewDataUI";
    private final static int    DATA_ADD        = 1;
    private final static int    DATA_EDIT       = 2;
    private static       int    REQUEST_COUNTER = 0;
    private static ConfigReviewDataUI sConfigReviewDataUI;

    private final Map<GVType, Config> mConfigsMap = new HashMap<GVType, Config>();

    private ConfigReviewDataUI() {
        mConfigsMap.put(GVType.TAGS, new Config(GVType.TAGS));
        mConfigsMap.put(GVType.CHILDREN, new Config(GVType.CHILDREN));
        mConfigsMap.put(GVType.COMMENTS, new Config(GVType.COMMENTS));
        mConfigsMap.put(GVType.IMAGES, new Config(GVType.IMAGES));
        mConfigsMap.put(GVType.FACTS, new Config(GVType.FACTS));
        mConfigsMap.put(GVType.LOCATIONS, new Config(GVType.LOCATIONS));
        mConfigsMap.put(GVType.URLS, new Config(GVType.URLS));
    }

    static Config get(GVType dataType) {
        return getConfigsMap().get(dataType);
    }

    static ReviewDataUI getReviewDataUI(Class<? extends ReviewDataUI> uiClass)
            throws RuntimeException {
        try {
            return uiClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "Couldn't create UI for " + uiClass.getName(), e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "IllegalAccessEception: trying to create " + uiClass.getName()
                    , e);
            throw new RuntimeException(e);
        }
    }

    private static Map<GVType, Config> getConfigsMap() {
        if (sConfigReviewDataUI == null) {
            sConfigReviewDataUI = new ConfigReviewDataUI();
        }

        return sConfigReviewDataUI.mConfigsMap;
    }

    /**
     * Encapsulates add, edit and display configs for a given
     * {@link com.chdryra.android.reviewer.GVReviewDataList.GVType}.
     */
    class Config {
        private final GVType                  mDataType;
        private final ReviewDataUIConfig      mAddConfig;
        private final ReviewDataUIConfig      mEditConfig;
        private final ReviewDataDisplayConfig mDisplayConfig;

        private Config(GVType dataType) {
            mDataType = dataType;
            mAddConfig = initAddConfig();
            mEditConfig = initEditConfig();
            mDisplayConfig = initDisplayConfig();
        }

        GVType getGVType() {
            return mDataType;
        }

        ReviewDataUIConfig getAdderConfig() {
            return mAddConfig;
        }

        ReviewDataUIConfig getEditorConfig() {
            return mEditConfig;
        }

        ReviewDataDisplayConfig getDisplayConfig() {
            return mDisplayConfig;
        }

        private ReviewDataUIConfig initAddConfig() {
            String tag = "DIALOG_" + mDataType.getDatumString().toUpperCase() + "_ADD_TAG";
            return new ReviewDataUIConfig(mDataType, ConfigAddEditDisplay.getAddClass
                    (mDataType), DATA_ADD, tag);
        }

        private ReviewDataUIConfig initEditConfig() {
            String tag = "DIALOG_" + mDataType.getDatumString().toUpperCase() + "_EDIT_TAG";
            return new ReviewDataUIConfig(mDataType, ConfigAddEditDisplay.getEditClass
                    (mDataType), DATA_EDIT, tag);
        }

        private ReviewDataDisplayConfig initDisplayConfig() {
            REQUEST_COUNTER = REQUEST_COUNTER + 10;
            return new ReviewDataDisplayConfig(mDataType, REQUEST_COUNTER);
        }
    }

    /**
     * Encapsulates a configuration for a UI that can add or edit review data of a certain
     * {@link com.chdryra.android.reviewer.GVReviewDataList.GVType}. Packages together:
     * <ul>
     * <li>A {@link com.chdryra.android.reviewer.ReviewDataUI} implementation for
     * adding/editing review data of a certain type</li>
     * <li>An integer request code (required when one activity launches another)</li>
     * <li>A String tag that may be used (if ultimately launching a dialog)</li>
     * </ul>
     * The ReviewDataUI is launched using a
     * {@link com.chdryra.android.reviewer.ReviewDataUILauncher}
     */
    class ReviewDataUIConfig {
        private final GVType                        mDataType;
        private final Class<? extends ReviewDataUI> mUIClass;
        private final int                           mRequestCode;
        private final String                        mTag;

        private ReviewDataUIConfig(GVType dataType, Class<? extends ReviewDataUI> UIClass,
                                   int requestCode, String tag) {
            mDataType = dataType;
            mUIClass = UIClass;
            mRequestCode = requestCode;
            mTag = tag;
        }


        GVType getGVType() {
            return mDataType;
        }

        ReviewDataUI getReviewDataUI() throws RuntimeException {
            return ConfigReviewDataUI.getReviewDataUI(mUIClass);
        }

        int getRequestCode() {
            return mRequestCode;
        }

        String getTag() {
            return mTag;
        }
    }

    /**
     * Encapsulates a configuration for displaying review data of a certain
     * {@link com.chdryra.android.reviewer.GVReviewDataList.GVType}. Packages together:
     * <ul>
     *     <li>An activity class for displaying a collection of review data of a certain
     * type</li>
     *     <li>An integer request code (required when one activity launches another)</li>
     * </ul>
     * The display activity is accessed by requesting an Intent object which can be used to start
     * activities via, for example, <code>startActivityForResult(.)</code> etc.
     */
    class ReviewDataDisplayConfig {
        private final GVType mDataType;
        private final int    mRequestCode;

        private ReviewDataDisplayConfig(GVType dataType, int requestCode) {
            mDataType = dataType;
            mRequestCode = requestCode;
        }

        GVType getGVType() {
            return mDataType;
        }

        Intent requestIntent(Context context) {
            return new Intent(context, ConfigAddEditDisplay.getDisplayClass(mDataType));
        }

        int getRequestCode() {
            return mRequestCode;
        }
    }
}
