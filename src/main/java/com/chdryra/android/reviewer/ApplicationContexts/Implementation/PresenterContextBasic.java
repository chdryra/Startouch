/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.Authentication.Interfaces.UsersManager;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefAuthorList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryBuildScreenLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher
        .ReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.FactoryUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PresenterContextBasic implements PresenterContext {
    private final ModelContext mModelContext;
    private final ViewContext mViewContext;
    private final SocialContext mSocialContext;
    private final NetworkContext mNetworkContext;
    private final PersistenceContext mPersistenceContext;
    private final ReviewPublisher mPublisher;

    private FactoryGvData mFactoryGvData;
    private FactoryReviewBuilderAdapter<?> mFactoryBuilderAdapter;
    private FactoryReviewView mFactoryReviewView;
    private FactoryReviewLauncher mFactoryReviewLauncher;
    private ReviewBuilderAdapter<?> mReviewBuilderAdapter;
    private FactoryBuildScreenLauncher mFactoryBuildScreenLauncher;

    protected PresenterContextBasic(ModelContext modelContext,
                                    ViewContext viewContext,
                                    SocialContext socialContext,
                                    NetworkContext networkContext,
                                    PersistenceContext persistenceContext) {
        mModelContext = modelContext;
        mViewContext = viewContext;
        mSocialContext = socialContext;
        mPersistenceContext = persistenceContext;
        mNetworkContext = networkContext;

        mPublisher = mNetworkContext.getPublisherFactory().newPublisher(mPersistenceContext
                .getLocalRepository());
    }

    protected void setFactoryReviewView(FactoryReviewView
                                                factoryReviewView) {
        mFactoryReviewView = factoryReviewView;
    }

    protected void setFactoryGvData(FactoryGvData factoryGvData) {
        mFactoryGvData = factoryGvData;
    }


    protected void setFactoryBuilderAdapter(FactoryReviewBuilderAdapter<?> factoryBuilderAdapter) {
        mFactoryBuilderAdapter = factoryBuilderAdapter;
    }

    protected void setFactoryReviewLauncher(FactoryReviewLauncher factoryReviewLauncher) {
        mFactoryReviewLauncher = factoryReviewLauncher;
    }

    protected void setFactoryBuildScreenLauncher(FactoryBuildScreenLauncher
                                                      factoryBuildScreenLauncher) {
        mFactoryBuildScreenLauncher = factoryBuildScreenLauncher;
    }

    @Override
    public FactoryGvData getGvDataFactory() {
        return mFactoryGvData;
    }

    @Override
    public FactoryReviewViewParams getReviewViewParamsFactory() {
        return mFactoryReviewView.getParamsFactory();
    }

    @Override
    public FactoryReviews getReviewsFactory() {
        return mModelContext.getReviewsFactory();
    }

    @Override
    public SocialPlatformList getSocialPlatformList() {
        return mSocialContext.getSocialPlatforms();
    }

    @Override
    public ConfigUi getConfigUi() {
        return mViewContext.getUiConfig();
    }

    @Override
    public FactoryUiLauncher getLauncherFactory() {
        return mViewContext.getLauncherFactory();
    }

    @Override
    public ReviewBuilderAdapter<?> newReviewBuilderAdapter(@Nullable Review template) {
        mReviewBuilderAdapter = mFactoryBuilderAdapter.newAdapter(template);
        return mReviewBuilderAdapter;
    }

    @Override
    public void discardReviewBuilderAdapter() {
        mReviewBuilderAdapter = null;
    }

    @Override
    public ReviewBuilderAdapter<?> getReviewBuilderAdapter() {
        return mReviewBuilderAdapter;
    }

    @Override
    public Review executeReviewBuilder() {
        Review published = mReviewBuilderAdapter.buildReview();
        discardReviewBuilderAdapter();

        return published;
    }

    @Override
    public void getReview(ReviewId id, final RepositoryCallback callback) {
        getMasterRepository().getReference(id, new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                ReviewReference reference = result.getReference();
                if(result.isReference() && reference != null) {
                    reference.dereference(new DataReference.DereferenceCallback<Review>() {
                        @Override
                        public void onDereferenced(DataValue<Review> review) {
                            callback.onRepositoryCallback(new RepositoryResult(review.getData(), review.getMessage()));
                        }
                    });
                }
            }
        });
    }

    @Override
    public TagsManager getTagsManager() {
        return mModelContext.getTagsManager();
    }

    @Override
    public ReviewPublisher getReviewPublisher() {
        return mPublisher;
    }

    @Override
    public ReviewsSource getMasterRepository() {
        return mPersistenceContext.getReviewsSource();
    }

    @Override
    public ReferencesRepository newFeed(AuthorId usersId, RefAuthorList following) {
        return mPersistenceContext.getRepoFactory().newFeed(usersId, following, getMasterRepository());
    }

    @Override
    public UsersManager getUsersManager() {
        return mPersistenceContext.getUsersManager();
    }

    @Override
    public ReviewDeleter newReviewDeleter(ReviewId id) {
        return mNetworkContext.getDeleterFactory().newDeleter(id, getTagsManager());
    }

    @Override
    public ReviewsListView newFeedView(ReviewNode reviewNode) {
        return mFactoryReviewView.newFeedView(reviewNode);
    }

    @Override
    public ReviewLauncher newReviewLauncher(AuthorId sessionAuthor, UiLauncher launcher) {
        return mFactoryReviewLauncher.newReviewLauncher(getMasterRepository(), launcher, sessionAuthor);
    }

    @Override
    public BuildScreenLauncher newBuildScreenLauncher(ApplicationInstance app) {
        return mFactoryBuildScreenLauncher.newLauncher(app);
    }
}
