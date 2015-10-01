/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 May, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.R;

import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhReviewOverview extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.grid_cell_review_overview2;
    private static final int SUBJECT  = R.id.review_subject;
    private static final int RATING = R.id.review_rating;
    private static final int IMAGE    = R.id.review_image;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS   = R.id.review_tags;
    private static final int PUBLISH  = R.id.review_publish_data;

    private TextView  mSubject;
    private RatingBar mRating;
    private ImageView mImage;
    private TextView  mHeadline;
    private TextView mTags;
    private TextView  mPublishDate;

    VhReviewOverview() {
        super(LAYOUT, new int[]{LAYOUT, SUBJECT, RATING, IMAGE, HEADLINE, TAGS, PUBLISH});
    }

    @Override
    public void updateView(ViewHolderData data) {
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (RatingBar) getView(RATING);
        if (mImage == null) mImage = (ImageView) getView(IMAGE);
        if (mHeadline == null) mHeadline = (TextView) getView(HEADLINE);
        if (mTags == null) mTags = (TextView) getView(TAGS);
        if (mPublishDate == null) mPublishDate = (TextView) getView(PUBLISH);

        GvReviewOverviewList.GvReviewOverview review = (GvReviewOverviewList.GvReviewOverview) data;
        if (review == null || !review.isValidForDisplay()) return;

        mSubject.setText(review.getSubject());
        mRating.setRating(review.getRating());
        mImage.setImageBitmap(review.getCoverImage());

        String author = review.getAuthor().getName();
        String date = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                DateFormat.SHORT).format(review.getPublishDate());
        String location = review.getLocationString();
        String locationStem = DataValidator.validateString(location) ? " @" + location : "";
        mPublishDate.setText(date + " by " + author + locationStem);

        mTags.setText(getTagString(review.getTags()));

        String headline = review.getHeadline();
        if (DataValidator.validateString(headline)) {
            mHeadline.setText("\"" + headline + "\"");
        } else {
            mHeadline.setText("");
        }
    }

    private String getTagString(ArrayList<String> tags) {
        int i = tags.size();
        String tagsString = getTagString(tags, i--);
        while (i > -1 && TextUtils.isTooLargeForTextView(mTags, tagsString)) {
            tagsString = getTagString(tags, i--);
        }

        return tagsString;
    }

    private String getTagString(ArrayList<String> tags, int maxTags) {
        String tagsString = "";
        int size = Math.min(tags.size(), Math.max(maxTags, tags.size()));
        int diff = tags.size() - size;
        int i = 0;
        while (i < size) {
            tagsString += "#" + tags.get(i) + " ";
            ++i;
        }

        if (diff > 0) tagsString += "+ " + String.valueOf(diff) + "#";

        return tagsString.trim();
    }
}

