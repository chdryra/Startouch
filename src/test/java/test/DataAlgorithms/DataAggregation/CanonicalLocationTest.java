package test.DataAlgorithms.DataAggregation;

import com.chdryra.android.mygenerallibrary.LatLngMidpoint;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalLocation;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalStringMaker;
import com.chdryra.android.reviewer.DataDefinitions.Factories.NullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.testutils.RandomLatLng;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalLocationTest extends CanonicalStringMakerTest<DataLocation>{
    public CanonicalLocationTest() {
        this(new CanonicalLocation());
    }

    protected CanonicalLocationTest(CanonicalStringMaker<DataLocation> canonical) {
        super(canonical);
    }

    @Override
    protected void checkValidForMultipleAggregated(DataLocation canonical) {
        String subject = canonical.getName();
        assertThat(subject, is(getModeString() + " + " + String.valueOf(getNumDifferent())));
        assertThat(canonical.getLatLng(), is(getExpectedLatLng()));
    }

    @Override
    protected void checkValidForSingleAggregated(DataLocation canonical) {
        assertThat(canonical.getName(), is(getModeString()));
        assertThat(canonical.getLatLng(), is(getExpectedLatLng()));
    }

    @Override
    protected void checkInvalid(DataLocation canonical) {
        assertThat(canonical, is(NullData.nullLocation(canonical.getReviewId())));
    }

    @Override
    protected DataLocation newDatum(String string) {
        return new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), string);
    }

    protected LatLng getExpectedLatLng() {
        IdableList<DataLocation> data = getData();
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (DataLocation location : data) {
            latLngs.add(location.getLatLng());
        }

        LatLngMidpoint midpoint = new LatLngMidpoint();
        return midpoint.getGeoMidpoint(latLngs);
    }
}