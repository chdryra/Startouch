/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.DataReferenceBasic;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbRefData<T> extends DataReferenceBasic<T> {
    private Firebase mReference;
    private Map<ReferenceBinder<T>, ValueEventListener> mBindings;
    private SnapshotConverter<T> mConverter;

    public FbRefData(Firebase reference, SnapshotConverter<T> converter) {
        mReference = reference;
        mConverter = converter;
        mBindings = new HashMap<>();
        mListeners = new ArrayList<>();
    }

    protected Firebase getReference() {
        return mReference;
    }

    @Override
    public void unbindFromValue(ReferenceBinder<T> binder) {
        if (isValidReference() && mBindings.containsKey(binder)) {
            mReference.removeEventListener(mBindings.get(binder));
        }
    }

    @Override
    public void bindToValue(final ReferenceBinder<T> binder) {
        if (isValidReference() && !mBindings.containsKey(binder)) {
            ValueEventListener listener = newListener(binder);
            mBindings.put(binder, listener);
            mReference.addValueEventListener(listener);
        }
    }

    @Override
    protected void onInvalidate() {
        for (Map.Entry<ReferenceBinder<T>, ValueEventListener> binding : mBindings.entrySet()) {
            binding.getKey().onInvalidated(this);
            mReference.removeEventListener(binding.getValue());
        }

        mBindings.clear();
        mConverter = null;
        mReference = null;
    }

    @Override
    public void registerListener(InvalidationListener listener) {
        if(!mListeners.contains(listener)) mListeners.add(listener);
    }

    @Override
    public void unregisterListener(InvalidationListener listener) {
        if(mListeners.contains(listener)) mListeners.remove(listener);
    }

    @Override
    public void dereference(final DereferenceCallback<T> callback) {
        if (isValidReference()) {
            mReference.addListenerForSingleValueEvent(getDereferencer(callback));
        }
    }

    @NonNull
    private ValueEventListener getDereferencer(final DereferenceCallback<T> callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                T value = mConverter.convert(dataSnapshot);
                if (value != null) {
                    onDereferenced(value);
                    callback.onDereferenced(value, CallbackMessage.ok());
                } else {
                    callback.onDereferenced(null, CallbackMessage.error("Couldn't dereference"));

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onDereferenced(null, CallbackMessage.error("Call to Firebase cancelled"));
            }
        };
    }

    protected void onDereferenced(T value) {

    }

    @NonNull
    private ValueEventListener newListener(final ReferenceBinder<T> binder) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                T convert = mConverter.convert(dataSnapshot);
                if (convert != null) binder.onReferenceValue(convert);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }
}
