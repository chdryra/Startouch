package com.chdryra.android.reviewer.View.Screens;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.mygenerallibrary.DialogDeleteConfirm;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuDataEdit<T extends GvData> extends ReviewViewAction.MenuAction {
    private static final String TAG = "ActionMenuDeleteDoneGridListener";

    private static final int MENU = R.menu.menu_delete_done;
    public static final int MENU_DELETE_ID = R.id.menu_item_delete;
    public static final int MENU_DONE_ID = R.id.menu_item_done;

    public static final ActivityResultCode RESULT_DELETE = ActivityResultCode.DELETE;
    public static final ActivityResultCode RESULT_DONE = ActivityResultCode.DONE;

    private static final int DELETE_CONFIRM = 314;

    private final MenuActionItem mDeleteAction;
    private final MenuActionItem mDoneAction;

    private final String mDeleteWhat;
    private final boolean mDismissOnDelete;
    private final boolean mDismissOnDone;

    private final Fragment mListener;
    private GvDataType<T> mDataType;
    private ReviewDataEditor<T> mEditor;

    //Constructors
    public MenuDataEdit(GvDataType<T> dataType) {
        this(dataType, dataType.getDataName(), dataType.getDataName());
    }

    public MenuDataEdit(GvDataType<T> dataType, String title, String deleteWhat) {
        this(dataType, title, deleteWhat, false);
    }

    public MenuDataEdit(GvDataType<T> dataType, String title, String deleteWhat,
                        boolean dismissOnDelete) {
        this(dataType, title, deleteWhat, dismissOnDelete, true, MENU);
    }

    public MenuDataEdit(GvDataType<T> dataType, String title, String deleteWhat,
                        boolean dismissOnDelete, boolean dismissOnDone, int menuId) {
        super(menuId, title, true);
        mDataType = dataType;

        mDeleteWhat = deleteWhat;
        mDismissOnDelete = dismissOnDelete;
        mDismissOnDone = dismissOnDone;

        mDeleteAction = new MenuActionItem() {
            //Overridden
            @Override
            public void doAction(Context context, MenuItem item) {
                if (hasDataToDelete()) showDeleteConfirmDialog();
            }
        };

        mDoneAction = new MenuActionItem() {
            //Overridden
            @Override
            public void doAction(Context context, MenuItem item) {
                doDoneSelected();
                sendResult(RESULT_DONE);
            }
        };

        addMenuItems();
        mListener = new DeleteConfirmListener() {
        };
        registerActionListener(mListener, TAG);
    }

    //protected methods
    protected MenuActionItem getDeleteAction() {
        return mDeleteAction;
    }

    protected MenuActionItem getDoneAction() {
        return mDoneAction;
    }

    protected ReviewBuilderAdapter.DataBuilderAdapter getBuilder() {
        return (ReviewBuilderAdapter.DataBuilderAdapter) getAdapter();
    }

    protected ReviewDataEditor<T> getEditor() {
        return mEditor;
    }

    protected void bindDefaultDeleteActionItem(int deleteId) {
        bindMenuActionItem(getDeleteAction(), deleteId, false);
    }

    protected void bindDefaultDoneActionItem(int doneId) {
        bindMenuActionItem(getDoneAction(), doneId, mDismissOnDone);
    }

    protected void doDeleteSelected() {
        if (hasDataToDelete()) {
            getBuilder().deleteAll();
            if (mDismissOnDelete) {
                sendResult(RESULT_DELETE);
                getActivity().finish();
            }
        }
    }

    private void doDoneSelected() {
        mEditor.commitEdits();
    }

    private void showDeleteConfirmDialog() {
        String deleteWhat = "all " + mDeleteWhat;
        DialogDeleteConfirm.showDialog(deleteWhat, mListener, DELETE_CONFIRM,
                getActivity().getFragmentManager());
    }

    private boolean hasDataToDelete() {
        return getGridData() != null && getGridData().size() > 0;
    }

    //Overridden
    @Override
    protected void addMenuItems() {
        bindDefaultDeleteActionItem(MENU_DELETE_ID);
        bindDefaultDoneActionItem(MENU_DONE_ID);
    }

    @Override
    protected void doUpSelected() {
        mEditor.discardEdits();
        super.doUpSelected();
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mEditor = ReviewDataEditor.cast(getReviewView(), mDataType);
    }

    private abstract class DeleteConfirmListener extends Fragment implements DialogAlertFragment
            .DialogAlertListener {
        //Overridden
        @Override
        public void onAlertNegative(int requestCode, Bundle args) {

        }

        @Override
        public void onAlertPositive(int requestCode, Bundle args) {
            if (requestCode == DELETE_CONFIRM) doDeleteSelected();
        }
    }

}