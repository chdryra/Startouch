package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.mygenerallibrary.DialogAddCancelDoneFragment;
import com.chdryra.android.mygenerallibrary.GVStrings;

public class DialogTagAddFragment extends DialogAddCancelDoneFragment{
	public static final String TAG = "com.chdryra.android.review.TAG";
	
	private GVStrings mTags;	
	private ClearableAutoCompleteTextView mTagEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTags = Controller.unpack(getArguments()).getTags();
		setDialogTitle(getResources().getString(R.string.dialog_add_tag_title));
		setAddOnDone(true);
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
		
		if(mTags.contains(tag))
			Toast.makeText(getActivity(), R.string.toast_has_tag, Toast.LENGTH_SHORT).show();
		else {
			mTags.add(tag);
			getNewReturnData().putExtra(TAG, tag);
			mTagEditText.setText(null);
			getDialog().setTitle("Added tag: " + tag);
		}
	}
}