package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogAddCancelDoneFragment;

public class DialogFactAddFragment extends DialogAddCancelDoneFragment{
	public static final String FACT_LABEL = "com.chdryra.android.reviewer.fact_label";
	public static final String FACT_VALUE = "com.chdryra.android.reviewer.fact_value";
	
	private GVFacts mFacts;	
	private ClearableEditText mFactLabelEditText;
	private ClearableEditText mFactValueEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFacts = Controller.unpack(getArguments()).getFacts();
		setDialogTitle(getResources().getString(R.string.dialog_add_fact_title));
		setAddOnDone(true);
	}

	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_fact_add, null);
		mFactLabelEditText = (ClearableEditText)v.findViewById(R.id.fact_label_edit_text);
		mFactValueEditText = (ClearableEditText)v.findViewById(R.id.fact_value_edit_text);
		setKeyboardIMEDoAction(mFactValueEditText);
		
		return v;
	}

	@Override
	protected void OnAddButtonClick() {
		String label = mFactLabelEditText.getText().toString();
		String value = mFactValueEditText.getText().toString();
		if((label == null || label.length() == 0) && (value == null || value.length() == 0))
			return;
		
		if(label == null || label.length() == 0)
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_label), Toast.LENGTH_SHORT).show();
		else if(value == null || value.length() == 0)
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_value), Toast.LENGTH_SHORT).show();
		else if (mFacts.hasFact(label, value)) {
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_has_fact), Toast.LENGTH_SHORT).show();
		} else {
			mFacts.add(label, value);
			Intent i = getNewReturnData(); 
			i.putExtra(FACT_LABEL, label);
			i.putExtra(FACT_VALUE, value);
			mFactLabelEditText.setText(null);
			mFactValueEditText.setText(null);
			getDialog().setTitle("Added " + label + ": " + value);
			mFactLabelEditText.requestFocus();
		}
	}
}