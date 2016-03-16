/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactorySocialUploader;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.NetworkServicesPlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseNetworkContext implements NetworkContext {
    private FactorySocialUploader mUploaderFactory;

    public ReleaseNetworkContext(Context context, NetworkServicesPlugin plugin) {
        mUploaderFactory = plugin.getUploaderFactory(context);
    }

    @Override
    public FactorySocialUploader getReviewUploaderFactory() {
        return mUploaderFactory;
    }
}
