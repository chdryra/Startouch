package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.DataAggregation.Implementation.CanonicalCommentMode;
import com.chdryra.android.reviewer.DataAggregation.Implementation.ComparitorComment;
import com.chdryra.android.reviewer.DataAggregation.Implementation.DifferencePercentage;
import com.chdryra.android.reviewer.DataAggregation.GvDataAggregatorOld;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonical;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCommentList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAggregatorOldTest extends TestCase {
    private static final int NUM = 100;

    @SmallTest
    public void testAggregate() {
        GvCommentList data = GvDataMocker.newCommentList(NUM, true);

        ComparitorComment comparitor = new ComparitorComment();
        DifferencePercentage minDiff = new DifferencePercentage(0.85);
        CanonicalCommentMode canonical = new CanonicalCommentMode();

        GvDataAggregatorOld<GvComment, DifferencePercentage, DifferencePercentage>
                aggregater = new GvDataAggregatorOld<>(comparitor, minDiff, canonical);
        GvCanonicalCollection<GvComment> results = aggregater.aggregate(data);

        assertTrue(results.size() > 0);
        int total = 0;
        for (int i = 0; i < results.size(); ++i) {
            GvCanonical<GvComment> gvCanonical = results.getItem(i);
            GvCommentList values = (GvCommentList) gvCanonical.toList();
            assertEquals(canonical.getCanonical(values), gvCanonical.getCanonical());
            int numVals = values.size();
            assertTrue(numVals > 0 && numVals < NUM);
            total += numVals;
            GvComment reference = values.getItem(0);
            for (int j = 1; j < numVals; ++j) {
                DifferencePercentage diff = comparitor.compare(reference, values.getItem(j));
                assertTrue(diff.lessThanOrEqualTo(minDiff));
            }
        }
        assertEquals(data.size(), total);
    }
}
