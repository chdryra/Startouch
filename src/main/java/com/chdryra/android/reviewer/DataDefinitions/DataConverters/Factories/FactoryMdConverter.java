package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Factories;

import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation
        .MdConverters.ConverterMd;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation
        .MdConverters.MdConverterComments;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation
        .MdConverters.MdConverterCriteria;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters.MdConverterFacts;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters.MdConverterImages;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters.MdConverterLocations;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters.MdConverterUrl;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryMdConverter {
    public ConverterMd newMdConverter() {
        MdConverterComments converterComments = new MdConverterComments();
        MdConverterUrl converterUrl = new MdConverterUrl();
        MdConverterFacts converterFacts = new MdConverterFacts(converterUrl);
        MdConverterImages converterImages = new MdConverterImages();
        MdConverterLocations converterLocations = new MdConverterLocations();
        MdConverterCriteria converterCriteria = new MdConverterCriteria();

        return new ConverterMd(converterComments, converterFacts, converterImages,
                converterLocations, converterUrl, converterCriteria);
    }
}