package com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Interfaces;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 24/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface AddEditLayout<T extends GvData> extends DialogLayout<T> {
    interface GvDataEditor {
        void setKeyboardAction(EditText editText);

        void setDeleteConfirmTitle(String title);
    }

    interface GvDataAdder {
        void setKeyboardAction(EditText editText);

        void setTitle(String title);
    }

    EditText getEditTextForKeyboardAction();

    T createGvData();

    void clearViews();

    void onAdd(T data);

    @Override
    View getView(int viewId);

    @Override
    void onActivityAttached(Activity activity, Bundle args);

    @Override
    View createLayoutUi(Context context, T data);

    @Override
    void initialise(T data);

    @Override
    void updateLayout(T data);
}
