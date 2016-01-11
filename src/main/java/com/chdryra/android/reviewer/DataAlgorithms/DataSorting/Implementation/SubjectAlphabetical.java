package com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectAlphabetical implements Comparator<DataSubject> {
    @Override
    public int compare(DataSubject lhs, DataSubject rhs) {
        return lhs.getSubject().compareToIgnoreCase(rhs.getSubject());
    }
}
