/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentHeadlineThenAlphabetical implements Comparator<DataComment> {
    @Override
    public int compare(DataComment lhs, DataComment rhs) {
        int comp;
        if (lhs.isHeadline() && !rhs.isHeadline()) {
            comp = -1;
        } else if (!lhs.isHeadline() && rhs.isHeadline()) {
            comp = 1;
        } else {
            comp = lhs.getComment().compareToIgnoreCase(rhs.getComment());
        }

        return comp;
    }
}
