/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Backend;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Utils.CallbackMessage;

/**
 * Created by: Rizwan Choudrey
 * On: 04/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LocalToBackendListener {
    void onUploadCompleted(ReviewId id, CallbackMessage message);

    void onUploadFailed(ReviewId id, CallbackMessage message);
}
