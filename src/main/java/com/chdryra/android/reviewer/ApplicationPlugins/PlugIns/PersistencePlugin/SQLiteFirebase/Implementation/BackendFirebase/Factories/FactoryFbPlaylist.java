/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendInfoConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbPlaylist;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Persistence.Interfaces.Playlist;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryFbPlaylist {
    private final FbReviewsStructure mStructure;
    private final SnapshotConverter<ReviewListEntry> mEntryConverter;
    private final BackendInfoConverter mInfoConvter;
    private final FactoryFbReviewReference mReferencer;

    public FactoryFbPlaylist(FbReviewsStructure structure, SnapshotConverter<ReviewListEntry>
            entryConverter, BackendInfoConverter infoConvter, FactoryFbReviewReference referencer) {
        mStructure = structure;
        mEntryConverter = entryConverter;
        mInfoConvter = infoConvter;
        mReferencer = referencer;
    }

    public Playlist newPlaylist(Firebase root, String name, AuthorId authorId) {
        return new FbPlaylist(root, mStructure, mEntryConverter, mReferencer, name, authorId, mInfoConvter);
    }
}
