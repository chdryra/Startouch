/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterAuthors;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocationList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewAsync;
import com.chdryra.android.reviewer.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhReviewAsync extends ViewHolderBasic implements CallbackRepository {
    private static final int LAYOUT = R.layout.grid_cell_review_overview;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating;
    private static final int IMAGE = R.id.review_image;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS = R.id.review_tags;
    private static final int PUBLISH = R.id.review_publish_data;

    private TagsManager mTagsManager;
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;
    private GvConverterAuthors mConverterAuthor;

    private TextView mSubject;
    private RatingBar mRating;
    private ImageView mImage;
    private TextView mHeadline;
    private TextView mTags;
    private TextView mPublishDate;

    public VhReviewAsync(TagsManager tagsManager,
                         GvConverterImages converterImages,
                         GvConverterComments converterComments,
                         GvConverterLocations converterLocations,
                         GvConverterAuthors converterAuthor) {
        super(LAYOUT, new int[]{LAYOUT, SUBJECT, RATING, IMAGE, HEADLINE, TAGS, PUBLISH});
        mTagsManager = tagsManager;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
        mConverterAuthor = converterAuthor;
    }

    @Override
    public void updateView(ViewHolderData data) {
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (RatingBar) getView(RATING);
        if (mImage == null) mImage = (ImageView) getView(IMAGE);
        if (mHeadline == null) mHeadline = (TextView) getView(HEADLINE);
        if (mTags == null) mTags = (TextView) getView(TAGS);
        if (mPublishDate == null) mPublishDate = (TextView) getView(PUBLISH);


        GvReviewAsync review = (GvReviewAsync) data;
        mSubject.setText(review.getSubject());
        mRating.setRating(review.getRating());
        mPublishDate.setText("loading...");
        review.getReview(this);
    }

    @Override
    public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
        if (review == null || result.isError()) return;

        mSubject.setText(review.getSubject().getSubject());
        mRating.setRating(review.getRating().getRating());

        String author = mConverterAuthor.convert(review.getAuthor()).getName();
        String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format
                (new Date(review.getPublishDate().getTime()));
        String location = getLocationString(review);
        String locationStem = validateString(location) ? " @" + location : "";
        //TODO sort this out with resource strings with placeholders
        mPublishDate.setText(date + " by " + author + locationStem);

        ItemTagCollection tags = mTagsManager.getTags(review.getReviewId().toString());
        mTags.setText(getTagString(tags.toStringArray()));

        GvImageList covers = mConverterImages.convert(review.getCovers());
        Bitmap cover = covers.size() > 0 ? covers.getRandomCover().getBitmap() : null;
        mImage.setImageBitmap(cover);

        GvCommentList comments = mConverterComments.convert(review.getComments());
        GvCommentList headlines = comments.getHeadlines();
        String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() : null;
        if (validateString(headline)) {
            //TODO sort this out with resource strings with placeholders
            mHeadline.setText("\"" + headline + "\"");
        } else {
            mHeadline.setText("");
        }
    }

    @Override
    public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {

    }

    private String getLocationString(Review review) {
        GvLocationList locations = mConverterLocations.convert(review.getLocations());
        ArrayList<String> locationNames = new ArrayList<>();
        for (GvLocation location : locations) {
            locationNames.add(location.getShortenedName());
        }
        String location = "";
        int locs = locationNames.size();
        if (locs > 0) {
            location = locationNames.get(0);
            if (locs > 1) {
                String loc = locs == 2 ? " loc" : " locs";
                location += " +" + String.valueOf(locationNames.size() - 1) + loc;
            }
        }

        return location;
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

    private boolean validateString(@Nullable String string) {
        return string != null && string.length() > 0;
    }
}
