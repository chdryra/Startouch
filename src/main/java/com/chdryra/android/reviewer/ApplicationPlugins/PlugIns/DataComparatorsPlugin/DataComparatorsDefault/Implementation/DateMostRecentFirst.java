/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DateTime;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DateMostRecentFirst implements Comparator<DateTime> {
    @Override
    public int compare(DateTime lhs, DateTime rhs) {
        Date lhsDate = new Date(lhs.getTime());
        Date rhsDate = new Date(rhs.getTime());
        return rhsDate.compareTo(lhsDate);
    }
}
