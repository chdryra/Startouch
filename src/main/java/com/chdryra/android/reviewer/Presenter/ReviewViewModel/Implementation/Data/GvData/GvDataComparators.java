package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Interfaces.ComparatorCollection;
import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Factories.DataComparatorsFactory;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
//TODO make this non-static and injectable
public class GvDataComparators {
    private static GvDataComparators sComparator;
    private ComparatorMappings mMap = new ComparatorMappings();

    private GvDataComparators(DataComparatorsFactory comparatorsFactory) {
        mMap = new ComparatorMappings();
        mMap.put(GvSubject.TYPE, comparatorsFactory.getSubjectComparators());
        mMap.put(GvAuthor.TYPE, comparatorsFactory.getAuthorComparators());
        mMap.put(GvCriterion.TYPE, comparatorsFactory.getCriterionComparators());
        mMap.put(GvComment.TYPE, comparatorsFactory.getCommentComparators());
        mMap.put(GvDate.TYPE, comparatorsFactory.getDateComparators());
        mMap.put(GvFact.TYPE, comparatorsFactory.getFactCompartors());
        mMap.put(GvImage.TYPE, comparatorsFactory.getImageComparators());
        mMap.put(GvLocation.TYPE, comparatorsFactory.getLocationComparators());
        mMap.put(GvReviewOverview.TYPE, comparatorsFactory.getReviewComparators());
        mMap.put(GvSocialPlatform.TYPE, comparatorsFactory.getSocialPlatformComparators());
        mMap.put(GvTag.TYPE, comparatorsFactory.getTagComparators());
        mMap.put(GvUrl.TYPE, comparatorsFactory.getUrlComparators());
    }

    //Static methods
    public static <T extends GvData> Comparator<? super T> getDefaultComparator(GvDataType<T> elementType) {
        ComparatorCollection<? super T> sorters = get().mMap.get(elementType);
        if(sorters != null) {
            return sorters.getDefault();
        } else {
            return getComparator(elementType);
        }
    }

    @NonNull
    private static <T extends GvData> Comparator<T> getComparator(GvDataType<T> elementType) {
        return new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                return lhs.toString().compareToIgnoreCase(rhs.toString());
            }
        };
    }

    private static GvDataComparators get() {
        if (sComparator == null) sComparator = new GvDataComparators(new DataComparatorsFactory());
        return sComparator;
    }

    //To help with type safety
    private class ComparatorMappings {
        private Map<GvDataType<? extends GvData>, ComparatorCollection<?>> mMap =
                new HashMap<>();

        private <T extends GvData> void put(GvDataType<T> dataType, ComparatorCollection<? super T>
                sorters) {
            mMap.put(dataType, sorters);
        }

        //TODO make type safe (although it kind of is really...)
        private <T extends GvData> ComparatorCollection<? super T> get(GvDataType<T> dataType) {
            return (ComparatorCollection<? super T>) mMap.get(dataType);
        }
    }
}