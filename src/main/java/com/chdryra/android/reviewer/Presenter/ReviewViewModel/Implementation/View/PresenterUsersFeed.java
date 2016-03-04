/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverview;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewUploader;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewUploaderListener;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterUsersFeed implements
        DialogAlertFragment.DialogAlertListener,
        NewReviewListener,
        ReviewsRepositoryObserver,
        ReviewUploaderListener{

    private ApplicationInstance mApp;
    private ReviewNodeComponent mFeedNode;
    private FactoryReviews mReviewsFactory;
    private ReviewView<GvReviewOverview> mReviewView;
    private GridItemFeedScreen mGridItem;
    private ReviewUploader mUploader;
    private ReviewUploadedListener mUploadedListener;

    public interface ReviewUploadedListener {
        void onReviewUploaded(String message);
    }

    private PresenterUsersFeed(ApplicationInstance app,
                              ReviewUploadedListener uploadedListener,
                              Actions actions) {
        mApp = app;

        mReviewsFactory = app.getReviewsFactory();
        ReviewsFeed feed = app.getUsersFeed();
        String title = feed.getAuthor().getName() + "'s feed";
        mFeedNode = mReviewsFactory.createMetaReviewMutable(feed.getReviews(), title);
        feed.registerObserver(this);
        mGridItem = (GridItemFeedScreen) actions.getGridItemAction();
        mReviewView = app.getLaunchableFactory().newReviewsListScreen(mFeedNode,
                app.getReviewViewAdapterFactory(), actions);
        mUploader = app.newReviewUploader();
        mUploader.registerListener(this);
        mUploadedListener = uploadedListener;
    }

    public ReviewView<GvReviewOverview> getView() {
        return mReviewView;
    }

    public void deleteFromUsersFeed(ReviewId id) {
        mApp.deleteFromUsersFeed(id);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mGridItem.onAlertPositive(requestCode, args);
    }

    @Override
    public void onReviewAdded(Review review) {
        addReviewToNode(review);
        if (mReviewView != null) mReviewView.onGridDataChanged();
    }

    @Override
    public void onReviewRemoved(ReviewId reviewId) {
        removeReviewFromNode(reviewId);
        if (mReviewView != null) mReviewView.onGridDataChanged();
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mGridItem.onNewReviewUsingTemplate(template);
    }

    public void upload(String reviewId, ArrayList<String> platformNames) {
        mUploader.upload(reviewId, platformNames);
    }

    @Override
    public void onUploadStatus(double percentage, PublishResults justUploaded) {

    }

    @Override
    public void onUploadCompleted(Collection<PublishResults> publishedOk,
                                  Collection<PublishResults> publishedNotOk) {
        int numFollowers = 0;
        ArrayList<String> platformsOk = new ArrayList<>();
        for (PublishResults result : publishedOk) {
            platformsOk.add(result.getPublisherName());
            numFollowers += result.getFollowers();
        }

        ArrayList<String> platformsNotOk = new ArrayList<>();
        for (PublishResults result : publishedNotOk) {
            platformsNotOk.add(result.getPublisherName());
        }

        String message = getReviewUploadedMessage(platformsOk, platformsNotOk, numFollowers);
        mUploadedListener.onReviewUploaded(message);
    }

    private String getReviewUploadedMessage(ArrayList<String> platformsOk,
                                            ArrayList<String> platformsNotOk,
                                            int numFollowers) {
        String message = "";

        if (platformsOk.size() > 0) {
            String num = String.valueOf(numFollowers);
            boolean fb = platformsOk.contains(PlatformFacebook.NAME);
            String plus = fb ? "+ " : "";
            String followers = numFollowers == 1 && !fb ? " follower" : " followers";
            String followersString = num + plus + followers;

            message = "Published to " + followersString + " on " +
                    StringUtils.join(platformsOk.toArray(), ", ");
        }

        String notOkMessage = "";
        if (platformsNotOk.size() > 0) {
            notOkMessage = "Problems publishing to " + StringUtils.join(platformsNotOk.toArray(),
                    ",");
            if (platformsOk.size() > 0) message += "\n" + notOkMessage;
        }

        return message;
    }

    private void addReviewToNode(Review review) {
        mFeedNode.addChild(mReviewsFactory.createReviewNodeComponent(review, false));
    }

    private void removeReviewFromNode(ReviewId reviewId) {
        mFeedNode.removeChild(reviewId);
    }

    public static class Actions extends ReviewViewActions<GvReviewOverview> {
        public Actions(SubjectAction<GvReviewOverview> subjectAction, RatingBarAction
                <GvReviewOverview> ratingBarAction, BannerButtonAction<GvReviewOverview>
                               bannerButtonAction, GridItemFeedScreen gridItemAction,
                       MenuAction<GvReviewOverview> menuAction) {
            super(subjectAction, ratingBarAction, bannerButtonAction, gridItemAction, menuAction);
        }
    }

    public static class Builder {
        private PresenterUsersFeed.ReviewUploadedListener mListener;
        private ApplicationInstance mApp;

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        public Builder setReviewUploadedListener(PresenterUsersFeed.ReviewUploadedListener listener) {
            mListener = listener;
            return this;
        }

        public PresenterUsersFeed build() {
            return new PresenterUsersFeed(mApp, mListener, getActions());
        }

        @NonNull
        private PresenterUsersFeed.Actions getActions() {
            ConfigUi configDataUi = mApp.getConfigDataUi();
            FactoryReviewViewLaunchable launchableFactory = mApp.getLaunchableFactory();
            LaunchableUiLauncher uiLauncher = mApp.getUiLauncher();
            LaunchableUi reviewBuildUi = configDataUi.getBuildReviewConfig().getLaunchable();

            GridItemFeedScreen gi = new GridItemFeedScreen(launchableFactory, uiLauncher,
                    configDataUi.getShareEditConfig().getLaunchable(), reviewBuildUi);
            SubjectAction<GvReviewOverview> sa = new SubjectActionNone<>();
            RatingBarAction<GvReviewOverview> rb
                    = new RatingBarExpandGrid<>(launchableFactory, uiLauncher);
            BannerButtonAction<GvReviewOverview> bba = new BannerButtonActionNone<>();
            MenuFeedScreen ma = new MenuFeedScreen(uiLauncher, reviewBuildUi);

            return new PresenterUsersFeed.Actions(sa, rb, bba, gi, ma);
        }
    }
}
