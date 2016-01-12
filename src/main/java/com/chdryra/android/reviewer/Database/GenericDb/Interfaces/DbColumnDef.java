package com.chdryra.android.reviewer.Database.GenericDb.Interfaces;

import com.chdryra.android.reviewer.Database.GenericDb.Implementation.ValueNullable;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DbColumnDef {
    String getName();

    StorageType getType();

    ValueNullable getNullable();
}
