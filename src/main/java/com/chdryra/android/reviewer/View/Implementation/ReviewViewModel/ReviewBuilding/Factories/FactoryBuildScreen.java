package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.BuildScreen;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.BuildScreenModifier;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GridItemClickObserved;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.MenuBuildScreen;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.RatingBarBuildScreen;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.SubjectEditBuildScreen;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryBuildScreen {
    private static final int SCREEN_TITLE = R.string.screen_title_build_review;

    public BuildScreen newScreen(Context context,
                                 ConfigDataUi uiConfig,
                                 ReviewBuilderAdapter<?> builder,
                                 LaunchableUiLauncher launchablefactory,
                                 FactoryReviewEditor editorFactory) {
        ReviewEditor<?> editor = editorFactory.newEditor(builder,
                getReviewViewParams(),
                getReviewViewActions(context),
                getModifier(launchablefactory, uiConfig.getShareReviewConfig()));

        return new BuildScreen(editor, uiConfig, launchablefactory);
    }

    @NonNull
    private ReviewViewParams getReviewViewParams() {
        ReviewViewParams params = new ReviewViewParams();
        params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);
        return params;
    }

    @NonNull
    private BuildScreenModifier getModifier(LaunchableUiLauncher launchablefactory,
                                            LaunchableConfig shareScreenConfig) {
        return new BuildScreenModifier(launchablefactory, shareScreenConfig);
    }

    @NonNull
    private ReviewViewActions<?> getReviewViewActions(Context context) {
        String screenTitle = context.getResources().getString(SCREEN_TITLE);
        String buttonTitle = context.getResources().getString(R.string.button_add_review_data);
        return new ReviewViewActions<>(new SubjectEditBuildScreen<>(),
                new RatingBarBuildScreen<>(), new BannerButtonActionNone<>(buttonTitle),
                new GridItemClickObserved<>(), new MenuBuildScreen<>(screenTitle));
    }
}