/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.ArrayList;

public class NodeInternal2 extends ReviewNodeComponentBasic implements ReviewNode.NodeObserver{
    private DataReviewInfo mMeta;
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryNodeTraverser mTraverserFactory;

    private ArrayList<NodeObserver> mObservers;
    private MdDataList<ReviewNodeComponent> mChildren;

    public NodeInternal2(DataReviewInfo meta, FactoryVisitorReviewNode visitorFactory,
                         FactoryNodeTraverser traverserFactory) {
        mMeta = meta;
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;
        mChildren = new MdDataList<>(getReviewId());
        mObservers = new ArrayList<>();
    }

    @Override
    public ReviewListReference<ReviewReference> getReviews() {
        return null;
    }

    @Override
    public ReviewListReference<DataSubject> getSubjects() {
        return null;
    }

    @Override
    public ReviewListReference<DataAuthorId> getAuthorIds() {
        return null;
    }

    @Override
    public ReviewListReference<DataDate> getDates() {
        return null;
    }

    @Override
    public ReviewItemReference<DataSize> getNumReviews() {
        return null;
    }

    @Override
    public ReviewItemReference<DataSize> getNumSubjects() {
        return null;
    }

    @Override
    public ReviewItemReference<DataSize> getNumAuthors() {
        return null;
    }

    @Override
    public ReviewItemReference<DataSize> getNumDates() {
        return null;
    }

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return null;
    }

    @Override
    public ReviewListReference<DataCriterion> getCriteria() {
        return null;
    }

    @Override
    public ReviewListReference<DataComment> getComments() {
        return null;
    }

    @Override
    public ReviewListReference<DataFact> getFacts() {
        return null;
    }

    @Override
    public ReviewListReference<DataImage> getImages() {
        return null;
    }

    @Override
    public ReviewListReference<DataLocation> getLocations() {
        return null;
    }

    @Override
    public ReviewListReference<DataTag> getTags() {
        return null;
    }

    @Override
    public void unregisterObserver(NodeObserver binder) {
        if (mObservers.contains(binder)) mObservers.remove(binder);
    }

    @Override
    public void registerObserver(NodeObserver binder) {
        if (!mObservers.contains(binder)) mObservers.add(binder);
    }

    @Override
    public void addChild(ReviewNodeComponent child) {
        if (mChildren.containsId(child.getReviewId())) return;

        mChildren.add(child);
        child.setParent(this);

        registerWithChild(child);

        notifyOnChildAdded(child);
    }

    @Override
    public void addChildren(Iterable<ReviewNodeComponent> children) {
        for(ReviewNodeComponent child : children) {
            if (mChildren.containsId(child.getReviewId())) continue;
            mChildren.add(child);
            child.setParent(this);
        }

        for(ReviewNodeComponent child : children) {
            registerWithChild(child);
        }

        for(ReviewNodeComponent child : children) {
            notifyOnChildAdded(child);
        }
    }

    @Override
    public void removeChild(ReviewId reviewId) {
        if (!hasChild(reviewId)) return;

        ReviewNodeComponent childNode = mChildren.getItem(reviewId);
        mChildren.remove(reviewId);
        if (childNode != null) childNode.setParent(null);

        unregisterWithChild(reviewId);

        if (childNode != null) notifyOnChildRemoved(childNode);
    }

    @Override
    public ReviewNode getChild(ReviewId reviewId) {
        return mChildren.getItem(reviewId);
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return mChildren.containsId(reviewId);
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        MdDataList<ReviewNode> children = new MdDataList<>(mChildren.getReviewId());
        children.addAll(mChildren);
        return children;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return true;
    }

    //-------------Review Reference methods--------------
    @Override
    public ReviewId getReviewId() {
        return mMeta.getReviewId();
    }

    @Override
    public DataSubject getSubject() {
        return mMeta.getSubject();
    }

    @Override
    public DataRating getRating() {
        return getAverageRating();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mMeta.getAuthorId();
    }

    @Override
    public DataDate getPublishDate() {
        return mMeta.getPublishDate();
    }

    @NonNull
    private DataRating getAverageRating() {
        float rating = 0f;
        int weight = 0;
        for (ReviewNode child : getChildren()) {
            DataRating childRating = child.getRating();
            rating += childRating.getRating() * childRating.getRatingWeight();
            weight += childRating.getRatingWeight();
        }
        if (weight > 0) rating /= weight;
        return new MdRating(new MdReviewId(getReviewId()), rating, weight);
    }

    private void unregisterWithChild(ReviewId childId) {
        ReviewNode child = getChild(childId);
        if(child != null) child.unregisterObserver(this);
    }

    private void registerWithChild(ReviewNode child) {
        child.registerObserver(this);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        notifyOnNodeChanged();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        notifyOnNodeChanged();
    }

    @Override
    public void onNodeChanged() {
        notifyOnNodeChanged();
    }
}
