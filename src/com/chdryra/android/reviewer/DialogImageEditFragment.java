/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;
import com.google.android.gms.maps.model.LatLng;

public class DialogImageEditFragment extends DialogCancelDeleteDoneFragment {
    public static final String OLD_CAPTION = "com.chdryra.android.reviewer.old_caption";

    private Bitmap            mBitmap;
    private LatLng            mLatLng;
    private String            mCaption;
    private ClearableEditText mImageCaption;

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_image_view, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.dialog_image_image_view);
        imageView.setImageBitmap(mBitmap);

        mImageCaption = (ClearableEditText) v.findViewById(R.id.dialog_image_caption_edit_text);
        mImageCaption.setText(mCaption);
        mImageCaption.setHint(getResources().getString(R.string.edit_text_image_caption_hint));

        //For some reason setSelection(0) doesn't work unless I force set the span of the selection
        if (mCaption != null && mCaption.length() > 0) {
            mImageCaption.setSelection(0, mCaption.length());
            mImageCaption.setSelection(0);
        }

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBitmap = getArguments().getParcelable(FragmentReviewImages.BITMAP);
        mLatLng = getArguments().getParcelable(FragmentReviewImages.LATLNG);
        mCaption = getArguments().getString(FragmentReviewImages.CAPTION);
        setDialogTitle(null);
        hideKeyboardOnLaunch();
        setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_image_title));
    }

    @Override
    protected void onDeleteButtonClick() {
        getNewReturnDataIntent().putExtras(getArguments());
    }

    @Override
    protected boolean hasDataToDelete() {
        return true;
    }

    @Override
    protected void onDoneButtonClick() {
        Intent i = getNewReturnDataIntent();
        i.putExtra(FragmentReviewImages.BITMAP, mBitmap);
        i.putExtra(FragmentReviewImages.LATLNG, mLatLng);
        i.putExtra(FragmentReviewImages.CAPTION, mImageCaption.getText().toString());
        i.putExtra(OLD_CAPTION, mCaption);
    }
}
