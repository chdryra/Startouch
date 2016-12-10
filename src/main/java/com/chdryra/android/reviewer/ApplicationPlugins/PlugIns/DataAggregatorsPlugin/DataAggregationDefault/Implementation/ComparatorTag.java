/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Interfaces.ComparatorString;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.ItemGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorTag extends ComparatorStringable<DataTag> {
    public ComparatorTag(ComparatorString comparitor) {
        super(comparitor, new ItemGetter<DataTag, String>() {
            @Override
            public String getItem(DataTag datum) {
                return datum.getTag().toLowerCase();
            }
        });
    }
}