package com.chdryra.android.reviewer.View.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Implementation.Configs.ConfigDataUiImpl;
import com.chdryra.android.reviewer.View.Implementation.Configs.DefaultLaunchables;
import com.chdryra.android.reviewer.View.Implementation.Configs.AddEditViewClasses;
import com.chdryra.android.reviewer.View.Implementation.Configs.LaunchableConfigImpl;
import com.chdryra.android.reviewer.View.Implementation.Configs.LaunchableConfigsHolder;
import com.chdryra.android.reviewer.View.Implementation.Configs.LaunchablesList;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryConfigDataUi {
    private static final String REVIEW_BUILD_TAG = "ReviewBuilderScreen";
    private static final int REVIEW_BUILD = RequestCodeGenerator.getCode(REVIEW_BUILD_TAG);
    private static final String EDIT_ON_MAP_TAG = "EditOnMap";
    private static final int EDIT_ON_MAP = RequestCodeGenerator.getCode(EDIT_ON_MAP_TAG);
    private static final String SHARE_REVIEW_TAG = "ShareReview";
    private static final int SHARE_REVIEW = RequestCodeGenerator.getCode(SHARE_REVIEW_TAG);

    public ConfigDataUi getDefaultConfig() {
        LaunchablesList defaultlaunchables = new DefaultLaunchables();
        return getConfigDataUi(defaultlaunchables);
    }

    @NonNull
    private ConfigDataUi getConfigDataUi(LaunchablesList classes) {
        ArrayList<LaunchableConfigsHolder<?>> dataConfigs = new ArrayList<>();

        for (AddEditViewClasses<?> uiClasses : classes.getDataLaunchables()) {
            dataConfigs.add(new LaunchableConfigsHolder<>(uiClasses));
        }

        LaunchableConfig builder = getReviewBuilderConfig(classes);
        LaunchableConfig mapper = getEditOnMapConfig(classes);
        LaunchableConfig sharer = getShareReviewConfig(classes);
        return new ConfigDataUiImpl(dataConfigs, builder, mapper, sharer);
    }

    private LaunchableConfig getReviewBuilderConfig(LaunchablesList classes) {
        return new LaunchableConfigImpl(classes.getReviewBuilderConfig(), REVIEW_BUILD, REVIEW_BUILD_TAG);
    }

    private LaunchableConfig getEditOnMapConfig(LaunchablesList classes) {
        return new LaunchableConfigImpl(classes.getMapEditorConfig(), EDIT_ON_MAP, EDIT_ON_MAP_TAG);
    }

    private LaunchableConfig getShareReviewConfig(LaunchablesList classes) {
        return new LaunchableConfigImpl(classes.getShareConfig(), EDIT_ON_MAP, EDIT_ON_MAP_TAG);
    }
}