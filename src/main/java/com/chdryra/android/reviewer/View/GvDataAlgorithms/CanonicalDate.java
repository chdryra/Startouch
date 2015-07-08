/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalDate implements CanonicalDatumMaker<GvDateList.GvDate> {
    @Override
    public GvDateList.GvDate getCanonical(GvDataList<GvDateList.GvDate> data) {
        if (data.size() == 0) return new GvDateList.GvDate(data.getReviewId(), null);

        DatumCounter<GvDateList.GvDate, Date> counter = new DatumCounter<>(data,
                new DataGetter<GvDateList.GvDate, Date>() {
                    @Override
                    public Date getData(GvDateList.GvDate datum) {
                        return datum.getDate();
                    }
                });

        Date canon = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) {
            Date finalDate = data.getItem(0).getDate();
            for (GvDateList.GvDate date : data) {
                canon = date.getDate();
                if (canon.after(finalDate)) finalDate = canon;
            }
        }

        return new GvDateList.GvDate(data.getReviewId(), canon);
    }
}