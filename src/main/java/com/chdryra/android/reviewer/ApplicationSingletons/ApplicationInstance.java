/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.app.Activity;
import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories
        .FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories
        .FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories
        .FactoryReviewViewParams;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;

/**
 * Singleton that controls app-wide duties. Holds 3 main objects:
 * <ul>
 * <li>ApplicationContext: for app-wide dependency injection</li>
 * <li>ReviewerDb: on-phone cache</li>
 * <li>ReviewBuilderAdapter: controls editing of new reviews</li>
 * </ul>
 * <p/>
 * Also manages:
 * <ul>
 * <li>The creation of new reviews</li>
 * <li>Publishing of reviews</li>
 * <li>List of social platforms</li>
 * <li>Launching of reviews form repository</li>
 * </ul>
 *
 */
public class ApplicationInstance extends ApplicationSingleton {
    private static final String NAME = "ApplicationInstance";

    private static ApplicationInstance sSingleton;

    private final ApplicationContext mApplicationContext;
    private ReviewBuilderAdapter<?> mReviewBuilderAdapter;

    private ApplicationInstance(Context context) {
        super(context, NAME);
        throw new IllegalStateException("Need to call createWithApplicationContext first!");
    }

    private ApplicationInstance(Context context, ApplicationContext applicationContext) {
        super(context, NAME);
        mApplicationContext = applicationContext;
    }

    //Static methods
    public static void createWithApplicationContext(Context context, ApplicationContext
                                                                     applicationContext) {
        sSingleton = new ApplicationInstance(context, applicationContext);
    }

    public static ApplicationInstance getInstance(Context c) {
        sSingleton = getSingleton(sSingleton, ApplicationInstance.class, c);
        return sSingleton;
    }

    //public methods
    public DataValidator getDataValidator() {
        return mApplicationContext.getDataValidator();
    }

    public ReviewBuilderAdapter<?> getReviewBuilderAdapter() {
        return mReviewBuilderAdapter;
    }

    public <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType) {
        return mReviewBuilderAdapter.getDataBuilderAdapter(dataType);
    }

    public ReviewsFeedMutable getAuthorsFeed() {
        return mApplicationContext.getAuthorsFeed();
    }

    public FactoryReviews getReviewsFactory() {
        return mApplicationContext.getReviewsFactory();
    }

    public FactoryReviewViewLaunchable getLaunchableFactory() {
        return mApplicationContext.getReviewViewLaunchableFactory();
    }

    public FactoryReviewViewAdapter getReviewViewAdapterFactory() {
        return mApplicationContext.getReviewViewAdapterFactory();
    }

    public SocialPlatformList getSocialPlatformList() {
        return mApplicationContext.getSocialPlatformList();
    }

    public ReviewBuilderAdapter<?> newReviewBuilderAdapter() {
        FactoryReviewBuilderAdapter adapterfactory =
                mApplicationContext.getReviewBuilderAdapterFactory();
        mReviewBuilderAdapter = adapterfactory.newAdapter();
        return mReviewBuilderAdapter;
    }

    public void publishReviewBuilder() {
        Review published = mReviewBuilderAdapter.publishReview();
        getAuthorsFeed().addReview(published);
        mReviewBuilderAdapter = null;
    }

    public void launchReview(Activity activity, String reviewId) {
        Review review = getAuthorsFeed().getReview(reviewId);
        ReviewNode reviewNode = getReviewsFactory().createMetaReview(review).getTreeRepresentation();
        FactoryReviewViewAdapter adapterFactory = mApplicationContext.getReviewViewAdapterFactory();
        LaunchableUi ui = getLaunchableFactory().newReviewsListScreen(reviewNode, adapterFactory);
        String tag = review.getSubject().getSubject();
        int requestCode = RequestCodeGenerator.getCode(tag);
        getUiLauncher().launch(ui, activity, requestCode);
    }

    public ConfigDataUi getConfigDataUi() {
        return mApplicationContext.getUiConfig();
    }

    public LaunchableUiLauncher getUiLauncher() {
        return mApplicationContext.getUiLauncher();
    }

    public FactoryReviewViewParams getParamsFactory() {
        return getLaunchableFactory().getParamsFactory();
    }

    public FactoryGvData getGvDataFactory() {
        return mApplicationContext.getGvDataFactory();
    }
}
