/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.SocialUploader;

import android.content.Context;
import android.content.Intent;

import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BroadcastingServiceReceiver;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublishingListener;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPublishingReceiver extends BroadcastingServiceReceiver<SocialPublishingListener> {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(SocialPublishingService.STATUS_UPDATE)) {
            updateListenersOnStatus(intent);
        } else if(action.equals(SocialPublishingService.PUBLISHING_COMPLETED)){
            updateListenersOnCompletion(intent);
        }
    }

    private void updateListenersOnCompletion(Intent intent) {
        ArrayList<PublishResults> ok = intent.getParcelableArrayListExtra(SocialPublishingService
                .PUBLISH_RESULTS_OK);
        ArrayList<PublishResults> notOk = intent.getParcelableArrayListExtra(SocialPublishingService
                .PUBLISH_RESULTS_NOT_OK);
        for(SocialPublishingListener listener : this) {
            listener.onUploadCompleted(ok, notOk);
        }
    }

    private void updateListenersOnStatus(Intent intent) {
        double percentage = intent.getDoubleExtra(SocialPublishingService.STATUS_PERCENTAGE, 0.);
        PublishResults results = intent.getParcelableExtra(SocialPublishingService.STATUS_RESULTS);
        for(SocialPublishingListener listener : this) {
            listener.onUploadStatus(percentage, results);
        }
    }
}