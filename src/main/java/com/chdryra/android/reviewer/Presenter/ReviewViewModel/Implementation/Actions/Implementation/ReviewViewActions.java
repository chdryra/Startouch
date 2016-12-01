/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryReviewViewActions;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewActions<T extends GvData> implements OptionSelectListener{
    private final SubjectAction<T> mSubjectAction;
    private final RatingBarAction<T> mRatingBarAction;
    private final BannerButtonAction<T> mBannerButtonAction;
    private final GridItemAction<T> mGridItemAction;
    private final MenuAction<T> mMenuAction;
    private final ContextualButtonAction<T> mContextualAction;

    public ReviewViewActions(SubjectAction<T> subjectAction, RatingBarAction<T> ratingBarAction,
                             BannerButtonAction<T> bannerButtonAction, GridItemAction<T>
                                     gridItemAction, MenuAction<T> menuAction) {
        this(subjectAction, ratingBarAction, bannerButtonAction, gridItemAction, menuAction, null);
    }

    public ReviewViewActions(SubjectAction<T> subjectAction, RatingBarAction<T> ratingBarAction,
                             BannerButtonAction<T> bannerButtonAction, GridItemAction<T>
                                     gridItemAction, MenuAction<T> menuAction,
                             @Nullable ContextualButtonAction<T> contextualAction) {
        mSubjectAction = subjectAction;
        mRatingBarAction = ratingBarAction;
        mBannerButtonAction = bannerButtonAction;
        mGridItemAction = gridItemAction;
        mMenuAction = menuAction;
        mContextualAction = contextualAction;
    }

    public ReviewViewActions(FactoryReviewViewActions<T> factory) {
        this(factory.newSubject(),
                factory.newRatingBar(), factory.newBannerButton(), factory.newGridItem(),
                factory.newMenu(), factory.newContextButton());
    }

    public SubjectAction<T> getSubjectAction() {
        return mSubjectAction;
    }

    public RatingBarAction<T> getRatingBarAction() {
        return mRatingBarAction;
    }

    public BannerButtonAction<T> getBannerButtonAction() {
        return mBannerButtonAction;
    }

    public GridItemAction<T> getGridItemAction() {
        return mGridItemAction;
    }

    public MenuAction<T> getMenuAction() {
        return mMenuAction;
    }

    @Nullable
    public ContextualButtonAction<T> getContextualAction() {
        return mContextualAction;
    }

    public void attachReviewView(ReviewView<T> view) {
        mMenuAction.attachReviewView(view);
        mSubjectAction.attachReviewView(view);
        mRatingBarAction.attachReviewView(view);
        mBannerButtonAction.attachReviewView(view);
        mGridItemAction.attachReviewView(view);
        if(mContextualAction != null ) mContextualAction.attachReviewView(view);
    }

    public void detachReviewView() {
        mMenuAction.detachReviewView();
        mSubjectAction.detachReviewView();
        mRatingBarAction.detachReviewView();
        mBannerButtonAction.detachReviewView();
        mGridItemAction.detachReviewView();
        if(mContextualAction != null ) mContextualAction.detachReviewView();
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        boolean consumed = mMenuAction.onOptionSelected(requestCode, option);
        if(!consumed) consumed = mSubjectAction.onOptionSelected(requestCode, option);
        if(!consumed) consumed = mRatingBarAction.onOptionSelected(requestCode, option);
        if(!consumed) consumed = mBannerButtonAction.onOptionSelected(requestCode, option);
        if(!consumed) consumed = mGridItemAction.onOptionSelected(requestCode, option);
        if(!consumed && mContextualAction != null ) {
            consumed = mContextualAction.onOptionSelected(requestCode, option);
        }

        return consumed;
    }
}
