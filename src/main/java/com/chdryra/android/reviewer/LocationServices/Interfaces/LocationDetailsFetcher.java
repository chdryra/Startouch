/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.LocationServices.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LocationDetailsFetcher {
    void fetchPlaceDetails(LocatedPlace place, LocationDetailsListener listener);

    interface LocationDetailsListener {
        void onPlaceDetailsFound(LocationDetails details);

        void onNotPermissioned();
    }
}
