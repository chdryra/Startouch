package com.chdryra.android.reviewer.View.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 09/10/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonEdit<T extends GvData>
        extends ReviewViewAction.BannerButtonAction implements
        DialogAlertFragment.DialogAlertListener,
        DialogGvDataAdd.GvDataAddListener<T>,
        ActivityResultListener{
    private GvDataType<T> mDataType;
    private final ConfigGvDataUi.LaunchableConfig mConfig;
    private GvDataList<T> mAdded;
    private ReviewDataEditor<T> mEditor;

    protected BannerButtonEdit(GvDataType<T> dataType, String title) {
        super(title);
        mDataType = dataType;
        mConfig = ConfigGvDataUi.getConfig(mDataType).getAdderConfig();
        initDataList();
    }

    //protected methods
    protected int getLaunchableRequestCode() {
        return mConfig.getRequestCode();
    }

    //TODO make type safe
    protected boolean addData(T data) {
        return mEditor.add(data);
    }

    protected void showAlertDialog(String alert, int requestCode) {
        DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, requestCode, new Bundle());
        DialogShower.show(dialog, getReviewView().getFragment(), requestCode, DialogAlertFragment.ALERT_TAG);
    }

    private void initDataList() {
        mAdded = FactoryGvData.newDataList(mDataType);
    }

    //Overridden
    @Override
    public void onClick(View v) {
        LauncherUi.launch(mConfig.getLaunchable(), getReviewView().getFragment(),
                getLaunchableRequestCode(), mConfig.getTag(), new Bundle());
    }

    @Override
    public void onAttachReviewView() {
        mEditor = ReviewDataEditor.cast(getReviewView(), mDataType);
    }


    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {

    }

    @Override
    public boolean onGvDataAdd(T data, int requestCode) {
        boolean success = false;
        if(requestCode == getLaunchableRequestCode()) {
            success = addData(data);
            if (success) mAdded.add(data);
        }
        return success;
    }

    @Override
    public void onGvDataCancel(int requestCode) {
        if(requestCode == getLaunchableRequestCode()) {
            for (T added : mAdded) {
                mEditor.delete(added);
            }
            initDataList();
        }
    }

    @Override
    public void onGvDataDone(int requestCode) {
        if(requestCode == getLaunchableRequestCode()) initDataList();
    }

    //For location and URL add activities
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getLaunchableRequestCode() && data != null
                && ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
            T datum = (T) GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.NEW, data);
            onGvDataAdd(datum, requestCode);
        }
    }
}