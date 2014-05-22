package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogAddCancelDoneFragment;
import com.chdryra.android.mygenerallibrary.GVStrings;

public class DialogProAndConAddFragment extends DialogAddCancelDoneFragment{

	private ControllerReviewNode mController;
	
	private GVStrings mPros;
	private GVStrings mCons;
	
	private ClearableEditText mProEditText;
	private ClearableEditText mConEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
		mPros = mController.getPros();
		mCons = mController.getCons();
		setDialogTitle(getResources().getString(R.string.dialog_add_procon_title));
	}

	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_pro_and_con_add, null);

		mProEditText = (ClearableEditText)v.findViewById(R.id.pro_edit_text);
		mConEditText = (ClearableEditText)v.findViewById(R.id.con_edit_text);
		setKeyboardIMEDoAction(mProEditText);
		setKeyboardIMEDoAction(mConEditText);

		return v;
	}

	@Override
	protected void OnAddButtonClick() {
		String pro = mProEditText.getText().toString();
		String con = mConEditText.getText().toString();
		if((pro == null || pro.length() == 0) && (con == null || con.length() == 0))
			return;
		
		if(pro.length() > 0)
			mPros.add(pro);
		
		if(con.length() > 0)
			mCons.add(con);
		
		mProEditText.setText(null);
		mConEditText.setText(null);
		
		if(pro.length() > 0 && con.length() > 0)
			getDialog().setTitle("Added Pro: " + pro + ", Con: " + con);
		else if(pro.length() > 0)
			getDialog().setTitle("Added Pro: " + pro);
		else 
			getDialog().setTitle("Added Con: " + con);
	
	}
	
	@Override
	protected void onDoneButtonClick() {
		OnAddButtonClick();
		mController.setProsCons(mPros, mCons);
	}
}