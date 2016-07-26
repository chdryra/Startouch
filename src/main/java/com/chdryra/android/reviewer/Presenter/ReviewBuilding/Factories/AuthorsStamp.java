/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.PublishDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorsStamp {
    private final NamedAuthor mAuthor;

    public NamedAuthor getAuthor() {
        return mAuthor;
    }

    public AuthorsStamp(NamedAuthor author) {
        mAuthor = author;
    }

    public ReviewStamp newStamp() {
        return ReviewStamp.newStamp(mAuthor.getAuthorId(), new PublishDate(new Date().getTime()));
    }
}
