/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories.FactoryFbReviewReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FbReferencesRepositoryBasic implements ReferencesRepository {
    private final Firebase mDataBase;
    private final SnapshotConverter<ReviewListEntry> mEntryConverter;
    private final FbReviews mStructure;
    private final FactoryFbReviewReference mReferencer;
    private final TagsManager mTagsManager;
    private final Map<String, ChildEventListener> mSubscribers;

    protected abstract Firebase getAggregatesDb(ReviewListEntry entry);

    protected abstract Firebase getReviewDb(ReviewListEntry entry);

    FbReferencesRepositoryBasic(Firebase dataBase,
                                FbReviews structure,
                                SnapshotConverter<ReviewListEntry> entryConverter,
                                FactoryFbReviewReference referencer,
                                TagsManager tagsManager) {
        mDataBase = dataBase;
        mEntryConverter = entryConverter;
        mStructure = structure;
        mSubscribers = new HashMap<>();
        mReferencer = referencer;
        mTagsManager = tagsManager;
    }

    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    protected Query getQuery(Firebase entriesDb) {
        return entriesDb.orderByChild(ReviewListEntry.DATE);
    }

    @Override
    public void subscribe(ReviewsSubscriber subscriber) {
        ChildEventListener listener = newChildEventListener(subscriber);
        mSubscribers.put(subscriber.getSubscriberId(), listener);
        getQuery().addChildEventListener(listener);
    }

    @Override
    public void unsubscribe(ReviewsSubscriber subscriber) {
        ChildEventListener listener = mSubscribers.remove(subscriber.getSubscriberId());
        if (listener != null) getQuery().removeEventListener(listener);
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        Firebase entry = mStructure.getListEntryDb(mDataBase, reviewId);
        doSingleEvent(entry, newGetReferenceListener(callback));
    }

    Firebase getDataBase() {
        return mDataBase;
    }

    private Query getQuery() {
        return getQuery(mStructure.getListEntriesDb(mDataBase));
    }

    @NonNull
    private ChildEventListener newChildEventListener(final ReviewsSubscriber subscriber) {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ReviewReference reference = newReference(dataSnapshot);
                if(reference != null) subscriber.onReviewAdded(reference);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ReviewReference reference = newReference(dataSnapshot);
                if(reference != null) subscriber.onReviewRemoved(reference);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }

    @NonNull
    private ValueEventListener newGetReferenceListener(final RepositoryCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReviewReference reference = newReference(dataSnapshot);
                RepositoryResult result;
                if(reference != null) {
                    result= new RepositoryResult(reference);
                } else {
                    result = new RepositoryResult(CallbackMessage.error("Reference not found"));
                }
                callback.onRepositoryCallback(result);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                BackendError error = FirebaseBackend.backendError(firebaseError);
                callback.onRepositoryCallback(new RepositoryResult(CallbackMessage.error(error
                        .getMessage())));
            }
        };
    }

    @Nullable
    private ReviewReference newReference(DataSnapshot dataSnapshot) {
        ReviewListEntry entry = mEntryConverter.convert(dataSnapshot);
        return entry != null ?
                mReferencer.newReview(entry.toInverseDate(), getReviewDb(entry), getAggregatesDb(entry)) : null;
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }
}
