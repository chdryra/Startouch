package com.chdryra.android.reviewer.Database.GenericDb.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public enum ValueNullable {
    TRUE(true),
    FALSE(false);

    private boolean mNullable;
    ValueNullable(boolean nullable) {

    }

    public boolean isNullable() {
        return mNullable;
    }
}
