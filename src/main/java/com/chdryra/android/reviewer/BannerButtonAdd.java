/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonAdd extends ViewReviewAction.BannerButtonAction {
    private static final String TAG = "ActionBannerButtonAddListener";
    private ConfigGvDataUi.GvDataUiConfig mConfig;
    private GvDataHandler                 mHandler;

    public BannerButtonAdd(ControllerReviewEditable controller, GvDataList.GvType dataType) {
        super(controller, dataType);
        ConfigGvDataUi.Config config = ConfigGvDataUi.getConfig(getDataType());
        mConfig = config.getAdderConfig();
    }

    @Override
    public void onSetReviewView() {
        mHandler = FactoryGvDataHandler.newHandler(getData());
    }

    protected Fragment getNewListener() {
        return new AddListener() {

        };
    }

    @Override
    public String getButtonTitle() {
        return getActivity().getResources().getString(R.string.add) + " " + getDataType()
                .getDatumString();
    }

    @Override
    public void onClick(View v) {
        if (getViewReview() == null) return;

        LauncherUi.launch(mConfig.getReviewDataUI(), getListener(), getRequestCode(),
                mConfig.getTag(), Administrator.get(getActivity()).pack(getController()));
    }

    protected Fragment getListener() {
        return getListener(TAG);
    }

    protected boolean addData(GvDataList.GvData data) {
        boolean added = mHandler.add(data, getActivity());
        getViewReview().updateUi();
        return added;
    }

    protected int getRequestCode() {
        return mConfig.getRequestCode();
    }

    //Dialogs expected to communicate directly with target fragments so using "invisible"
    // fragment as listener.
    //Restrictions on how fragments are constructed mean I have to use an abstract class...
    private abstract class AddListener extends Fragment implements DialogFragmentGvDataAdd
            .GvDataAddListener {
        @Override
        public boolean onGvDataAdd(GvDataList.GvData data) {
            return addData(data);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == getRequestCode() && data != null
                    && ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                addData(GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.NEW, data));
            }
        }
    }
}