package com.chdryra.android.reviewer.Model.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.VerboseIdableCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 31/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsFeedMutable extends ReviewsRepositoryMutable, ReviewsFeed {
    @Override
    DataAuthor getAuthor();

    @Override
    ItemTagCollection getTags(String reviewId);

    @Override
    Review getReview(VerboseDataReview datum);

    @Override
    Review createMetaReview(VerboseIdableCollection data, String subject);

    @Override
    Review asMetaReview(VerboseDataReview datum, String subject);

    @Override
    Review createFlattenedMetaReview(VerboseIdableCollection data, String subject);

    @Override
    void addReview(Review review);

    @Override
    void deleteReview(String reviewId);

    @Override
    Review getReview(String id);

    @Override
    Iterable<Review> getReviews();

    @Override
    TagsManager getTagsManager();

    @Override
    void registerObserver(ReviewsProviderObserver observer);

    @Override
    void unregisterObserver(ReviewsProviderObserver observer);
}