/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 29/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbRefDatalist<Value extends HasReviewId> extends FbReviewListRef<Value, ReviewItemReference<Value>> implements RefDataList<Value> {
    public FbRefDatalist(ReviewId id, Firebase reference, ReviewItemReference<DataSize>
            sizeReference, ListConverter<Value> converter, ListItemsReferencer<Value,
            ReviewItemReference<Value>> itemReferencer) {
        super(id, reference, sizeReference, converter, itemReferencer);
    }
}