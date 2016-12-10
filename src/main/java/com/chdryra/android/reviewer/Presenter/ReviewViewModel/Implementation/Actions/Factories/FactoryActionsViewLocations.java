/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuViewLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchMappedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewLocations extends FactoryActionsViewData<GvLocation.Reference> {
    private final ReviewNode mNode;

    public FactoryActionsViewLocations(ViewDataParameters<GvLocation.Reference> parameters,
                                       ReviewNode node) {
        super(parameters);
        mNode = node;
    }

    @Override
    public MenuAction<GvLocation.Reference> newMenu() {
        LaunchMappedCommand command
                = getCommandsFactory().newLaunchMappedCommand(mNode);
        return new MenuViewLocations(newOptionsMenuItem(), new MaiCommand<GvLocation.Reference>(command));
    }
}