package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import com.chdryra.android.reviewer.Interfaces.Data.DataAuthor;
import com.chdryra.android.reviewer.Interfaces.Data.DataDate;

/**
 * Created by: Rizwan Choudrey
 * On: 18/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewPublisher {
    private DataAuthor mAuthor;
    private DataDate mDate;
    private int mIndex;

    //Constructors
    public ReviewPublisher(DataAuthor author, DataDate date) {
        mAuthor = author;
        mDate = date;
    }

    //public methods
    public DataAuthor getAuthor() {
        return mAuthor;
    }

    public DataDate getDate() {
        return mDate;
    }

    public int getIncrement() {
        return mIndex++;
    }
}