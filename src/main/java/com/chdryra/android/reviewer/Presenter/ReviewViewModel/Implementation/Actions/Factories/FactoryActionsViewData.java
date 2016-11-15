/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonLaunchAuthorReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemConfigLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiReviewOptions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuViewDataDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarExecuteCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchFormattedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewData<T extends GvData> extends FactoryActionsNone<T> {
    private final FactoryReviewView mFactoryView;
    private final FactoryCommands mFactoryCommands;
    private final UiLauncher mLauncher;
    private final LaunchableConfig mGridItemConfig;
    private final LaunchableConfig mOptionsConfig;
    private final ReviewStamp mStamp;
    private final AuthorsRepository mRepo;
    private final ReviewNode mNode;

    public FactoryActionsViewData(GvDataType<T> dataType,
                                  FactoryReviewView factoryView,
                                  FactoryCommands factoryCommands,
                                  ReviewStamp stamp, AuthorsRepository repo, UiLauncher launcher,
                                  LaunchableConfig optionsConfig,
                                  @Nullable LaunchableConfig gridItemConfig,
                                  @Nullable ReviewNode node) {
        super(dataType);
        mFactoryView = factoryView;
        mFactoryCommands = factoryCommands;
        mLauncher = launcher;
        mGridItemConfig = gridItemConfig;
        mOptionsConfig = optionsConfig;
        mStamp = stamp;
        mRepo = repo;
        mNode = node;
    }

    protected UiLauncher getLauncher() {
        return mLauncher;
    }

    @Override
    public MenuAction<T> newMenu() {
        return isSummary() ?
                new MenuViewDataDefault<>(Strings.Screens.SUMMARY, newOptionsMenuItem())
                : new MenuViewDataDefault<>(getDataType(), newOptionsMenuItem());
    }

    @Override
    public RatingBarAction<T> newRatingBar() {
        if (mNode == null) return super.newRatingBar();
        LaunchFormattedCommand command
                = mFactoryCommands.newLaunchFormattedCommand(mLauncher.getReviewLauncher(), mNode);
        return new RatingBarExecuteCommand<>(command, Strings.LOADING);
    }

    @Override
    public BannerButtonAction<T> newBannerButton() {
        return mStamp.isValid() ?
                new BannerButtonLaunchAuthorReviews<T>(mLauncher.getReviewLauncher(), mStamp, mRepo)
                : new BannerButtonActionNone<T>(Strings.Buttons.SUMMARY);
    }

    @Override
    public GridItemAction<T> newGridItem() {
        return isSummary() ?
                new GridItemLauncher<T>(mLauncher, mFactoryView)
                : new GridItemConfigLauncher<T>(mLauncher, mFactoryView, mGridItemConfig);
    }

    private boolean isSummary() {
        return mGridItemConfig == null;
    }

    FactoryReviewView getViewFactory() {
        return mFactoryView;
    }

    LaunchableConfig getGridItemConfig() {
        return mGridItemConfig;
    }

    @NonNull
    MenuActionItem<T> newOptionsMenuItem() {
        LaunchOptionsCommand command = mFactoryCommands.newLaunchOptionsCommand(mOptionsConfig);
        return new MaiReviewOptions<>(command, mStamp.getDataAuthorId());
    }
}
