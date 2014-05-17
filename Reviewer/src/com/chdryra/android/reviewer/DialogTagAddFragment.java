package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.mygenerallibrary.DialogAddCancelDoneFragment;
import com.chdryra.android.mygenerallibrary.GVStrings;

public class DialogTagAddFragment extends DialogAddCancelDoneFragment{
	private ControllerReviewNode mController;
	private GVStrings mTags;	
	private ClearableAutoCompleteTextView mTagEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
		mTags = mController.getTags();
		setDialogTitle(getResources().getString(R.string.dialog_add_tag_title));
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag, null);
		mTagEditText = (ClearableAutoCompleteTextView)v.findViewById(R.id.tag_edit_text);
		setKeyboardIMEDoAction(mTagEditText);
		
		return v;
	}

	@Override
	protected void OnAddButtonClick(){
		String tag = mTagEditText.getText().toString();
		if(tag == null || tag.length() == 0)
			return;
		
		if(tag.length() > 0)
			mTags.add(tag);
		
		mTagEditText.setText(null);
		
		getDialog().setTitle("Added tag: " + tag);
	}

	@Override
	protected void onDoneButtonClick() {
		OnAddButtonClick();
		mController.removeTags();
		mController.addTags(mTags);
	}	
}
