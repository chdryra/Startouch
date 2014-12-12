/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 20 October, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.GvSubjectRatingList.GvSubjectRating;

import java.text.DecimalFormat;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * {@link DialogHolderAddEdit}: child reviews
 */
class DHChild extends DialogHolderAddEdit<GvSubjectRating> {
    private static final int             LAYOUT    = R.layout.dialog_criterion;
    private static final int             SUBJECT   = R.id.child_name_edit_text;
    private static final int             RATING    = R.id.child_rating_bar;
    private static final GvSubjectRating NULL_DATA = new GvSubjectRating(null, 0);

    DHChild(DialogGvDataAddFragment<GvSubjectRating> dialogAdd) {
        super(LAYOUT, new int[]{SUBJECT, RATING}, dialogAdd, NULL_DATA);
    }

    DHChild(DialogGvDataEditFragment<GvSubjectRating> dialogEdit) {
        super(LAYOUT, new int[]{SUBJECT, RATING}, dialogEdit);
    }

    @Override
    protected EditText getEditTextForKeyboardAction() {
        return (EditText) getView(SUBJECT);
    }

    @Override
    protected String getDialogOnAddTitle(GvSubjectRatingList.GvSubjectRating data) {
        float childRating = data.getRating();
        DecimalFormat formatter = new DecimalFormat("0");
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        String rating = childRating % 1L > 0L ? decimalFormatter.format(childRating) : formatter
                .format(childRating);
        return data.getSubject() + ": " + rating + "/" + "5";
    }

    @Override
    protected String getDialogDeleteConfirmTitle(GvSubjectRatingList.GvSubjectRating data) {
        return data.getSubject() + ": " + data.getRating();

    }

    @Override
    protected GvSubjectRatingList.GvSubjectRating createGVData() {
        String subject = ((EditText) getView(SUBJECT)).getText().toString().trim();
        float rating = ((RatingBar) getView(RATING)).getRating();
        return new GvSubjectRatingList.GvSubjectRating(subject, rating);
    }

    @Override
    protected void updateWithGVData(GvSubjectRatingList.GvSubjectRating data) {
        ((EditText) getView(SUBJECT)).setText(data.getSubject());
        ((RatingBar) getView(RATING)).setRating(data.getRating());
    }
}