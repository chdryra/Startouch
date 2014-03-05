package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class DatumDialogFragment extends BasicDialogFragment {

	public static final String DATUM_OLD_LABEL = "com.chdryra.android.reviewer.datum_old_label";
	public static final String DATUM_OLD_VALUE = "com.chdryra.android.reviewer.datum_old_label";
	
	private EditText mLabel;
	private EditText mValue;
	private String mOldLabel;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_datum, null);
		mLabel = (EditText)v.findViewById(R.id.datum_label_edit_text);
		mValue = (EditText)v.findViewById(R.id.datum_value_edit_text);
		
		mOldLabel = getArguments().getString(ReviewDataFragment.DATUM_LABEL);
		mLabel.setText(mOldLabel);		
		mValue.setText(getArguments().getString(ReviewDataFragment.DATUM_VALUE));
		
		final AlertDialog dialog = buildDialog(v); 

		mValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            	dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();

	            return true;
	        }
	    });

		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		
		return dialog;
	}
		
	@Override
	protected void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		Intent i = new Intent();
		if(resultCode == Activity.RESULT_OK) {
			i.putExtra(DATUM_OLD_LABEL, mOldLabel);
			i.putExtra(ReviewDataFragment.DATUM_LABEL, mLabel.getText().toString());
			i.putExtra(ReviewDataFragment.DATUM_VALUE, mValue.getText().toString());
		}
		
		if(resultCode == RESULT_DELETE)
			i.putExtra(DATUM_OLD_LABEL, mOldLabel);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
}