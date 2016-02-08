/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewBuilderImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewBuilder {
    private ConverterGv mConverterGv;
    private TagsManager mTagsManager;
    private FactoryReviews mFactoryReviews;
    private DataValidator mDataValidator;
    private FactoryDataBuilder mDataBuilderFactory;

    public FactoryReviewBuilder(ConverterGv converterGv,
                                DataValidator dataValidator, TagsManager tagsManager,
                                FactoryReviews factoryReviews,
                                FactoryDataBuilder dataBuilderfactory) {
        mConverterGv = converterGv;
        mTagsManager = tagsManager;
        mFactoryReviews = factoryReviews;
        mDataValidator = dataValidator;
        mDataBuilderFactory = dataBuilderfactory;
    }

    public ReviewBuilder newBuilder() {
        return new ReviewBuilderImpl(mConverterGv,
                mTagsManager,
                mFactoryReviews,
                mDataBuilderFactory,
                mDataValidator);
    }
}
