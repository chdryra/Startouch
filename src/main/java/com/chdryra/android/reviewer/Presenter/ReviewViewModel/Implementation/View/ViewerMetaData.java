/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterAuthors;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterCriteria;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterDataTags;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterDateReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterFacts;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterReferences;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterSubjects;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDateList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFactList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocationList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReferenceList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSubjectList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ViewerMetaData<T extends GvData> extends ViewerReviewData<T> {
    private FactoryReviewViewAdapter mAdapterFactory;

    protected abstract void bind(MetaReference reference);

    protected abstract void unbind(MetaReference reference);

    protected ViewerMetaData(MetaReference reference,
                             GvDataList<T> initial,
                             FactoryReviewViewAdapter adapterFactory) {
        super(reference, initial);
        mAdapterFactory = adapterFactory;
    }

    @Override
    protected void bind(ReviewReference reference) {
        bind((MetaReference) reference);
    }

    @Override
    protected void unbind(ReviewReference reference) {
        unbind((MetaReference) reference);
    }

    @NonNull
    private static GvReviewId getId(ReviewReference reference) {
        return new GvReviewId(reference.getReviewId());
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return mAdapterFactory.newReviewsListAdapter(getGridData());
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(T datum) {
        return isExpandable(datum) ? mAdapterFactory.newReviewsListAdapter(datum) : null;
    }

    @Override
    public boolean isExpandable(T datum) {
        return getGridData().contains(datum);
    }

    public static class Reviews extends ViewerMetaData<GvReference> implements MetaBinders
            .ReviewsBinder {
        private GvConverterReferences mConverter;

        public Reviews(MetaReference reference,
                       GvConverterReferences converter,
                       FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvReferenceList(getId(reference)), adapterFactory);
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<ReviewReference> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(MetaReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(MetaReference reference) {
            reference.unbind(this);
        }
    }

    public static class Authors extends ViewerMetaData<GvAuthor> implements MetaBinders
            .AuthorsBinder {
        private GvConverterAuthors mConverter;

        public Authors(MetaReference reference,
                       GvConverterAuthors converter,
                       FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvAuthorList(getId(reference)), adapterFactory);
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataAuthorId> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(MetaReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(MetaReference reference) {
            reference.unbind(this);
        }
    }

    public static class Subjects extends ViewerMetaData<GvSubject> implements MetaBinders
            .SubjectsBinder {
        private GvConverterSubjects mConverter;

        public Subjects(MetaReference reference,
                        GvConverterSubjects converter,
                        FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvSubjectList(getId(reference)), adapterFactory);
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataSubject> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(MetaReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(MetaReference reference) {
            reference.unbind(this);
        }
    }

    public static class Dates extends ViewerMetaData<GvDate> implements MetaBinders.DatesBinder {
        private GvConverterDateReviews mConverter;

        public Dates(MetaReference reference,
                     GvConverterDateReviews converter,
                     FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvDateList(getId(reference)), adapterFactory);
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataDate> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(MetaReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(MetaReference reference) {
            reference.unbind(this);
        }
    }

    public static class Tags extends ViewerMetaData<GvTag> implements MetaBinders.TagsBinder {
        private GvConverterDataTags mConverter;

        public Tags(MetaReference reference,
                    GvConverterDataTags converter,
                    FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvTagList(getId(reference)), adapterFactory);
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataTag> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(MetaReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(MetaReference reference) {
            reference.unbind(this);
        }
    }

    public static class Criteria extends ViewerMetaData<GvCriterion> implements MetaBinders
            .CriteriaBinder {
        private GvConverterCriteria mConverter;

        public Criteria(MetaReference reference,
                        GvConverterCriteria converter,
                        FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvCriterionList(getId(reference)), adapterFactory);
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataCriterion> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(MetaReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(MetaReference reference) {
            reference.unbind(this);
        }
    }

    public static class Images extends ViewerMetaData<GvImage> implements MetaBinders.ImagesBinder {
        private GvConverterImages mConverter;

        public Images(MetaReference reference,
                      GvConverterImages converter,
                      FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvImageList(getId(reference)), adapterFactory);
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataImage> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(MetaReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(MetaReference reference) {
            reference.unbind(this);
        }
    }

    public static class Comments extends ViewerMetaData<GvComment> implements MetaBinders
            .CommentsBinder {
        private GvConverterComments mConverter;

        public Comments(MetaReference reference,
                        GvConverterComments converter,
                        FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvCommentList(getId(reference)), adapterFactory);
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataComment> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(MetaReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(MetaReference reference) {
            reference.unbind(this);
        }
    }

    public static class Locations extends ViewerMetaData<GvLocation> implements MetaBinders
            .LocationsBinder {
        private GvConverterLocations mConverter;

        public Locations(MetaReference reference,
                         GvConverterLocations converter,
                         FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvLocationList(getId(reference)), adapterFactory);
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataLocation> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(MetaReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(MetaReference reference) {
            reference.unbind(this);
        }
    }

    public static class Facts extends ViewerMetaData<GvFact> implements MetaBinders.FactsBinder {
        private GvConverterFacts mConverter;

        public Facts(MetaReference reference,
                     GvConverterFacts converter,
                     FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvFactList(getId(reference)), adapterFactory);
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataFact> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(MetaReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(MetaReference reference) {
            reference.unbind(this);
        }
    }
}
