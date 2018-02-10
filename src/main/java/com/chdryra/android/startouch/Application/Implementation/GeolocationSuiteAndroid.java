/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Application.Implementation;

import android.app.Activity;

import com.chdryra.android.corelibrary.LocationUtils.LocationClient;
import com.chdryra.android.corelibrary.LocationUtils.LocationClientGoogle;
import com.chdryra.android.startouch.Application.Interfaces.GeolocationSuite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServices;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class GeolocationSuiteAndroid implements GeolocationSuite {
    private final LocationServices mApi;
    private Activity mActivity;

    public GeolocationSuiteAndroid(LocationServices api) {
        mApi = api;
    }

    @Override
    public LocationServices getLocationServices() {
        return mApi;
    }

    @Override
    public LocationClient newLocationClient() {
        return new LocationClientGoogle(mActivity);
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }
}
