/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Criterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 29/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterCriterion implements ReviewItemConverter<DataCriterion> {

    @Override
    public DataCriterion convert(ReviewId id, DataSnapshot snapshot) {
        Criterion value = snapshot.getValue(Criterion.class);
        return value == null ? null :
                new DatumCriterion(id, value.getSubject(), (float) value.getRating());
    }
}
