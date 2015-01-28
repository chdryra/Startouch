/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 January, 2015
 */

package com.chdryra.android.reviewer;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.ViewHolderAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 23/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentViewReview extends Fragment {
    private static final int LAYOUT        = R.layout.fragment_review_grid;
    private static final int LINEAR_LAYOUT = R.id.review_grid_linearlayout;
    private static final int SUBJECT       = R.id.review_subject_edit_text;
    private static final int RATING        = R.id.review_rating_bar;
    private static final int BUTTON        = R.id.banner_button;
    private static final int GRID          = R.id.gridview_data;

    private static final String TYPE = "comchdryra.android.reviewer.fragmentreviewgrid_type";
    private static final String EDIT = "comchdryra.android.reviewer.fragmentreviewgrid_edit";

    private ViewReview mViewReview;

    private LinearLayout mLinearLayout;
    private TextView     mSubjectView;
    private RatingBar    mRatingBar;
    private Button       mBannerButton;
    private GridView     mGridView;

    private int mMaxGridCellWidth;
    private int mMaxGridCellHeight;
    private int mCellWidthDivider  = 1;
    private int mCellHeightDivider = 1;

    private boolean mIsModified = false;

    public static Fragment newInstance(GvDataList.GvType dataType, boolean isEdit) {
        Bundle args = new Bundle();
        args.putSerializable(TYPE, dataType);
        args.putBoolean(EDIT, isEdit);

        Fragment fragment = new FragmentViewReview();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        GvDataList.GvType dataType = (GvDataList.GvType) args.getSerializable(TYPE);
        boolean isEdit = args.getBoolean(EDIT);

        mViewReview = FactoryViewReview.newViewReview(this, dataType, isEdit);

        setGridCellDimension(mViewReview.getGridCellWidth(), mViewReview.getGridCellHeight());
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(LAYOUT, container, false);

        mLinearLayout = (LinearLayout) v.findViewById(LINEAR_LAYOUT);
        mSubjectView = (ClearableEditText) v.findViewById(SUBJECT);
        mRatingBar = (RatingBar) v.findViewById(RATING);
        mBannerButton = (Button) v.findViewById(BUTTON);
        mGridView = (GridView) v.findViewById(GRID);

        mGridView.setDrawSelectorOnTop(true);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mMaxGridCellWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);
        //noinspection SuspiciousNameCombination
        mMaxGridCellHeight = mMaxGridCellWidth;

        initUi();
        updateUi();
        updateCover();

        return mViewReview.modifyIfNeccesary(v, inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mViewReview.getMenuAction().inflateMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mViewReview.getMenuAction().onItemSelected(item) || super
                .onOptionsItemSelected(item);
    }

    public void updateUi() {
        updateBannerButtonUi();
        updateGridDataUi();
    }

    public String getSubject() {
        return mSubjectView.getText().toString().trim();
    }

    public void setSubject(String subject) {
        mSubjectView.setText(subject);
    }

    public float getRating() {
        return mRatingBar.getRating();
    }

    public void setRating(float rating) {
        mRatingBar.setRating(rating);
    }

    public void addView(View v) {
        if (!mIsModified) {
            mGridView.getLayoutParams().height = ActionBar.LayoutParams.WRAP_CONTENT;
            mIsModified = true;
        }

        mLinearLayout.addView(v);
    }

    public void setBannerNotClickable() {
        mBannerButton.setClickable(false);
    }

    protected void setGridCellDimension(ViewReview.CellDimension width,
            ViewReview.CellDimension height) {
        mCellWidthDivider = 1;
        mCellHeightDivider = 1;

        if (width == ViewReview.CellDimension.HALF) {
            mCellWidthDivider = 2;
        } else if (width == ViewReview.CellDimension.QUARTER) {
            mCellWidthDivider = 4;
        }

        if (height == ViewReview.CellDimension.HALF) {
            mCellHeightDivider = 2;
        } else if (height == ViewReview.CellDimension.QUARTER) {
            mCellHeightDivider = 4;
        }
    }

    void initUi() {
        initSubjectUi();
        initRatingBarUi();
        initBannerButtonUi();
        initDataGridUi();
    }

    void initSubjectUi() {
        if (isEditable()) {
            mSubjectView.setFocusable(true);
            ((ClearableEditText) mSubjectView).makeClearable(true);
            mSubjectView.addTextChangedListener(new TextWatcher() {
                ViewReviewAction.SubjectViewAction action = mViewReview.getSubjectViewAction();

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    action.beforeTextChanged(s, start, before, count);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    action.beforeTextChanged(s, start, count, after);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() > 0) {
                        action.afterTextChanged(s);
                    }
                }
            });
        } else {
            mSubjectView.setFocusable(false);
            ((ClearableEditText) mSubjectView).makeClearable(false);
        }

        mSubjectView.setText(mViewReview.getSubjectViewAction().getSubject());
    }

    void initRatingBarUi() {
        if (isEditable()) {
            mRatingBar.setIsIndicator(false);
            mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    mViewReview.getRatingBarAction().onRatingChanged(ratingBar, rating, fromUser);
                }
            });
        } else {
            mRatingBar.setIsIndicator(true);
        }

        mRatingBar.setRating(mViewReview.getRatingBarAction().getRating());
    }

    void initBannerButtonUi() {
        final ViewReviewAction.BannerButtonAction action = mViewReview.getBannerButtonAction();
        mBannerButton.setText(action.getButtonTitle());
        mBannerButton.setTextColor(mSubjectView.getTextColors().getDefaultColor());
        mBannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.onClick(v);
            }
        });
    }

    void initDataGridUi() {
        mGridView.setAdapter(getGridViewCellAdapter());
        mGridView.setColumnWidth(getGridCellWidth());
        mGridView.setNumColumns(getNumberColumns());

        final ViewReviewAction.GridItemAction action = mViewReview.getGridItemAction();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                action.onGridItemClick((GvDataList.GvData) parent.getItemAtPosition(position), v);
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                action.onGridItemLongClick((GvDataList.GvData) parent.getItemAtPosition(position)
                        , v);
                return true;
            }
        });
    }

    boolean isEditable() {
        return mViewReview.isEditable();
    }

    ViewHolderAdapter getGridViewCellAdapter() {
        return FactoryGridCellAdapter.newAdapter(getActivity(), mViewReview.getGridViewData(),
                getGridCellWidth(), getGridCellHeight());
    }

    int getGridCellWidth() {
        return mMaxGridCellWidth / mCellWidthDivider;
    }

    int getGridCellHeight() {
        return mMaxGridCellHeight / mCellHeightDivider;
    }

    int getNumberColumns() {
        return mCellWidthDivider;
    }

    void updateBannerButtonUi() {
    }

    void updateGridDataUi() {
        ((ViewHolderAdapter) mGridView.getAdapter()).notifyDataSetChanged();
        mViewReview.notifyDataSetChanged();
    }

    void updateGridData() {
        ((ViewHolderAdapter) mGridView.getAdapter()).setData(mViewReview.getGridViewData());
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void updateCover() {
        GvImageList.GvImage cover = mViewReview.getCover();
        if (cover.isValidForDisplay()) {
            BitmapDrawable bitmap = new BitmapDrawable(getResources(), cover.getBitmap());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mLinearLayout.setBackground(bitmap);
            } else {
                mLinearLayout.setBackgroundDrawable(bitmap);
            }
            mGridView.getBackground().setAlpha(mViewReview.getGridViewAlpha().getAlpha());

        } else {
            mLinearLayout.setBackgroundColor(Color.TRANSPARENT);
            mGridView.getBackground().setAlpha(ViewReview.GridViewImageAlpha.OPAQUE.getAlpha());
        }
    }
}

