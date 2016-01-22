package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Implementation.DbEntryType;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowEntry<T> {
    String getColumnName();
    DbEntryType<T> getEntryType();
    @Nullable T getValue();
}
