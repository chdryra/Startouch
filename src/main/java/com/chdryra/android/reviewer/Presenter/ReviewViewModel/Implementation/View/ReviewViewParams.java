/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewParams {
    private GridViewParams mGridViewParams = new GridViewParams();
    private boolean mSubjectVisible = true;
    private boolean mRatingVisible = true;
    private boolean mBannerButtonVisible = true;
    private boolean mContextualVisible = false;
    private boolean mCoverManager = true;

    public enum GridViewAlpha {
        TRANSPARENT(0),
        MEDIUM(200),
        OPAQUE(255);

        private final int mAlpha;

        GridViewAlpha(int alpha) {
            this.mAlpha = alpha;
        }

        public int getAlpha() {
            return mAlpha;
        }
    }

    public enum CellDimension {
        FULL(1),
        HALF(2),
        QUARTER(4);

        private int mDivider;
        CellDimension(int divider) {
            mDivider = divider;
        }

        public int getDivider() {
            return mDivider;
        }
    }

    public GridViewParams getGridViewParams() {
        return mGridViewParams;
    }

    public boolean isSubjectVisible() {
        return mSubjectVisible;
    }

    public ReviewViewParams setSubjectVisible(boolean subjectVisible) {
        mSubjectVisible = subjectVisible;
        return this;
    }

    public boolean isRatingVisible() {
        return mRatingVisible;
    }

    public ReviewViewParams setRatingVisible(boolean ratingVisible) {
        mRatingVisible = ratingVisible;
        return this;
    }

    public boolean isBannerButtonVisible() {
        return mBannerButtonVisible;
    }

    public ReviewViewParams setBannerButtonVisible(boolean bannerButtonVisible) {
        mBannerButtonVisible = bannerButtonVisible;
        return this;
    }

    public boolean isContextualVisible() {
        return mContextualVisible;
    }

    public ReviewViewParams setContextualVisible(boolean contextualVisible) {
        mContextualVisible = contextualVisible;
        return this;
    }

    public boolean manageCover() {
        return mCoverManager;
    }

    public ReviewViewParams setCellWidth(CellDimension cellWidth) {
        getGridViewParams().setCellWidth(cellWidth);
        return this;
    }

    public ReviewViewParams setCellHeight(CellDimension cellHeight) {
        getGridViewParams().setCellHeight(cellHeight);
        return this;
    }

    public ReviewViewParams setGridAlpha(GridViewAlpha alpha) {
        getGridViewParams().setGridAlpha(alpha);
        return this;
    }

    public ReviewViewParams setCoverManager(boolean coverManager) {
        mCoverManager = coverManager;
        return this;
    }

    public static class GridViewParams {
        private GridViewAlpha mGridAlpha = GridViewAlpha.MEDIUM;
        private CellDimension mCellWidth = CellDimension.HALF;
        private CellDimension mCellHeight = CellDimension.QUARTER;

        public int getGridAlpha() {
            return mGridAlpha.getAlpha();
        }

        public GridViewParams setGridAlpha(GridViewAlpha gridAlpha) {
            mGridAlpha = gridAlpha;
            return this;
        }

        public CellDimension getCellWidth() {
            return mCellWidth;
        }

        public GridViewParams setCellWidth(CellDimension cellWidth) {
            mCellWidth = cellWidth;
            return this;
        }

        public CellDimension getCellHeight() {
            return mCellHeight;
        }

        public GridViewParams setCellHeight(CellDimension cellHeight) {
            mCellHeight = cellHeight;
            return this;
        }
    }
}
