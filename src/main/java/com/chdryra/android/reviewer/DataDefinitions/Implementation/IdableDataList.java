package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class IdableDataList<T extends HasReviewId> extends AbstractCollection<T> implements IdableList<T> {
    private ReviewId mReviewId;
    private ArrayList<T> mData;

    public IdableDataList(@Nullable ReviewId reviewId) {
        mReviewId = reviewId;
        mData = new ArrayList<>();
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public int size() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public boolean add(T datum) {
        return mData.add(datum);
    }


    @Override
    public Iterator<T> iterator() {
        return mData.iterator();
    }
}
