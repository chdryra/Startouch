/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Plugin.DataAggregatorsDefault;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Plugin.DataComparatorsDefault;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.GoogleLocationServices.LocationServicesGoogle;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api.NetworkServicesPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Plugin.NetworkServicesAndroid;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Plugin.SQLiteFirebase;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.Api.UiPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationPluginsTest implements ApplicationPlugins {
    private static final String PERSISTENCE_NAME = "TestReviewer";
    private static final int PERSISTENCE_VER = 1;

    private final Context mContext;

    public ApplicationPluginsTest(Context context) {
        mContext = context;
    }

    @Override
    public DataComparatorsPlugin getDataComparators() {
        return new DataComparatorsDefault();
    }

    @Override
    public DataAggregatorsPlugin getDataAggregators() {
        return new DataAggregatorsDefault();
    }

    @Override
    public PersistencePlugin getPersistence() {
        return new SQLiteFirebase(mContext, PERSISTENCE_NAME, PERSISTENCE_VER);
    }

    @Override
    public LocationServicesPlugin getLocationServices() {
        return new LocationServicesGoogle(mContext);
    }

    @Override
    public NetworkServicesPlugin getNetworkServices() {
        return new NetworkServicesAndroid(mContext);
    }

    @Override
    public UiPlugin getUi() {
        return new UiAndroid();
    }
}
