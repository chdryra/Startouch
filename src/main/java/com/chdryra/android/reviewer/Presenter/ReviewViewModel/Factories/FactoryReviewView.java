/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher
        .ReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewPerspective;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewView {
    private final ConfigUi mConfig;
    private final FactoryReviewViewParams mParamsFactory;
    private final BuildScreenLauncher mLauncher;
    private FactoryReviewViewAdapter mAdapterFactory;

    public FactoryReviewView(ConfigUi config,
                             FactoryReviewViewParams paramsFactory,
                             BuildScreenLauncher launcher) {
        mConfig = config;
        mParamsFactory = paramsFactory;
        mLauncher = launcher;
    }

    public FactoryReviewViewParams getParamsFactory() {
        return mParamsFactory;
    }

    public void setAdapterFactory(FactoryReviewViewAdapter adapterFactory) {
        mAdapterFactory = adapterFactory;
        mAdapterFactory.setReviewViewFactory(this);
    }

    public ReviewViewAdapter<GvNode> newReviewsListAdapter(ReviewNode node) {
        return newReviewsListView(node).getAdapter();
    }

    public ReviewsListView newFeedView(ReviewNode node) {
        return newReviewsListView(node,
                mAdapterFactory.newFeedAdapter(node),
                new FactoryActions.Feed(this, mConfig.getShareEdit().getLaunchable(), mLauncher));
    }

    public ReviewsListView newReviewsListView(ReviewNode node) {
        return newReviewsListView(node,
                mAdapterFactory.newChildListAdapter(node),
                new FactoryActions.ReviewsList(this, mConfig.getShareEdit().getLaunchable(),
                        mLauncher));
    }

    public <T extends GvData> ReviewView<T> newDefaultView(ReviewViewAdapter<T> adapter,
                                                           ReviewLauncher launcher,
                                                           AuthorsRepository repo) {
        FactoryReviewViewActions<T> factory
                = newActionsFactory(adapter, launcher, repo, getDataType(adapter));
        return newReviewView(adapter, factory);
    }

    public <T extends GvData> ReviewView<T> newSearchView(ReviewViewAdapter.Filterable<T> adapter) {
        FactoryActions.Search<T> factory = newSearchActionsFactory(getDataType(adapter));
        return newReviewView(adapter, factory);
    }

    @NonNull
    private <T extends GvData> ReviewView<T> newReviewView(ReviewViewAdapter<T> adapter,
                                                           FactoryReviewViewActions<T> factory) {
        ReviewViewParams params = mParamsFactory.getParams(getDataType(adapter));
        ReviewViewActions<T> actions = newActions(factory);

        return new ReviewViewDefault<>(new ReviewViewPerspective<>(adapter, actions, params));
    }

    @NonNull
    private <T extends GvData> ReviewViewActions<T> newActions(FactoryReviewViewActions<T>
                                                                       factory) {
        return new ReviewViewActions<>(factory.newSubject(),
                factory.newRatingBar(), factory.newBannerButton(), factory.newGridItem(),
                factory.newMenu(), factory.newContextButton());
    }

    @NonNull
    private <T extends GvData> FactoryActions.Search<T> newSearchActionsFactory(GvDataType<T>
                                                                                        dataType) {
        FactoryActions.Search<T> factory;
        if (dataType.equals(GvAuthor.TYPE)) {
            factory = (FactoryActions.Search<T>) new FactoryActions.SearchAuthor(this);
        } else {
            factory = new FactoryActions.Search<>(dataType, this);
        }
        return factory;
    }

    @NonNull
    private <T extends GvData> FactoryReviewViewActions<T>
    newActionsFactory(ReviewViewAdapter<T> adapter, ReviewLauncher launcher,
                      AuthorsRepository repo, GvDataType<T> dataType) {
        FactoryReviewViewActions factory;
        if (dataType.equals(GvSize.Reference.TYPE)) {
            factory = new FactoryActions.Summary(this, launcher,
                    adapter.getStamp(), repo, mLauncher);
        } else if(dataType.equals(GvComment.Reference.TYPE)) {
            factory = new FactoryActions.Comments(this, launcher, adapter.getStamp(),
                    repo, mConfig.getViewer(dataType.getDatumName()));
        } else {
            factory = new FactoryActions.ViewData<>(dataType, this, launcher, adapter.getStamp(),
                    repo, mConfig.getViewer(dataType.getDatumName()));
        }
        //TODO make type safe
        return (FactoryReviewViewActions<T>) factory;
    }

    private <T extends GvData> GvDataType<T> getDataType(ReviewViewAdapter<T> adapter) {
        //TODO make type safe
        return (GvDataType<T>) adapter.getGvDataType();
    }

    //private
    @NonNull
    private ReviewsListView newReviewsListView(ReviewNode node,
                                               ReviewViewAdapter<GvNode> adapter,
                                               FactoryActions.ReviewsList actionsFactory) {
        GridItemReviewsList gi = actionsFactory.newGridItem();
        SubjectAction<GvNode> sa = actionsFactory.newSubject();
        RatingBarAction<GvNode> rb = actionsFactory.newRatingBar();
        BannerButtonAction<GvNode> bba = actionsFactory.newBannerButton();
        MenuAction<GvNode> ma = actionsFactory.newMenu();

        ReviewViewActions<GvNode> actions = new ReviewsListView.Actions(sa, rb, bba, gi, ma);

        ReviewViewParams params = new ReviewViewParams();
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);

        return new ReviewsListView(node, new ReviewViewPerspective<>(adapter, actions, params));
    }
}
