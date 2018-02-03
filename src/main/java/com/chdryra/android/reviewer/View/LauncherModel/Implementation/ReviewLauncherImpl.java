/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.NodeRepo;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLauncherImpl implements ReviewLauncher {
    private static final String TYPE = TagKeyGenerator.getKey(ReviewLauncherImpl.class, "DataType");
    private final NodeRepo mNodeRepo;
    private final FactoryReviewView mViewFactory;
    private final Map<String, NodeLauncher<?>> mLaunchers;

    private UiLauncher mUiLauncher;
    private AuthorId mSessionAuthor;

    public ReviewLauncherImpl(NodeRepo nodeRepo,
                              List<NodeLauncher<?>> nodeLaunchers,
                              FactoryReviewView viewFactory) {
        mNodeRepo = nodeRepo;
        mViewFactory = viewFactory;
        mLaunchers = new HashMap<>();
        for(NodeLauncher<?> launcher : nodeLaunchers) {
            mLaunchers.put(launcher.getDataType().getDatumName(), launcher);
        }
    }

    @Nullable
    public ReviewNode unpack(Bundle args) {
        if(args == null) return null;
        NodeLauncher<?> launcher = mLaunchers.get(args.getString(TYPE));
        return launcher != null ? launcher.unpack(args) : null;
    }

    @Override
    public void setUiLauncher(UiLauncher uiLauncher) {
        mUiLauncher = uiLauncher;
        for(NodeLauncher<?> launcher : mLaunchers.values()) {
            launcher.setUiLauncher(uiLauncher);
        }
    }

    @Override
    public void launchAsList(ReviewId reviewId) {
        launchAsList(mNodeRepo.asMetaReview(reviewId));
    }

    @Override
    public void launchAsList(ReviewNode node) {
        launchView(newListView(node), getRequestCode(node));
    }

    @Override
    public void launchSummary(final ReviewId reviewId) {
        ReviewNode node = mNodeRepo.asReviewNode(reviewId);
        launchView(mViewFactory.newSummaryView(node), getRequestCode(node));
    }

    @Override
    public void launchReviewsList(AuthorId authorId) {
        ReviewNode node = mNodeRepo.getMetaReview(authorId);
        launchView(newListView(node), getRequestCode(node));
    }

    @Override
    public void launchNodeView(ReviewNode node, GvDataType<?> dataType, int datumIndex, boolean isPublished) {
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, dataType.getDatumName());
        NodeLauncher<?> launcher = mLaunchers.get(dataType.getDatumName());
        if(launcher != null) launcher.launch(node, datumIndex, isPublished, bundle);
    }

    void setSessionAuthor(AuthorId authorId) {
        mSessionAuthor = authorId;
    }

    private int getRequestCode(ReviewNode node) {
        return RequestCodeGenerator.getCode(node.getSubject().getSubject());
    }

    private void launchView(ReviewView<?> ui, int requestCode) {
        mUiLauncher.launch(ui, new UiLauncherArgs(requestCode));
    }

    private ReviewView<?> newListView(ReviewNode reviewNode) {
        DataAuthorId authorId = reviewNode.getAuthorId();
        boolean menu = !authorId.toString().equals(mSessionAuthor.toString());
        return mViewFactory.newListView(reviewNode, menu ? authorId : null);
    }
}
