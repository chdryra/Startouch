/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;


import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.SimpleItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbItemDereferencer<Row extends ReviewDataRow<Row>, Value extends HasReviewId> implements SimpleItemReference.Dereferencer<Value> {
    private DataLoader<Row> mLoader;
    private Converter<Row, Value> mConverter;

    public interface Converter<T, R extends HasReviewId> {
        R convert(T data);
    }

    public DbItemDereferencer(DataLoader<Row> loader, Converter<Row, Value> converter) {
        mLoader = loader;
        mConverter = converter;
    }

    @Override
    public ReviewId getReviewId() {
        return mLoader.getReviewId();
    }

    @Override
    public void dereference(final DataReference.DereferenceCallback<Value> callback) {
        mLoader.onLoaded(new DataLoader.LoadedListener<Row>() {
            @Override
            public void onLoaded(IdableList<Row> data) {
                if (data.size() > 0) {
                    callback.onDereferenced(mConverter.convert(data.getItem(0)), CallbackMessage.ok());
                } else {
                    callback.onDereferenced(null, CallbackMessage.error("No item in database for this reference"));
                }
            }
        }).execute();
    }
}
