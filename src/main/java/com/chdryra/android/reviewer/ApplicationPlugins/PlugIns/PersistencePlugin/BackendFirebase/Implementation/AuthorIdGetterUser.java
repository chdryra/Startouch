/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 04/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorIdGetterUser implements DynamicPathUpdater.PathGetter<User>{
    @Override
    public String getPath(User item) {
        return item.getAuthorId();
    }
}