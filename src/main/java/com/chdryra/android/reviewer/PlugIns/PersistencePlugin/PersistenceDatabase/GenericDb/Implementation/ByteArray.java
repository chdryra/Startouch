package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation;


import java.util.Arrays;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ByteArray {
    private byte[] mData;

    public ByteArray(byte[] data) {
        mData = data;
    }

    public byte[] getData() {
        return mData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ByteArray)) return false;

        ByteArray byteArray = (ByteArray) o;

        return Arrays.equals(mData, byteArray.mData);

    }

    @Override
    public int hashCode() {
        return mData != null ? Arrays.hashCode(mData) : 0;
    }
}
