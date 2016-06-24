/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

public class NodeLeaf extends ReviewNodeBasic implements ReviewNodeComponent{
    private final ReviewReference mReview;

    public NodeLeaf(ReviewReference review) {
        mReview = review;
    }

    @Override
    public boolean addChild(ReviewNodeComponent childNode) {
        return false;
    }

    @Override
    public void removeChild(ReviewId reviewId) {

    }

    @Override
    public void getCovers(CoversCallback callback) {
        mReview.getCovers(callback);
    }

    @Override
    public void getTags(TagsCallback callback) {
        mReview.getTags(callback);
    }

    @Override
    public void getCriteria(CriteriaCallback callback) {
        mReview.getCriteria(callback);
    }

    @Override
    public void getImages(ImagesCallback callback) {
        mReview.getImages(callback);
    }

    @Override
    public void getComments(CommentsCallback callback) {
        mReview.getComments(callback);
    }

    @Override
    public void getLocations(LocationsCallback callback) {
        mReview.getLocations(callback);
    }

    @Override
    public void getFacts(FactsCallback callback) {
        mReview.getFacts(callback);
    }

    @Override
    public void getNumTags(TagsSizeCallback callback) {
        mReview.getNumTags(callback);
    }

    @Override
    public void getNumCriteria(CriteriaSizeCallback callback) {
        mReview.getNumCriteria(callback);
    }

    @Override
    public void getNumImages(ImagesSizeCallback callback) {
        mReview.getNumImages(callback);
    }

    @Override
    public void getNumComments(CommentsSizeCallback callback) {
        mReview.getNumComments(callback);
    }

    @Override
    public void getNumLocations(LocationsSizeCallback callback) {
        mReview.getNumLocations(callback);
    }

    @Override
    public void getNumFacts(FactsSizeCallback callback) {
        mReview.getNumFacts(callback);
    }

    @Override
    public void bind(ReferenceBinders.CoversBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.TagsBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.CriteriaBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.ImagesBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.CommentsBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.LocationsBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.FactsBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bindToTags(ReferenceBinders.SizeBinder binder) {
        mReview.bindToTags(binder);
    }

    @Override
    public void bindToCriteria(ReferenceBinders.SizeBinder binder) {
        mReview.bindToCriteria(binder);
    }

    @Override
    public void bindToImages(ReferenceBinders.SizeBinder binder) {
        mReview.bindToImages(binder);
    }

    @Override
    public void bindToComments(ReferenceBinders.SizeBinder binder) {
        mReview.bindToComments(binder);
    }

    @Override
    public void bindToLocations(ReferenceBinders.SizeBinder binder) {
        mReview.bindToLocations(binder);
    }

    @Override
    public void bindToFacts(ReferenceBinders.SizeBinder binder) {
        mReview.bindToFacts(binder);
    }

    @Override
    public void unbind(ReferenceBinders.CoversBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.TagsBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.CriteriaBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.ImagesBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.CommentsBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.LocationsBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.FactsBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbindFromTags(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromTags(binder);
    }

    @Override
    public void unbindFromCriteria(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromCriteria(binder);
    }

    @Override
    public void unbindFromImages(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromImages(binder);
    }

    @Override
    public void unbindFromComments(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromComments(binder);
    }

    @Override
    public void unbindFromLocations(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromLocations(binder);
    }

    @Override
    public void unbindFromFacts(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromFacts(binder);
    }

    @Override
    public void dereference(DereferenceCallback callback) {
        mReview.dereference(callback);
    }

    @Override
    public boolean isValid() {
        return mReview.isValid();
    }

    @Override
    public ReviewNode getChild(ReviewId reviewId) {
        return null;
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return false;
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        return new MdDataList<>(getReviewId());
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return false;
    }

    @Override
    public ReviewId getReviewId() {
        return mReview.getReviewId();
    }

    @Override
    public DataSubject getSubject() {
        return mReview.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mReview.getRating();
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mReview.getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public ReviewNode asNode() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeLeaf)) return false;
        if (!super.equals(o)) return false;

        NodeLeaf nodeLeaf = (NodeLeaf) o;

        return mReview.equals(nodeLeaf.mReview);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mReview.hashCode();
        return result;
    }
}
