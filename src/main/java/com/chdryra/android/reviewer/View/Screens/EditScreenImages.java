/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Utils.ImageChooser;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenImages {
    private static final GvDataType<GvImageList.GvImage> TYPE =
            GvImageList.GvImage.TYPE;
    private static final ConfigGvDataUi.Config CONFIG =
            ConfigGvDataUi.getConfig(TYPE);

    //Classes
    public static class BannerButton extends EditScreen.BannerButton {
        private ImageChooser mImageChooser;

        //Constructors
        public BannerButton(String title) {
            super(CONFIG.getAdderConfig(), title);
            setListener(new AddImageListener() {
            });
        }

        private void setCover() {
            GvImageList images = (GvImageList) getGridData();
            GvImageList.GvImage cover = images.getItem(0);
            cover.setIsCover(true);
            getReviewView().update();
        }

        //Overridden
        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            mImageChooser = Administrator.getImageChooser(getActivity());
        }

        @Override
        public void onClick(View v) {
            getListener().startActivityForResult(mImageChooser.getChooserIntents(),
                    getRequestCode());
        }

        private abstract class AddImageListener extends Fragment implements ImageChooser
                .ImageChooserListener {

            //Overridden
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                boolean correctCode = requestCode == getRequestCode();
                boolean isOk = ActivityResultCode.OK.equals(resultCode);
                boolean imageExists = mImageChooser.chosenImageExists(ActivityResultCode.get
                        (resultCode), data);

                if (correctCode && isOk && imageExists) mImageChooser.getChosenImage(this);
            }

            @Override
            public void onImageChosen(GvImageList.GvImage image) {
                if (getGridData().size() == 0) image.setIsCover(true);
                if (addData(image) && getGridData().size() == 1) setCover();
            }
        }
    }

    public static class GridItem extends EditScreen.GridItem {
        private static final int IMAGE_AS_COVER = 200;

        //Constructors
        public GridItem() {
            super(CONFIG.getEditorConfig());
        }

        //Overridden
        @Override
        public void onGridItemLongClick(GvData item, int position, View v) {
            GvImageList.GvImage image = (GvImageList.GvImage) item;
            if (image.isCover()) {
                super.onGridItemLongClick(item, position, v);
            } else {
                showAlertDialog(getActivity().getString(R.string.alert_set_image_as_background),
                        IMAGE_AS_COVER, image);
            }
        }

        @Override
        protected void deleteData(GvData datum) {
            super.deleteData(datum);
            if (((GvImageList.GvImage) datum).isCover()) getReviewView().updateCover();
        }

        @Override
        protected void onDialogAlertPositive(int requestCode, Bundle args) {
            if (requestCode == IMAGE_AS_COVER) {
                GvImageList.GvImage cover = (GvImageList.GvImage) GvDataPacker.unpackItem
                        (GvDataPacker.CurrentNewDatum.CURRENT, args);
                getEditor().proposeCover(cover);
                getReviewView().update();
            }
        }
    }
}
