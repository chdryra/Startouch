/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.RowAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowAuthorImpl extends RowTableBasic<RowAuthor> implements RowAuthor {
    private String mUserId;
    private String mName;

    public RowAuthorImpl(DataAuthor author) {
        mUserId = author.getUserId().toString();
        mName = author.getName();
    }

    //Via reflection
    public RowAuthorImpl() {
    }

    public RowAuthorImpl(RowValues values) {
        mUserId = values.getValue(USER_ID.getName(), USER_ID.getType());
        mName = values.getValue(AUTHOR_NAME.getName(), AUTHOR_NAME.getType());
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public UserId getUserId() {
        return new DatumUserId(mUserId);
    }

    @Override
    public String getRowId() {
        return mUserId;
    }

    @Override
    public String getRowIdColumnName() {
        return USER_ID.getName();
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }

    @Override
    protected int size() {
        return 2;
    }

    @Override
    protected RowEntry<RowAuthor, ?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(RowAuthor.class, USER_ID, mUserId);
        } else if(position == 1){
            return new RowEntryImpl<>(RowAuthor.class, AUTHOR_NAME, mName);
        } else {
            throw noElement();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowAuthorImpl)) return false;

        RowAuthorImpl that = (RowAuthorImpl) o;

        if (mUserId != null ? !mUserId.equals(that.mUserId) : that.mUserId != null) return false;
        return !(mName != null ? !mName.equals(that.mName) : that.mName != null);

    }

    @Override
    public int hashCode() {
        int result = mUserId != null ? mUserId.hashCode() : 0;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        return result;
    }
}