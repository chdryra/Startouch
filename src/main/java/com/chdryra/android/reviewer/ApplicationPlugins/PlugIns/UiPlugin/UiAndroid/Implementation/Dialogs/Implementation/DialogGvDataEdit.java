/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogCancelDeleteDoneFragment;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewBuilderSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api
        .LocationServicesApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Configs.DefaultLayoutConfig;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Factories.FactoryDialogLayout;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.DatumLayoutEdit;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.GvDataEditor;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataEditListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 16/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Base class for all dialog fragments that can edit data on reviews.
 */
public abstract class DialogGvDataEdit<T extends GvDataParcelable>
        extends DialogCancelDeleteDoneFragment
        implements GvDataEditor, LaunchableUi {

    private static final int EDIT = R.string.edit;

    private final GvDataType<T> mDataType;
    private DatumLayoutEdit<T> mLayout;
    private ReviewDataEditor<T> mEditor;
    private DataEditListener<T> mListener;
    private T mDatum;

    private boolean mQuickSet = false;

    public DialogGvDataEdit(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    @Override
    public String getLaunchTag() {
        return "Edit" + mDataType.getDatumName();
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(this);
    }

    @Override
    public void setKeyboardAction(EditText editText) {
        setKeyboardDoDoneOnEditText(editText);
    }

    @Override
    public void setDeleteTitle(String title) {
        setDeleteWhatTitle(title);
    }

    @Override
    protected void onConfirmedDeleteButtonClick() {
        if (isQuickSet()) {
            mEditor.delete(mDatum);
            mEditor.commitData();
        } else {
            mListener.onDelete(mDatum, getTargetRequestCode());
        }
    }

    @Override
    protected boolean hasDataToDelete() {
        return mDatum != null;
    }

    @Override
    protected View createDialogUi() {
        return mLayout.createLayoutUi(getActivity(), mDatum);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        setIsQuickSet();
        setDialogTitle();
        getDatumToEdit();
    }

    @Override
    protected void onDoneButtonClick() {
        T newDatum = mLayout.createGvDataFromInputs();
        if (isQuickSet()) {
            if (!mDatum.equals(newDatum)) mEditor.replace(mDatum, newDatum);
            mEditor.commitData();
        } else {
            mListener.onEdit(mDatum, newDatum, getTargetRequestCode());
        }
    }

    private boolean isQuickSet() {
        return mQuickSet && mEditor != null;
    }

    private void setIsQuickSet() {
        Bundle args = getArguments();
        mQuickSet = args != null && args.getBoolean(ReviewBuilderSuite.QUICK_ADD);
        if (!mQuickSet) {
            //TODO make type safe
            mListener = (DataEditListener<T>) getTargetListenerOrThrow(DataEditListener.class);
        } else {
            ApplicationInstance app = AppInstanceAndroid.getInstance(getActivity());
            mEditor = app.getReviewBuilder().getReviewEditor().newDataEditor(mDataType);
        }
    }

    private void getDatumToEdit() {
        ParcelablePacker<T> unpacker = new ParcelablePacker<>();
        mDatum = unpacker.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, getArguments());
    }

    private void setLayout() {
        LocationServicesApi services = AppInstanceAndroid.getInstance(getActivity())
                .getLocationServices().getApi();
        FactoryDialogLayout layoutFactory = new FactoryDialogLayout(getActivity(), new
                DefaultLayoutConfig(), services);
        mLayout = layoutFactory.newLayout(mDataType, this);
        mLayout.onActivityAttached(getActivity(), getArguments());
    }

    private void setDialogTitle() {
        if (mDataType.equals(GvImage.TYPE)) {
            setDialogTitle(null);
            hideKeyboardOnLaunch();
        } else {
            setDialogTitle(edit() + " " + mDataType.getDatumName());
        }
    }

    private String edit() {
        return getString(EDIT);
    }
}
