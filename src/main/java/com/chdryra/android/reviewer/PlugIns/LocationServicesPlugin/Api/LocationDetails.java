package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LocationDetails {
    LatLng getLatLng();

    String getDescription();
}
