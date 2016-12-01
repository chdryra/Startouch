/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class LaunchReviewOptionsCommand extends Command {
    public static final String AUTHOR_ID
            = TagKeyGenerator.getKey(LaunchReviewOptionsCommand.class, "AuthorId");

    private final LaunchOptionsCommand mOptionsCommand;
    private final Command mShare;
    private final Command mCopy;
    private final Command mDelete;

    private DataAuthorId mAuthorId;

    public LaunchReviewOptionsCommand(LaunchOptionsCommand optionsCommand, Command share, Command copy, Command delete) {
        mOptionsCommand = optionsCommand;
        mShare = share;
        mCopy = copy;
        mDelete = delete;
    }

    public LaunchReviewOptionsCommand(LaunchOptionsCommand optionsCommand, Command share, Command copy, Command delete, DataAuthorId authorId) {
        mOptionsCommand = optionsCommand;
        mAuthorId = authorId;
    }

    public void execute(DataAuthorId authorId) {
        mAuthorId = authorId;
        execute();
    }

    @Override
    public void execute() {
        if(mAuthorId == null) return;
        ArrayList<String> options = new ArrayList<>();

    }
}
