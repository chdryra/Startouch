/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 December, 2014
 */

package com.chdryra.android.reviewer.Controller;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataImage {
    public Bitmap getBitmap();

    public Date getDate();

    public String getCaption();

    public boolean isCover();
}
