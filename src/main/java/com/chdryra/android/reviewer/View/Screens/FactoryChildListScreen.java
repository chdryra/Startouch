package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.GridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerChildList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryChildListScreen {
    public ReviewView createView(ReviewNode node,
                                 MdGvConverter converter,
                                 TagsManager tagsManager,
                                 FactoryReviewViewAdapter adapterFactory,
                                 ReviewViewAction.GridItemAction giAction,
                                 ReviewViewAction.MenuAction menuAction) {
        GridDataViewer<GvReviewOverviewList.GvReviewOverview> viewer;
        viewer = new ViewerChildList(node, converter, tagsManager, adapterFactory);
        ReviewViewAdapter adapter = new AdapterReviewNode<>(node, converter, viewer);

        ReviewViewActions actions = new ReviewViewActions();
        if (giAction != null) actions.setAction(giAction);
        if (menuAction != null) actions.setAction(menuAction);
        actions.setAction(new RbExpandGrid());

        ReviewViewParams params = new ReviewViewParams();
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setSubjectVisible(true).setRatingVisible(true).setBannerButtonVisible(true)
                .setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);

        ReviewViewPerspective perspective = new ReviewViewPerspective(adapter, params, actions);
        return new ReviewView(perspective);
    }
}
