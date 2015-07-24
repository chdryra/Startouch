/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.widget.EditText;

import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.Dialogs.AddEditComment;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditCommentTest extends AddEditLayoutTest<GvCommentList.GvComment> {
    public AddEditCommentTest() {
        super(GvCommentList.TYPE, new AddEditComment(new ConfigGvDataAddEditView.AddComment()));
    }

    @Override
    protected void enterData(GvCommentList.GvComment comment) {
        mEditText.setText(comment.getComment());
    }

    @Override
    protected void checkViewAndDataEquivalence(GvCommentList.GvComment datum, boolean result) {
        assertEquals(result, mEditText.getText().toString().trim().equals(datum.getComment()));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mEditText = (EditText) getView(AddEditComment.COMMENT);
        assertNotNull(mEditText);
    }

    @Override
    protected GvCommentList.GvComment newDatum() {
        GvCommentList.GvComment data = super.newDatum();
        data.setIsHeadline(false);
        return data;
    }
}
