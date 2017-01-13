/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories;



import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbAuthorReviewsMutable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbAuthorReviewsReadable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.Playlist;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorsRepo {
    private final BackendReviewConverter mReviewConverter;
    private final BackendValidator mValidator;
    private final SnapshotConverter<ReviewListEntry> mEntryConverter;
    private final FactoryFbReviewReference mReferencer;
    private final ReviewsCache mCache;
    private final FactoryFbPlaylist mPlaylistFactory;

    public FactoryAuthorsRepo(BackendReviewConverter reviewConverter,
                              BackendValidator validator,
                              SnapshotConverter<ReviewListEntry> entryConverter,
                              FactoryFbReviewReference referencer,
                              ReviewsCache cache,
                              FactoryFbPlaylist playlistFactory) {
        mReviewConverter = reviewConverter;
        mValidator = validator;
        mEntryConverter = entryConverter;
        mReferencer = referencer;
        mCache = cache;
        mPlaylistFactory = playlistFactory;
    }

    public ReferencesRepository newAuthorsDbReadable(Firebase root, FbAuthorsReviews authorsDb) {
        return new FbAuthorReviewsReadable(root, authorsDb, mEntryConverter, mReferencer);
    }

    public MutableRepository newAuthorsDbMutable(Firebase root, FbAuthorsReviews authorsDb) {
        Playlist bookmarks = mPlaylistFactory.newPlaylist(root, Strings.Playlists.BOOKMARKS,
                authorsDb.getAuthorId());
        return new FbAuthorReviewsMutable(root, authorsDb, mEntryConverter, mReviewConverter,
                mValidator, mReferencer, mCache, bookmarks);
    }
}
