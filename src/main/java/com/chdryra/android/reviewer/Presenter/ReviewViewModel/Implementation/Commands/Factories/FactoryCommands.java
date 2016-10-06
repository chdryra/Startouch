/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Interfaces.ReviewDeleter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CopyCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.DeleteCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.NewReviewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.ShareCommand;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryCommands {
    public Command newCopyCommand(UiLauncher launcher, @Nullable ReviewId template, CurrentScreen
            screen) {
        return new CopyCommand(newNewReviewCommand(launcher, template), screen);
    }

    public Command newShareCommand(RepositorySuite repo, ReviewId reviewId, CurrentScreen screen,
                                   SocialPublisher publisher, TagsManager tagsManager) {
        return new ShareCommand(reviewId, repo, screen, publisher, tagsManager);
    }

    public Command newDeleteCommand(ReviewDeleter deleter, CurrentScreen screen) {
        return new DeleteCommand(deleter, screen);
    }

    public LaunchOptionsCommand newLaunchOptionsCommand(LaunchableConfig optionsUi) {
        return new LaunchOptionsCommand(optionsUi);
    }

    private NewReviewCommand newNewReviewCommand(UiLauncher launcher, @Nullable ReviewId template) {
        return new NewReviewCommand(launcher, template);
    }
}
