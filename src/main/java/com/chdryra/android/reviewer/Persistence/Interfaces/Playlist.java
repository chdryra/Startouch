/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface Playlist extends ReferencesRepository{
    String getName();

    void addEntry(ReviewId reviewId, PlaylistCallback callback);

    void removeEntry(ReviewId reviewId, PlaylistCallback callback);

    void hasEntry(ReviewId reviewId, PlaylistCallback callback);
}