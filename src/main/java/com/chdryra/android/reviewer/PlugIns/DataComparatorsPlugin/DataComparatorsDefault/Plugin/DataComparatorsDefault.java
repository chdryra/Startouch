/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Plugin;

import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.Api.DataComparatorsPlugin;
import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.Api.FactoryDataComparators;

/**
 * Created by: Rizwan Choudrey
 * On: 22/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataComparatorsDefault implements DataComparatorsPlugin{
    private static final FactoryDataComparators FACTORY = new FactoryDataComparatorsDefault();
    @Override
    public FactoryDataComparators getComparatorsFactory() {
        return FACTORY;
    }
}