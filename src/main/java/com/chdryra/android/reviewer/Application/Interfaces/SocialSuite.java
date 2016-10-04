/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Interfaces;

import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public interface SocialSuite {
    SocialPlatformList getSocialPlatformList();

    SocialProfile getSocialProfile();

    TagsManager getTagsManager();
}
