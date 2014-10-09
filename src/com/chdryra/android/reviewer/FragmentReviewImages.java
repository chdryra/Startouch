/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogTwoButtonFragment.ActionType;
import com.chdryra.android.mygenerallibrary.FunctionPointer;
import com.chdryra.android.reviewer.GVImageList.GVImage;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.google.android.gms.maps.model.LatLng;

/**
 * UI Fragment: images. Each grid cell shows an image.
 * <p/>
 * <p>
 * FragmentReviewGrid functionality:
 * <ul>
 * <li>Subject: disabled</li>
 * <li>RatingBar: disabled</li>
 * <li>Banner button: calls <code>mHelperReviewImage.getImageChooserIntents(.)</code></li>
 * <li>Grid cell click: launches DialogImageEditFragment</li>
 * <li>Grid cell long click: launches DialogSetImageAsBackgroundFragment</li>
 * </ul>
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityReviewImages
 * @see com.chdryra.android.reviewer.HelperReviewImage
 * @see com.chdryra.android.reviewer.DialogImageEditFragment
 * @see com.chdryra.android.reviewer.DialogSetImageAsBackgroundFragment
 */
public class FragmentReviewImages extends FragmentReviewGridAddEditDone<GVImage> {
    public static final  String BITMAP   = "com.chdryra.android.reviewer.bitmap";
    public static final  String CAPTION  = "com.chdryra.android.reviewer.caption";
    public static final  String LATLNG   = "com.chdryra.android.reviewer.latlng";
    private static final String POSITION = "com.chdryra.android.reviewer.image_position";

    private static final String IMAGE_BACKGROUND_TAG = "DataEditTag";
    private static final int    IMAGE_AS_BACKGROUND  = 20;

    private GVImageList       mImages;
    private HelperReviewImage mHelperReviewImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImages = (GVImageList) setAndInitData(GVType.IMAGES);
        mHelperReviewImage = HelperReviewImage.getInstance(getController());
        setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_images_title));
        setGridCellDimension(CellDimension.HALF, CellDimension.HALF);
        setBannerButtonText(getResources().getString(R.string.button_add_image));
        setAddEditDialogs(null, DialogImageEditFragment.class);
    }

    @Override
    protected void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        if (mImages.getItem(position).isCover()) {
            return;
        }

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        DialogShower.show(new DialogSetImageAsBackgroundFragment(), FragmentReviewImages.this,
                IMAGE_AS_BACKGROUND, IMAGE_BACKGROUND_TAG, args);
    }

    @Override
    void updateCover() {
        updateCover(mImages);
    }

    @Override
    protected void addData(int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                mHelperReviewImage.addReviewImage(getActivity(), mImages,
                        new FunctionPointer<Void>() {
                            @Override
                            public void execute(Void data) {
                                if (mImages.size() == 1) {
                                    setCover(0);
                                }
                                updateUI();
                            }
                        });
                break;
            default:
        }
    }

    @Override
    protected void editData(int resultCode, Intent data) {
        switch (ActivityResultCode.get(resultCode)) {
            case DONE:
                String newCaption = (String) data.getSerializableExtra(CAPTION);
                if (newCaption != null) {
                    mImages.updateCaption((Bitmap) data.getParcelableExtra(BITMAP),
                            (LatLng) data.getParcelableExtra(LATLNG),
                            (String) data.getSerializableExtra(DialogImageEditFragment
                                    .OLD_CAPTION), newCaption);
                }
                break;
            case DELETE:
                GVImage image = mImages.getItem(data.getIntExtra(POSITION, 0));
                boolean isCover = image.isCover();
                mImages.remove(image);
                if (isCover) {
                    setCover(0);
                }
                break;
            default:
        }
    }

    @Override
    protected Bundle packGridCellData(GVImage image, Bundle args) {
        args.putParcelable(BITMAP, image.getBitmap());
        args.putParcelable(LATLNG, image.getLatLng());
        args.putString(CAPTION, image.getCaption());

        return args;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getRequestCode(DataAddEdit.ADD)) {
            if (mHelperReviewImage.processOnActivityResult(getActivity(), resultCode, data)) {
                addData(resultCode, data);
            }

        } else if (requestCode == IMAGE_AS_BACKGROUND) {
            if (ActionType.YES.getResultCode().equals(resultCode)) {
                setCover(data.getIntExtra(POSITION, 0));
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onBannerButtonClick() {
        startActivityForResult(mHelperReviewImage.getImageChooserIntents(getActivity()),
                getRequestCode(DataAddEdit.ADD));
    }

    private void setCover(int position) {
        if (mImages.size() > 0 && position < mImages.size()) {
            for (GVImage image : mImages) {
                image.setIsCover(false);
            }

            mImages.getItem(position).setIsCover(true);
        }

        updateCover();
    }
}
