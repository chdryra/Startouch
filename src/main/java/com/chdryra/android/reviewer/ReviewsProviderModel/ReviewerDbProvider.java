package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbProvider implements ReviewsProvider, ReviewerDb.ReviewerDbObserver {
    private ReviewerDb mDatabase;
    private ArrayList<ReviewsProviderObserver> mObservers;

    //Constructors
    public ReviewerDbProvider(ReviewerDb database) {
        mDatabase = database;
        mObservers = new ArrayList<>();
    }

    //Overridden
    @Override
    public Review getReview(ReviewId id) {
        return mDatabase.loadReviewFromDb(id.toString());
    }

    @Override
    public IdableList<Review> getReviews() {
        return mDatabase.loadReviewsFromDb();
    }

    @Override
    public TagsManager getTagsManager() {
        return mDatabase.getTagsManager();
    }

    @Override
    public void registerObserver(ReviewsProviderObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(ReviewsProviderObserver observer) {
        mObservers.remove(observer);
    }

    @Override
    public void onReviewAdded(Review review) {
        for (ReviewsProviderObserver observer : mObservers) {
            observer.onReviewAdded(review);
        }
    }

    @Override
    public void onReviewDeleted(String reviewId) {
        for (ReviewsProviderObserver observer : mObservers) {
            observer.onReviewRemoved(ReviewId.fromString(reviewId));
        }
    }
}