package com.chdryra.android.reviewer.LocationServices.Implementation.ProviderGoogle;

import com.chdryra.android.remoteapifetchers.GpPlaceSearchResults;
import com.chdryra.android.remoteapifetchers.GpPlaceSearcher;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearchResultsListener;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcher;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlaceSearcherGp implements GpPlaceSearcher.SearchListener, PlaceSearcher {
    private GpPlaceSearcher mSearcher;
    private PlaceSearchResultsListener mListener;

    public PlaceSearcherGp(GpPlaceSearcher searcher) {
        mSearcher = searcher;
    }

    @Override
    public void searchQuery(String query, PlaceSearchResultsListener listener) {
        mListener = listener;
        mSearcher.fetchResults(query, this);
    }

    @Override
    public void onSearchResultsFound(GpPlaceSearchResults results) {
        mListener.onSearchResultsFound(GpLocatedPlaceConverter.convert(results));
    }
}
