/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Implementation.UserModel;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;

import java.util.UUID;

/**
 * Unique user ID for author credentials. A Wrapper for a UUID.
 * <p/>
 * <p>
 * Use static method <code>generateId()</code> to return a unique UserId.
 * </p>
 *
 * @see DatumAuthor
 */

public class AuthorId implements UserId {
    private final UUID mId;

    private AuthorId(UUID id) {
        mId = id;
    }

    public static AuthorId generateId() {
        return new AuthorId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass() && this.mId.equals(((AuthorId) obj).mId);
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public String toString() {
        return mId.toString();
    }
}