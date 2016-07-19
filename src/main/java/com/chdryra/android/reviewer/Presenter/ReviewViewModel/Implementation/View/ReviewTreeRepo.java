/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NodeInternal;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeRepo extends NodeInternal implements ReviewsSubscriber, ReviewNode {
    private ReferencesRepository mRepo;

    public ReviewTreeRepo(DataReviewInfo meta,
                          ReferencesRepository repo,
                          FactoryReviewNode nodeFactory) {
        super(meta, nodeFactory);
        mRepo = repo;
        mRepo.bind(this);
    }

    @Override
    public String getSubscriberId() {
        return getReviewId().toString();
    }

    @Override
    public void onReviewAdded(ReviewReference reference) {
        addChild(reference);
    }

    @Override
    public void onReviewEdited(ReviewReference reference) {

    }

    @Override
    public void onReviewRemoved(ReviewReference reference) {
        removeChild(reference.getReviewId());
    }

    private void addChild(ReviewReference review) {
        addChild(getNodeFactory().createLeafNode(review));
    }

    public void detachFromRepo() {
        mRepo.unbind(this);
        for(ReviewNode child : getChildren()) {
            removeChild(child.getReviewId());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewTreeRepo)) return false;
        if (!super.equals(o)) return false;

        ReviewTreeRepo that = (ReviewTreeRepo) o;

        return mRepo.equals(that.mRepo);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mRepo.hashCode();
        return result;
    }
}
