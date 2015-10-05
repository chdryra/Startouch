package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderFacts extends ExpanderToReviews<GvCanonical> {
    public ExpanderFacts(Context context) {
        super(context);
    }

    @Override
    public boolean isExpandable(GvCanonical datum) {
        return datum.getGvDataType() == GvFactList.GvFact.TYPE && super.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvCanonical datum) {
        if (!isExpandable(datum)) {
            return null;
        } else if(datum.size() == 1) {
            return super.expandGridCell(datum);
        }

        GvFactList.GvFact canonical = (GvFactList.GvFact) datum.getCanonical();
        return FactoryReviewViewAdapter.newExpandToReviewsAdapter(getContext(), datum.toList(),
                canonical.getLabel());
    }
}
