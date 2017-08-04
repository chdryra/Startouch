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
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.AsyncUtils.DelayTask;
import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DefaultNamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .CacheVhReviewSelected;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils
        .DataFormatter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RatingFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhReviewSelected extends ViewHolderBasic implements ReviewSelector
        .ReviewSelectorCallback, VhNode, ReviewReference.ReviewReferenceObserver {
    private static final int LAYOUT = R.layout.grid_cell_review_abstract;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating_number;
    private static final int IMAGE = R.id.review_image;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS = R.id.review_tags;
    private static final int STAMP = R.id.review_stamp;
    private static final long WAIT_TIME = 150L;

    private final AuthorsRepository mAuthorsRepo;
    private final ReviewSelector mSelector;
    private final CacheVhReviewSelected mCache;
    private final TagsBinder mTagsBinder;
    private final CommentsBinder mCommentsBinder;
    private final LocationsBinder mLocationsBinder;
    private NameBinder mNameBinder;
    private CoverBinder mCoverBinder;
    private boolean mSelecting = false;

    private TextView mSubject;
    private TextView mRating;
    private ImageView mImage;
    private TextView mHeadline;
    private TextView mTags;
    private TextView mFooter;
    private ReviewId mNodeId;
    private ReviewReference mReview;
    private NamedAuthor mAuthor;
    private DataDate mDate;
    private String mLocation;

    private int mNumReturned = 0;
    private boolean mCancelBinding = false;
    private DelaySelectTask mBindingTask;
    private Bitmap mCover;

    public VhReviewSelected(AuthorsRepository authorsRepo,
                            ReviewSelector selector,
                            CacheVhReviewSelected cache) {
        super(LAYOUT, new int[]{LAYOUT, SUBJECT, RATING, IMAGE, HEADLINE, TAGS, STAMP});
        mAuthorsRepo = authorsRepo;
        mSelector = selector;
        mCache = cache;
        mCache.registerObserver(this);

        mCommentsBinder = new CommentsBinder();
        mTagsBinder = new TagsBinder();
        mLocationsBinder = new LocationsBinder();
    }

    @Override
    public boolean isBoundTo(ReviewNode node) {
        return mNodeId != null && mNodeId.equals(node.getReviewId());
    }

    @Override
    public void unbind() {
        if (mReview == null) return;
        mCancelBinding = true;
        if (mCoverBinder != null) unbindFromCover();
        mReview.getComments().unbindFromValue(mCommentsBinder);
        mReview.getLocations().unbindFromValue(mLocationsBinder);
        mReview.getTags().unbindFromValue(mTagsBinder);
        mReview.unregisterObserver(this);
        mAuthorsRepo.getReference(mReview.getAuthorId()).unbindFromValue(mNameBinder);
        mSelector.unregister(mNodeId);
        mReview = null;
        mNodeId = null;
        mCache.unregisterObserver(this);
    }

    @Override
    public void updateView(ViewHolderData data) {
        setViewsIfNecessary();
        setNode(((GvNode) data));
    }

    @Override
    public void onReviewSelected(@Nullable ReviewReference review) {
        mSelecting = false;
        if (review != null) {
            if (notReinitialising()) bindToReview(review);
        } else {
            unbind();
        }
    }

    @Override
    public void onSubjectChanged(DataSubject newSubject) {
        mCache.addSubject(newSubject);
        mSubject.setText(newSubject.getSubject());
    }

    @Override
    public void onRatingChanged(DataRating newRating) {
        mCache.addRating(newRating);
        mRating.setText(RatingFormatter.upToTwoSignificantDigits(newRating.getRating()));
    }

    @Override
    public void refresh(ReviewNode node) {
        if (mBindingTask != null) mBindingTask.cancel(true);
        mBindingTask = new DelaySelectTask(node);
        mBindingTask.execute(WAIT_TIME);
    }

    private void unbindFromCover() {
        mReview.getCover().unbindFromValue(mCoverBinder);
        mCoverBinder.unbind();
        mCoverBinder = null;
    }

    private void setViewsIfNecessary() {
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (TextView) getView(RATING);
        if (mImage == null) mImage = (ImageView) getView(IMAGE);
        if (mHeadline == null) mHeadline = (TextView) getView(HEADLINE);
        if (mTags == null) mTags = (TextView) getView(TAGS);
        if (mFooter == null) mFooter = (TextView) getView(STAMP);
    }

    private void setNode(GvNode node) {
        if (isBoundTo(node.getNode())) return;
        unbind();
        mNodeId = node.getReviewId();
        initialiseView(node);
        node.setViewHolder(this);
        refresh(node.getNode());
    }

    private void selectAndBind(ReviewNode node) {
        mBindingTask = null;
        mSelector.select(node, this);
    }

    private void initialiseView(GvNode node) {
        ReviewId reviewId = node.getReviewId();
        mSubject.setText(mCache.containsSubject(reviewId) ?
                mCache.getSubject(reviewId).getSubject() : Strings.EditTexts.FETCHING);
        mRating.setText(RatingFormatter.upToTwoSignificantDigits(mCache.containsRating(reviewId) ?
                mCache.getRating(reviewId).getRating() : 0f));

        setCover(mCache.containsCover(reviewId) ? mCache.getCover(reviewId) : null);
        setHeadline(mCache.containsComments(reviewId) ? mCache.getComments(reviewId) : new
                IdableDataList<DataComment>(reviewId));
        setTags(mCache.containsTags(reviewId) ? mCache.getTags(reviewId) : new
                IdableDataList<DataTag>(reviewId));
        setLocations(mCache.containsLocations(reviewId) ? mCache.getLocations(reviewId) : new
                IdableDataList<DataLocation>(reviewId));
        setAuthor(mCache.containsAuthor(reviewId) ? mCache.getAuthor(reviewId) : new
                DefaultNamedAuthor());
        setDate(mCache.containsDate(reviewId) ? mCache.getDate(reviewId) : null);
    }

    private void newFooter() {
        newFooter(mDate);
    }

    private void newFooter(@Nullable DataDate publishDate) {
        String date = publishDate != null ? ReviewStamp.toReadableDate(publishDate) : "";
        String name = mAuthor != null ? mAuthor.getName() : "";
        String text = name + " " + date + (validateString(mLocation) ? " @" + mLocation : "");
        mFooter.setText(text);
    }

    private String getTagString(IdableList<? extends DataTag> tags, int maxTags) {
        String ignoreTag = TextUtils.toTag(mSubject.getText().toString());
        return DataFormatter.formatTags(tags, maxTags, ignoreTag);
    }

    private boolean validateString(@Nullable String string) {
        return string != null && string.length() > 0;
    }

    private void setAuthor(@Nullable NamedAuthor author) {
        mAuthor = author;
        newFooter();
    }

    private void setDate(@Nullable DataDate publishDate) {
        mDate = publishDate;
        newFooter();
    }

    private void setHeadline(IdableList<DataComment> value) {
        String headline = DataFormatter.getHeadlineQuote(value);
        if (headline.length() == 0) headline = Strings.Formatted.NO_COMMENT;
        mHeadline.setText(headline);
    }

    private void setTags(IdableList<? extends DataTag> tags) {
        int i = tags.size();
        String tagsString = getTagString(tags, i--);
        while (i > -1 && TextUtils.isTooLargeForTextView(mTags, tagsString)) {
            tagsString = getTagString(tags, i--);
        }

        mTags.setText(tagsString);
    }

    private void setLocations(IdableList<? extends DataLocation> value) {
        mLocation = DataFormatter.formatLocationsShort(value);
        newFooter();
    }

    private void bindToReview(ReviewReference review) {
        mReview = review;

        setDate(mReview.getPublishDate());
        mCache.addDate(mDate);
        onSubjectChanged(mReview.getSubject());
        onRatingChanged(mReview.getRating());

        mReview.registerObserver(this);

        mNumReturned = 0;
        mReview.getComments().bindToValue(mCommentsBinder);
        mReview.getLocations().bindToValue(mLocationsBinder);
        mReview.getTags().bindToValue(mTagsBinder);
        mNameBinder = new NameBinder(mReview.getReviewId());
        mAuthorsRepo.getReference(mReview.getAuthorId()).bindToValue(mNameBinder);
    }

    private void returned() {
        if (++mNumReturned == 4 && mReview != null && notReinitialising()) {
            bindToCover();
        }
    }

    private void bindToCover() {
        mCoverBinder = new CoverBinder(mReview.getReviewId(), mAuthorsRepo.getProfile(mReview
                .getAuthorId()).getProfileImage());
        mReview.getCover().bindToValue(mCoverBinder);
    }

    private void setCover(@Nullable Bitmap cover) {
        if (mCover != null && mCover.sameAs(cover)) return;
        mCover = cover;
        mImage.setImageBitmap(cover);
    }

    private boolean notReinitialising() {
        return !mCancelBinding && !mSelecting;
    }

    //hacky way of avoiding downloads spooling up if view is being scrolled fast.
    private class DelaySelectTask extends DelayTask {
        private final ReviewNode mNode;

        private DelaySelectTask(ReviewNode node) {
            mNode = node;
            mSelecting = true;
            mCancelBinding = false;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!mCancelBinding) selectAndBind(mNode);
        }
    }

    private class NameBinder implements ReferenceBinder<NamedAuthor> {
        private final ReviewId mReviewId;

        private NameBinder(ReviewId reviewId) {
            mReviewId = reviewId;
        }

        @Override
        public void onInvalidated(DataReference<NamedAuthor> reference) {
            mCache.removeAuthor(mReviewId);
            returned();
            setAuthor(null);
        }

        @Override
        public void onReferenceValue(NamedAuthor value) {
            boolean added = mCache.addAuthor(mReviewId, value);
            returned();
            if (notReinitialising() && added) setAuthor(value);
        }
    }

    private class TagsBinder extends ListBinder<DataTag> {
        @Override
        protected void onList(IdableList<DataTag> data) {
            boolean newData = true;
            if (data.size() > 0) {
                newData = mCache.addTags(data);
            } else {
                mCache.removeTags(data.getReviewId());
            }
            if (newData) setTags(data);
        }
    }

    private class LocationsBinder extends ListBinder<DataLocation> {
        @Override
        protected void onList(IdableList<DataLocation> data) {
            boolean newData = true;
            if (data.size() > 0) {
                newData = mCache.addLocations(data);
            } else {
                mCache.removeLocations(data.getReviewId());
            }
            if (newData) setLocations(data);
        }
    }

    private class CommentsBinder extends ListBinder<DataComment> {
        @Override
        protected void onList(IdableList<DataComment> data) {
            boolean newData = true;
            if (data.size() > 0) {
                newData = mCache.addComments(data);
            } else {
                mCache.removeComments(data.getReviewId());
            }
            if (newData) setHeadline(data);
        }
    }

    private abstract class ListBinder<T extends HasReviewId> implements
            ReferenceBinder<IdableList<T>> {
        protected abstract void onList(IdableList<T> data);

        @Override
        public void onReferenceValue(IdableList<T> value) {
            returned();
            if (notReinitialising()) onList(value);
        }

        @Override
        public void onInvalidated(DataReference<IdableList<T>> reference) {
            returned();
            onList(new IdableDataList<T>(mNodeId));
        }
    }

    private class CoverBinder implements ReferenceBinder<DataImage> {
        private final ReviewId mReviewId;
        private final DataReference<ProfileImage> mProfileImage;
        private ProfileImageBinder mProfileImageBinder;
        private boolean mCancel = false;

        private CoverBinder(ReviewId reviewId, DataReference<ProfileImage> profileImage) {
            mReviewId = reviewId;
            mProfileImage = profileImage;
            mCancel = false;
        }

        public void unbind() {
            mCancel = true;
            if (mProfileImageBinder != null) mProfileImage.unbindFromValue(mProfileImageBinder);
        }

        @Override
        public void onInvalidated(DataReference<DataImage> reference) {
            bindToProfileImage();
        }

        @Override
        public void onReferenceValue(DataImage value) {
            Bitmap bitmap = value.getBitmap();
            if (bitmap == null) {
                if (!mCancel) bindToProfileImage();
            } else {
                unbindFromProfile();
                notifyOnCover(bitmap);
            }
        }

        private void unbindFromProfile() {
            if (mProfileImage != null) {
                mProfileImage.unbindFromValue(mProfileImageBinder);
                mProfileImageBinder = null;
            }
        }

        private void bindToProfileImage() {
            if (mProfileImageBinder == null) {
                notifyOnCover(null);
                mProfileImageBinder = new ProfileImageBinder();
                mProfileImage.bindToValue(mProfileImageBinder);
            }
        }

        private void notifyOnCover(@Nullable Bitmap bitmap) {
            if (bitmap == null) {
                mCache.removeCover(mReviewId);
            } else {
                mCache.addCover(mReviewId, bitmap);
            }
            if (!mCancel) setCover(bitmap);
        }

        private class ProfileImageBinder implements ReferenceBinder<ProfileImage> {
            @Override
            public void onInvalidated(DataReference<ProfileImage> reference) {
                notifyOnCover(null);
            }

            @Override
            public void onReferenceValue(ProfileImage value) {
                notifyOnCover(value.getBitmap());
            }
        }
    }
}