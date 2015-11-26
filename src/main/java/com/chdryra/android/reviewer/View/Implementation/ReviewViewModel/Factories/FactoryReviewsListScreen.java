package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvReviewOverview;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Builders.BuilderChildListView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.SubjectAction;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsListScreen {
    private BuilderChildListView mBuilder;
    private LaunchableUiLauncher mLaunchableFactory;

    public FactoryReviewsListScreen(LaunchableUiLauncher launchableFactory,
                                    BuilderChildListView childListBuilder) {
        mLaunchableFactory = launchableFactory;
        mBuilder = childListBuilder;
    }

    public ReviewView<GvReviewOverview> newReviewsListScreen(ReviewNode node,
                                           FactoryReviewViewAdapter adapterFactory) {
        return mBuilder.buildView(node, adapterFactory, getActions());
    }

    @NonNull
    private ReviewViewActions<GvReviewOverview> getActions() {
        SubjectAction<GvReviewOverview> subject = new SubjectActionNone<>();
        RatingBarAction<GvReviewOverview> rb = new RatingBarExpandGrid<>(mLaunchableFactory);
        BannerButtonAction<GvReviewOverview> bb = new BannerButtonActionNone<>();
        GridItemAction<GvReviewOverview> giAction
                = new GridItemLauncher<>(mLaunchableFactory);
        MenuAction<GvReviewOverview> menuAction = new MenuActionNone<>();

        return new ReviewViewActions<>(subject, rb, bb, giAction, menuAction);
    }
}