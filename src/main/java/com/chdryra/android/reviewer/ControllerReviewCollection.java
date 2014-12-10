/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Similar to {@link ControllerReview} but for {@link RCollectionReview} data.
 */
public class ControllerReviewCollection<T extends Review> {
    private RCollectionReview<T>              mReviews;
    private HashMap<String, ControllerReview> mControllers;

    public ControllerReviewCollection(RCollectionReview<T> reviews) {
        init(reviews);
    }

    public void addReview(T review) {
        mReviews.add(review);
    }

    public GVDataList toGridViewable(boolean publishedOnly) {
        return publishedOnly ? toGridViewablePublished() : toGridViewableAll();
    }

    protected void init(RCollectionReview<T> reviews) {
        mReviews = reviews;
        mControllers = new HashMap<>();
    }

    private GVDataList toGridViewableAll() {
        GVSubjectRatingList data = new GVSubjectRatingList();
        for (Review r : mReviews) {
            data.add(r.getSubject().get(), r.getRating().get());
        }

        return data;
    }

    private GVDataList toGridViewablePublished() {
        GVOverviewList data = new GVOverviewList();
        for (Review r : mReviews) {
            if (!r.isPublished()) continue;
            ControllerReview c = getControllerFor(r.getId().toString());

            GVImageList images = (GVImageList) c.getData(GVDataList.GvType.IMAGES);
            GVCommentList comments = (GVCommentList) c.getData(GVDataList.GvType.COMMENTS);
            GVLocationList locations = (GVLocationList) c.getData(GVDataList.GvType
                    .LOCATIONS);

            Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
            String headline = comments.size() > 0 ? comments.getItem(0).getCommentHeadline() : null;
            String location = locations.size() > 0 ? locations.getItem(0).getName() : null;

            data.add(c.getId(), c.getSubject(), c.getRating(), cover, headline, location,
                    c.getAuthor(), c.getPublishDate());
        }

        return data;
    }

    private T get(String id) {
        return mReviews.get(RDId.generateId(id));
    }

    private ControllerReview getControllerFor(String id) {
        if (mControllers.get(id) == null) {
            mControllers.put(id, new ControllerReview<T>(get(id)));
        }

        return mControllers.get(id);
    }
}
