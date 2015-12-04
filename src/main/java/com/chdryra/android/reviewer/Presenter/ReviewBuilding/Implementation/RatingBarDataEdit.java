package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarDataEdit<T extends GvData> extends ReviewDataEditorActionBasic<T> implements RatingBarAction<T>{
    @Override
    public void onClick(View v) {

    }

    @Override
    public float getRating() {
        return getAdapter().getRating();
    }

    @Override
    public void onRatingChanged(android.widget.RatingBar ratingBar, float rating,
                                boolean fromUser) {
        if(fromUser) getEditor().setRating(rating, true);
    }
}