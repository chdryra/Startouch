package com.chdryra.android.reviewer;

import android.support.v4.app.Fragment;

public class ReviewCommentActivity extends SingleFragmentActivity {
	
	@Override
	protected Fragment createFragment() {
		return new ReviewCommentFragment();
	}

}
