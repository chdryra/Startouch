/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReferenceBinders {
    interface CoversBinder extends ValueBinder<IdableList<DataImage>> {
    }

    interface CriteriaBinder extends ValueBinder<IdableList<DataCriterion>> {
    }

    interface CommentsBinder extends ValueBinder<IdableList<DataComment>> {
    }

    interface FactsBinder extends ValueBinder<IdableList<DataFact>> {
    }

    interface LocationsBinder extends ValueBinder<IdableList<DataLocation>> {
    }

    interface ImagesBinder extends ValueBinder<IdableList<DataImage>> {
    }

    interface TagsBinder extends ValueBinder<IdableList<DataTag>> {
    }

    interface SizeBinder extends ValueBinder<DataSize> {

    }
}
