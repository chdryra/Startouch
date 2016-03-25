/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LatitudeLongitude {
    private double latitude;
    private double longitude;

    public LatitudeLongitude() {
    }

    public LatitudeLongitude(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }

    double getLatitude() {
        return latitude;
    }

    double getLongitude() {
        return longitude;
    }
}
