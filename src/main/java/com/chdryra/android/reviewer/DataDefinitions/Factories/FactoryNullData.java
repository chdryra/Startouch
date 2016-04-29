/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryNullData {
    private static final AuthorId NULL_USER_ID = new DatumAuthorId(AuthorId.NULL_ID_STRING);

    public static DataAuthorReview nullAuthor(ReviewId id) {
        return new DatumAuthorReview(id, "", NULL_USER_ID);
    }

    public static DataComment nullComment(ReviewId id) {
        return new DatumComment(id, "", false);
    }

    public static DataCriterion nullCriterion(ReviewId id) {
        return new DatumCriterion(id, "", 0f);
    }

    public static DataDateReview nulDate(ReviewId id) {
        return new DatumDateReview(id, 0l);
    }

    public static DataFact nullFact(ReviewId id) {
        return new DatumFact(id, "", "");
    }

    public static DataImage nullImage(ReviewId id) {
        return new DatumImage(id);
    }

    public static DataLocation nullLocation(ReviewId id) {
        return new DatumLocation(id);
    }

    public static DataSubject nullSubject(ReviewId id) {
        return new DatumSubject(id, "");
    }

    public static DataTag nullTag(ReviewId id) {
        return new DatumTag(id, "");
    }
}
