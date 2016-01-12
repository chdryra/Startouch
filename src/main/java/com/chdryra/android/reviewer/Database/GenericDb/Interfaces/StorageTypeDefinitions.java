package com.chdryra.android.reviewer.Database.GenericDb.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface StorageTypeDefinitions {
    StorageType getTextType();

    StorageType getRealType();

    StorageType getIntegerType();

    StorageType getBooleanType();

    StorageType getBitmapType();
}
