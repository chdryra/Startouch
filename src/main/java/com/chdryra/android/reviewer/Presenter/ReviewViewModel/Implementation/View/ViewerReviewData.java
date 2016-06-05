/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerReviewData implements GridDataViewer<GvData> {
    private static final GvDataType<GvData> TYPE = GvList.TYPE;

    private ReviewNode mNode;
    private ConverterGv mConverter;
    private TagsManager mTagsManager;
    private FactoryReviewViewAdapter mAdapterFactory;
    private GvList mCache;

    public ViewerReviewData(ReviewNode node,
                     ConverterGv converter,
                     TagsManager tagsManager,
                     FactoryReviewViewAdapter adapterFactory) {
        mNode = node;
        mConverter = converter;
        mTagsManager = tagsManager;
        mAdapterFactory = adapterFactory;
    }

    protected ReviewNode getReviewNode() {
        return mNode;
    }

    protected FactoryReviewViewAdapter getAdapterFactory() {
        return mAdapterFactory;
    }

    protected ConverterGv getConverter() {
        return mConverter;
    }

    protected TagsManager getTagsManager() {
        return mTagsManager;
    }

    protected GvList makeGridData() {
        Review review = mNode.getReview();
        ReviewId reviewId = review.getReviewId();
        GvReviewId id = new GvReviewId(reviewId);

        GvList data = new GvList(id);
        data.add(mConverter.toGvTagList(mTagsManager.getTags(reviewId.toString()), reviewId));
        data.add(mConverter.toGvCriterionList(review.getCriteria()));
        data.add(mConverter.toGvImageList(review.getImages()));
        data.add(mConverter.toGvCommentList(review.getComments()));
        data.add(mConverter.toGvLocationList(review.getLocations()));
        data.add(mConverter.toGvFactList(review.getFacts()));

        return data;
    }

    @Override
    public GvDataType<GvData> getGvDataType() {
        return TYPE;
    }

    @Override
    public GvList getGridData() {
        GvList data = makeGridData();
        mCache = data;
        return data;
    }


    @Override
    public ReviewStamp getStamp() {
        return ReviewStamp.newStamp(mNode.getAuthor(), mNode.getPublishDate());
    }

    @Override
    public boolean isExpandable(GvData datum) {
        if (!datum.hasElements() || mCache == null) return false;

        GvDataCollection data = (GvDataCollection) datum;
        for (GvData list : mCache) {
            ((GvDataCollection) list).sort();
        }
        data.sort();

        return mCache.contains(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvData datum) {
        if (isExpandable(datum)) {
            return mAdapterFactory.newDataToDataAdapter(mNode,
                    (GvDataCollection<? extends GvData>) datum);
        } else {
            return null;
        }
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return null;
    }
}
