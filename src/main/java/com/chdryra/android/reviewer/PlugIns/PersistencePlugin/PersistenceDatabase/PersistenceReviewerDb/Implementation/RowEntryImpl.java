/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Implementation.DbEntryType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowEntry;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowEntryImpl<T> implements RowEntry<T> {
    private ColumnInfo<T> mInfo;
    private T mValue;

    public RowEntryImpl(ColumnInfo<T> info, @Nullable T value) {
        mInfo = info;
        mValue = value;
    }

    @Override
    public String getColumnName() {
        return mInfo.getName();
    }

    @Override
    public DbEntryType<T> getEntryType() {
        return mInfo.getType();
    }

    @Override
    public T getValue() {
        return mValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowEntryImpl)) return false;

        RowEntryImpl<?> rowEntry = (RowEntryImpl<?>) o;

        if (!mInfo.equals(rowEntry.mInfo)) return false;
        return !(mValue != null ? !mValue.equals(rowEntry.mValue) : rowEntry.mValue != null);

    }

    @Override
    public int hashCode() {
        int result = mInfo.hashCode();
        result = 31 * result + (mValue != null ? mValue.hashCode() : 0);
        return result;
    }
}