package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationServicesSuite;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationContext {
    PresenterContext getContext();
    LocationServicesSuite getLocationServicesSuite();
}
