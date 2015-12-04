package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class MdCriterion implements DataCriterionReview {
    private MdReviewId mParentId;
    private Review mCriterion;

    //Constructors
    public MdCriterion(MdReviewId parent, Review criterion) {
        mParentId = parent;
        mCriterion = criterion;
    }

    //Overridden
    @Override
    public String getSubject() {
        return mCriterion.getSubject().getSubject();
    }

    @Override
    public float getRating() {
        return mCriterion.getRating().getRating();
    }

    @Override
    public Review getReview() {
        return mCriterion;
    }

    @Override
    public String getReviewId() {
        return mParentId.toString();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return mCriterion != null && mParentId != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdCriterion)) return false;

        MdCriterion that = (MdCriterion) o;

        if (!mParentId.equals(that.mParentId)) return false;
        return !(mCriterion != null ? !mCriterion.equals(that.mCriterion) : that.mCriterion
                != null);

    }

    @Override
    public int hashCode() {
        int result = mParentId.hashCode();
        result = 31 * result + (mCriterion != null ? mCriterion.hashCode() : 0);
        return result;
    }
}