/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * UI Fragment: tags. Each grid cell shows a tag.
 */
public class FragmentReviewTags extends FragmentReviewGridAddEdit<GvTagList.GvTag> {
    public FragmentReviewTags() {
        super(GvDataList.GvType.TAGS);
    }
}
