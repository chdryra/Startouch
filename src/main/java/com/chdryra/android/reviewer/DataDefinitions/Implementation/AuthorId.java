/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

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

public class AuthorId implements com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId {
    private final UUID mId;

    private AuthorId(UUID id) {
        mId = id;
    }

    public static AuthorId generateId() {
        return new AuthorId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return mId.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId)) return false;

        com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId authorId = (com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId) o;

        return !(mId != null ? !toString().equals(authorId.toString()) : authorId.toString() != null);
    }

    @Override
    public int hashCode() {
        return mId != null ? mId.hashCode() : 0;
    }
}