/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Follow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FbSocialStructure {
    String SOCIAL = "Social";
    String FOLLOWING = "Following";
    String FOLLOWERS = "Followers";

    DbUpdater<Follow> getSocialUpdater();

    Firebase getFollowingDb(Firebase root, AuthorId authorId);

    Firebase getFollowersDb(Firebase root, AuthorId authorId);
}
