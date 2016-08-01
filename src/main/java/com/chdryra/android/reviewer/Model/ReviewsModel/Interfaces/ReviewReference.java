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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewReference extends DataReviewInfo, ReviewDataReference<Review> {
    ReviewNode asNode();

    ReviewDataReference<DataImage> getCover();

    ReviewListReference<DataCriterion> getCriteria();

    ReviewListReference<DataComment> getComments();

    ReviewListReference<DataFact> getFacts();

    ReviewListReference<DataImage> getImages();

    ReviewListReference<DataLocation> getLocations();

    ReviewListReference<DataTag> getTags();

    ReviewDataReference<DataSize> getCriteriaSize();

    ReviewDataReference<DataSize> getCommentsSize();

    ReviewDataReference<DataSize> getFactsSize();

    ReviewDataReference<DataSize> getImagesSize();

    ReviewDataReference<DataSize> getLocationsSize();

    ReviewDataReference<DataSize> getTagsSize();
}
