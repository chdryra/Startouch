package com.chdryra.android.reviewer.View.Screens;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RbExpandGrid extends RatingBarAction {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode("RbTreePerspective");

    //Overridden
    @Override
    public void onClick(View v) {
        ReviewViewAdapter expanded = getAdapter().expandGridData();
        if (expanded == null) return;
        ReviewView ui = expanded.getReviewView();
        if (ui == null) ui = ReviewDataScreen.newScreen(expanded);
        LauncherUi.launch(ui, getActivity(), REQUEST_CODE, ui.getLaunchTag(), new Bundle());
    }
}
