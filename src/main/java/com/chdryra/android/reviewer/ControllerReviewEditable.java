/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Expands on {@link ControllerReview} to include setters on {@link ReviewEditable}.
 */
public class ControllerReviewEditable extends ControllerReview<ReviewEditable> {
    public ControllerReviewEditable(ReviewEditable review) {
        super(review);
    }

    public void setSubject(String subject) {
        getControlledReview().setSubject(subject);
    }

    public void setRating(float rating) {
        getControlledReview().setRating(rating);
    }

    public void setData(GVDataList data) {
        GVDataList.GvType dataType = data.getGvType();
        ReviewEditable r = getControlledReview();
        if (dataType == GVDataList.GvType.COMMENTS) {
            r.setComments(data);
        } else if (dataType == GVDataList.GvType.IMAGES) {
            r.setImages(data);
        } else if (dataType == GVDataList.GvType.FACTS) {
            r.setFacts(data);
        } else if (dataType == GVDataList.GvType.URLS) {
            r.setUrls(data);
        } else if (dataType == GVDataList.GvType.LOCATIONS) {
            r.setLocations(data);
        } else if (dataType == GVDataList.GvType.TAGS) {
            setTags((GVTagList) data);
        }
    }
}
