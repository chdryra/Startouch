/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemEditComment;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuEditComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiSplitCommentVals;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsCommentsEdit extends FactoryActionsEditData<GvComment> {
    private static final GvDataType<GvComment> TYPE = GvComment.TYPE;

    FactoryActionsCommentsEdit(UiConfig config,
                               FactoryGvData dataFactory,
                               FactoryCommands commandsFactory) {
        super(TYPE, config, dataFactory, commandsFactory);
    }

    @Override
    public GridItemAction<GvComment> newGridItem() {
        return new GridItemEditComment(getEditorConfig(), getPacker());
    }

    @Override
    public MenuAction<GvComment> newMenu() {
        return new MenuEditComments(newUpAction(), newDoneAction(), newDeleteAction(),
                newPreviewAction(), new MaiSplitCommentVals());
    }
}
