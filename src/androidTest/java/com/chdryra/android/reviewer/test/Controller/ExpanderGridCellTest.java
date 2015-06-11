/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.test.Controller;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.ReviewAdapterModel.AdapterReviewNode;
import com.chdryra.android.reviewer.Controller.ReviewAdapterModel.ExpanderGridCell;
import com.chdryra.android.reviewer.Controller.ReviewAdapterModel.ViewerGvDataList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderGridCellTest extends AndroidTestCase {
    @SmallTest
    public void testExpandItem() {
        ReviewNode node = ReviewMocker.newReviewNode(false);

        GvTagList tags = GvDataMocker.newTagList(3);
        TagsManager.tag(node.getId(), tags.toStringArray());

        GvDataList<GvData> data = new GvDataList<>(null, GvData.class, new GvDataType("testData"));
        data.add(GvDataMocker.newCommentList(6, false));
        data.add(GvDataMocker.newFactList(6, false));
        data.add(GvDataMocker.newLocationList(0, false));
        data.add(GvDataMocker.newImage(null));

        ViewerGvDataList wrapper = new ViewerGvDataList(data);
        AdapterReviewNode parent = new AdapterReviewNode(node, wrapper);

        ExpanderGridCell expander = new ExpanderGridCell(getContext(), parent);
        GvCommentList.GvComment comment = GvDataMocker.newComment(null);
        assertFalse(expander.isExpandable(comment));
        assertNull(expander.expandItem(comment));

        GvDataList gridData = parent.getGridData();
        assertTrue(gridData.size() > 0);
        for (int i = 0; i < data.size(); ++i) {
            GvData datum = data.getItem(i);
            assertNotNull(expander.expandItem(datum));
        }
    }
}
