/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.test.Controller;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.MdGvConverter;
import com.chdryra.android.reviewer.Controller.WrapperGvDataList;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsManager;
import com.chdryra.android.reviewer.View.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvReviewId;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class WrapperTreeDataTest extends TestCase {
    @SmallTest
    public void testGetGridData() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        GvTagList tags = GvDataMocker.newTagList(3);
        TagsManager.tag(node.getId(), tags.toStringArray());

        ReviewId id = node.getId();
        GvDataCollection data = new GvDataCollection(GvReviewId.getId(id.toString()));
        data.add(MdGvConverter.getTags(id.toString()));
        data.add(MdGvConverter.convertChildren(node));
        data.add(MdGvConverter.convert(node.getImages()));
        data.add(MdGvConverter.convert(node.getComments()));
        data.add(MdGvConverter.convert(node.getLocations()));
        data.add(MdGvConverter.convert(node.getFacts()));

        WrapperGvDataList wrapper = new WrapperGvDataList(data);
        GvDataList collection = wrapper.getGridData();

        assertNotNull(collection);
        assertEquals(data.size(), collection.size());
        for (int i = 0; i < data.size(); ++i) {
            assertEquals(data.getItem(i), collection.getItem(i));
        }
    }
}