/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.Widgets.ClearableEditText;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectEditUi extends SubjectRvUi<EditText> {
    private SubjectAction<?> mSubjectAction;

    public SubjectEditUi(final ReviewView<?> reviewView, EditText view) {
        super(view, reviewView.getParams().getSubjectParams(), new ReferenceValueGetter<String>() {
            @Override
            public String getValue() {
                return reviewView.getSubject();
            }
        });
        initialise(reviewView);
    }

    private void initialise(ReviewView<?> reviewView) {
        EditText editText = getView();
        setViewValue(reviewView.getSubject());

        ReviewViewParams.Subject params = getParams();
        boolean isEditable = params.isEditable();
        setSubjectRefresh(!isEditable && params.isUpdateOnRefresh());
        if(isEditable) editText.setHint(params.getHint());

        editText.setFocusable(isEditable);
        ((ClearableEditText) editText).makeClearable(isEditable);
        if (isEditable) {
            mSubjectAction = reviewView.getActions().getSubjectAction();
            editText.setOnEditorActionListener(newSubjectActionListener());
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if(!hasFocus && getView().getText().length() > 0) setSubject();
                }
            });
            editText.addTextChangedListener(newSubjectChangeListener());
        }

        update();
    }

    private void setSubject() {
        String newText = getView().getText().toString();
        if(!getTextCache().equals(newText)) {
            updateTextCache();
            mSubjectAction.onKeyboardDone(newText);
        }
    }

    @NonNull
    private TextWatcher newSubjectChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSubjectAction.onTextChanged(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    @NonNull
    private TextView.OnEditorActionListener newSubjectActionListener() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    setSubject();
                    return true;
                }
                return false;
            }
        };
    }

}
