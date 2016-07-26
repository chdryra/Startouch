/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsApi;

import test.TestUtils.RandomAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorAuthorsTest extends AggregatedDistinctItemsTest<DataAuthor>{
    @NonNull
    @Override
    protected DataAggregator<DataAuthor> newAggregator(DataAggregatorsApi factory, DataAggregatorParams params) {
        return factory.newAuthorsAggregator(params.getSimilarBoolean());
    }

    @NonNull
    @Override
    protected DataAuthor randomDatum() {
        return RandomAuthor.nextAuthorReview();
    }

    @NonNull
    @Override
    protected DatumAuthor newSimilarDatum(ReviewId reviewId, DataAuthor template) {
        return new DatumAuthor(reviewId, template.getName(), template.getAuthorId());
    }
}
