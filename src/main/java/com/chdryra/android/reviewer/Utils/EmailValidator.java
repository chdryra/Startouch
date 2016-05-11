/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Utils;

import android.widget.EditText;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailValidator {
    public static boolean isValid(String target) {
        return target != null
                && org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(target);
    }

    public static boolean isValid(EditText editText) {
        return isValid(editText.getText().toString());
    }
}
