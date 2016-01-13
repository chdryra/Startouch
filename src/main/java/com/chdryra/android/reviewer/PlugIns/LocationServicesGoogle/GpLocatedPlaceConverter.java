/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 March, 2015
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesGoogle;

import com.chdryra.android.remoteapifetchers.GpPlaceSearchResults;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 27/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GpLocatedPlaceConverter {
    public static ArrayList<LocatedPlace> convert(GpPlaceSearchResults results) {
        ArrayList<LocatedPlace> places = new ArrayList<>();
        for (GpPlaceSearchResults.GpPlaceSearchResult result : results) {
            LatLng latlng = result.getGeometry().getLatLng();
            String description = result.getName().getString();
            String googleId = result.getPlaceId().getString();

            places.add(new GooglePlace(latlng, description, googleId));
        }

        return places;
    }

}
