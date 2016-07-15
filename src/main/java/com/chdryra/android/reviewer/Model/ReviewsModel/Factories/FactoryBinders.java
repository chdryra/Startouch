/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.BindersManagerNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.BindersManager;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaReference;

/**
 * Created by: Rizwan Choudrey
 * On: 26/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryBinders {
    public ReferenceBinder bindTo(MetaReference reference, ReferenceBinder.DataSizeBinder sizebinder, @Nullable ReferenceBinder.DataBinder dataBinder) {
        return new ReferenceBinder(reference, sizebinder, dataBinder);
    }

    public BindersManager newReferenceBindersManager() {
        return new BindersManager();
    }

    public BindersManagerNode newMetaBindersManager() {
        return new BindersManagerNode();
    }
}
