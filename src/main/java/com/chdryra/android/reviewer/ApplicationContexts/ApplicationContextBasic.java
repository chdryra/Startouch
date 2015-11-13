package com.chdryra.android.reviewer.ApplicationContexts;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.DataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Models.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProvider;
import com.chdryra.android.reviewer.View.Screens.BuilderChildListScreen;
import com.chdryra.android.reviewer.View.Utils.FactoryFileIncrementor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextBasic implements ApplicationContext {
    private Author mAuthor;
    private FactoryReview mFactoryReview;
    private TagsManager mTagsManager;
    private ReviewerDb mReviewerDb;
    private ReviewsProvider mReviewsProvider;
    private SocialPlatformList mSocialPlatforms;
    private BuilderChildListScreen mBuilderChildListScreen;
    private FactoryReviewViewAdapter mFactoryReviewViewAdapter;
    private DataConverters mConverters;
    private DataValidator mDataValidator;
    private FactoryFileIncrementor mFactoryFileIncrementor;

    public ApplicationContextBasic() {

    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    public void setFactoryReview(FactoryReview factoryReview) {
        mFactoryReview = factoryReview;
    }

    public void setTagsManager(TagsManager tagsManager) {
        mTagsManager = tagsManager;
    }

    public void setReviewerDb(ReviewerDb reviewerDb) {
        mReviewerDb = reviewerDb;
    }

    public void setReviewsProvider(ReviewsProvider reviewsProvider) {
        mReviewsProvider = reviewsProvider;
    }

    public void setDataConverters(DataConverters converters) {
        mConverters = converters;
    }

    public void setSocialPlatforms(SocialPlatformList socialPlatforms) {
        mSocialPlatforms = socialPlatforms;
    }

    public void setBuilderChildListScreen(BuilderChildListScreen builderChildListScreen) {
        mBuilderChildListScreen = builderChildListScreen;
    }

    public void setFactoryReviewViewAdapter(FactoryReviewViewAdapter factoryReviewViewAdapter) {
        mFactoryReviewViewAdapter = factoryReviewViewAdapter;
    }

    public void setDataValidator(DataValidator dataValidator) {
        mDataValidator = dataValidator;
    }

    public void setFactoryFileIncrementor(FactoryFileIncrementor factoryFileIncrementor) {
        mFactoryFileIncrementor = factoryFileIncrementor;
    }

    @Override
    public Author getAuthor() {
        return mAuthor;
    }

    @Override
    public ReviewerDb getReviewerDb() {
        return mReviewerDb;
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    @Override
    public SocialPlatformList getSocialPlatformList() {
        return mSocialPlatforms;
    }

    @Override
    public ReviewsProvider getReviewsProvider() {
        return mReviewsProvider;
    }

    @Override
    public DataConverters getDataConverters() {
        return mConverters;
    }

    @Override
    public FactoryReviewViewAdapter getReviewViewAdapterFactory() {
        return mFactoryReviewViewAdapter;
    }

    @Override
    public BuilderChildListScreen getBuilderChildListScreen() {
        return mBuilderChildListScreen;
    }

    @Override
    public FactoryReview getReviewFactory() {
        return mFactoryReview;
    }

    @Override
    public DataValidator getDataValidator() {
        return mDataValidator;
    }

    @Override
    public FactoryFileIncrementor getFileIncrementorFactory() {
        return mFactoryFileIncrementor;
    }
}
