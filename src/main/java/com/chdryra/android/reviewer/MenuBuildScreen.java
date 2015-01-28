/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 28/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuBuildScreen extends ViewReviewAction.MenuAction {
    public static final  int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
    private static final int MENU            = R.menu.fragment_review_options;
    private MenuActionItem mActionItem;

    public MenuBuildScreen(ControllerReview controller) {
        super(controller, GvDataList.GvType.REVIEW, MENU);
        mActionItem = new MenuItemChildrenRatingAverage(this);
    }

    @Override
    protected void addMenuItems() {
        addMenuActionItem(mActionItem, MENU_AVERAGE_ID, false);
    }
}
