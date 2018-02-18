/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.ItemBindersDelegate;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionBinder;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionReference;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FbListReferenceBasic<T, C extends Collection<T>, S extends Size> extends
        FbRefData<C> implements

        CollectionReference<T, C, S>, ItemBindersDelegate.BindableListReference<T, C, S> {
    private final Map<CollectionBinder<T>, ChildEventListener> mItemBinders;
    private final ItemBindersDelegate<T, S> mManager;
    private SnapshotConverter<T> mItemConverter;

    public FbListReferenceBasic(Firebase reference,
                                SnapshotConverter<C> listConverter,
                                SnapshotConverter<T> itemConverter) {
        super(reference, listConverter);
        mItemConverter = itemConverter;
        mItemBinders = new HashMap<>();
        mManager = new ItemBindersDelegate<>(this);
    }

    protected void doBinding(ChildEventListener listener) {
        getReference().addChildEventListener(listener);
    }

    protected void doUnbinding(ChildEventListener listener) {
        getReference().removeEventListener(listener);
    }

    @Override
    public void unbindFromItems(CollectionBinder<T> binder) {
        mManager.unbindFromItems(binder);
    }

    @Override
    public void bindToItems(final CollectionBinder<T> binder) {
        mManager.bindToItems(binder);
    }

    @Override
    public Collection<CollectionBinder<T>> getItemBinders() {
        return mItemBinders.keySet();
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        for (Map.Entry<CollectionBinder<T>, ChildEventListener> binding : mItemBinders.entrySet()) {
            doUnbinding(binding.getValue());
        }
        mManager.notifyBinders();
        mItemBinders.clear();
        mItemConverter = null;
    }

    @Override
    public void unbindItemBinder(CollectionBinder<T> binder) {
        ChildEventListener listener = mItemBinders.remove(binder);
        doUnbinding(listener);
    }

    @Override
    public void bindItemBinder(CollectionBinder<T> binder) {
        ChildEventListener listener = newChildListener(binder);
        mItemBinders.put(binder, listener);
        doBinding(listener);
    }

    @Override
    public boolean containsItemBinder(CollectionBinder<T> binder) {
        return mItemBinders.containsKey(binder);
    }

    @NonNull
    private ChildEventListener newChildListener(final CollectionBinder<T> binder) {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                T convert = mItemConverter.convert(dataSnapshot);
                if (convert != null) binder.onItemAdded(convert);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                T convert = mItemConverter.convert(dataSnapshot);
                if (convert != null) binder.onItemRemoved(convert);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                invalidate();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }

}