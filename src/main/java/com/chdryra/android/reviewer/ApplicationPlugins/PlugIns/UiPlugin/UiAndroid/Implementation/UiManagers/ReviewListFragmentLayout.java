/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 06/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewListFragmentLayout implements ReviewViewLayout {
    private static final int LAYOUT = R.layout.fragment_review_list;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating;
    private static final int BANNER = R.id.sort_button;
    private static final int GRID = R.id.gridview_data;
    private static final int COVER = R.id.background_image;
    private static final int CONTEXTUAL_VIEW = R.id.view_selector_layout;
    private static final int CONTEXTUAL_BUTTON = R.id.view_button;

    private View mView;

    private MenuUi mMenu;
    private SimpleViewUi<?, Bitmap> mCover;
    private SubjectUi<?> mSubject;
    private SimpleViewUi<?, Float> mRatingBar;
    private ViewUi<?, ?> mSortButton;
    private DataViewUi<?, ?> mDataView;
    private ViewUi<?, ?> mViewSelector;

    @Override
    public <T extends GvData> View inflateLayout(ReviewView<T> reviewView,
                                                 CellDimensionsCalculator calculator,
                                                 LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(LAYOUT, container, false);
        if(reviewView == null) return mView;

        mMenu = newMenuUi(reviewView);
        mSubject = newSubjectUi(reviewView);
        mRatingBar = newRatingUi(reviewView);
        mSortButton = newBannerButtonUi(reviewView);
        mDataView = newDataViewUi(reviewView, calculator);
        mCover = newCoverUi(reviewView);
        mViewSelector = newContextualUi(reviewView);

        return mView;
    }

    @Override
    public void inflateMenu(Menu menu, MenuInflater inflater) {
        mMenu.inflate(menu, inflater);
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        return mMenu.onItemSelected(item);
    }

    @Override
    public String getSubject() {
        return mSubject.getViewValue();
    }

    @Override
    public float getRating() {
        return mRatingBar.getViewValue();
    }

    @Override
    public void setRating(float rating) {
        mRatingBar.setViewValue(rating);
    }

    @Override
    public void setCover(@Nullable Bitmap cover) {
        mCover.setViewValue(cover);
    }

    @Override
    public void update(boolean forceSubject) {
        mSubject.update(forceSubject);
        mRatingBar.update();
        mSortButton.update();
        mDataView.update();
        mViewSelector.update();
        mCover.update();
    }

    @NonNull
    private ViewUi<?, ?> newContextualUi(ReviewView<?> reviewView) {
        return new ContextualUi(mView.findViewById(CONTEXTUAL_VIEW),
                CONTEXTUAL_BUTTON, reviewView.getActions().getContextualAction());
    }

    @NonNull
    private CoverUi newCoverUi(ReviewView<?> reviewView) {
        return new CoverRvUi(reviewView, (ImageView) mView.findViewById(COVER), mDataView);
    }

    @NonNull
    private MenuUi newMenuUi(ReviewView<?> reviewView) {
        return new MenuUi(reviewView.getActions().getMenuAction());
    }

    @NonNull
    private <T extends GvData> DataViewUi<?, ?> newDataViewUi(ReviewView<T> reviewView, CellDimensionsCalculator calculator) {
        return new RecyclerViewUi<>(reviewView, (RecyclerView) mView.findViewById(GRID),
                calculator);
    }

    @NonNull
    private BannerButtonUi newBannerButtonUi(ReviewView<?> reviewView) {
        return new BannerButtonUi((Button) mView.findViewById(BANNER),
                reviewView.getActions().getBannerButtonAction());
    }

    @NonNull
    private SimpleViewUi<?, Float> newRatingUi(ReviewView<?> reviewView) {
        return new RatingBarRvUi(reviewView, (RatingBar) mView.findViewById(RATING));
    }

    @NonNull
    private SubjectUi<?> newSubjectUi(ReviewView<?> reviewView) {
        return new SubjectEditUi(reviewView, (EditText) mView.findViewById(SUBJECT));
    }

}