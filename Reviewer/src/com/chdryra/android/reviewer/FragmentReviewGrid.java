package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.mygenerallibrary.FragmentDeleteDone;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;

public abstract class FragmentReviewGrid extends FragmentDeleteDone{

public enum CellDimension{FULL, HALF, QUARTER}; 

	private ControllerReviewNode mController;
	
	private TextView mSubjectView;
	private RatingBar mTotalRatingBar;
	private Button mAddDataButton;
	private GridView mGridView;

	int mMaxGridCellWidth;
	int mMaxGridCellHeight;
	
	int mCellWidthDivider = 1;
	int mCellHeightDivider = 1;

	private boolean mIsEditable = false;
	private String mAddDataButtonText;
	
	protected abstract GridViewCellAdapter getGridViewCellAdapter();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mController = Controller.unpack(getActivity().getIntent().getExtras());
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		setAddDataButtonText(getResources().getString(R.string.button_add_text));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View v = inflater.inflate(R.layout.fragment_review_grid, container, false);			

		mSubjectView = (TextView)v.findViewById(R.id.review_subject_edit_text);
		mTotalRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		mAddDataButton = (Button)v.findViewById(R.id.add_data_button);
		mGridView = (GridView)v.findViewById(R.id.data_gridview);
		
		//***Get display metrics for reviewTag display size***//
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		
		mMaxGridCellWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);				
		mMaxGridCellHeight = mMaxGridCellWidth;
		
		initUI();
		updateUI();
		
		return v;		
	}
	
	protected void initUI() {
		initSubjectUI();
		initRatingBarUI();
		initAddDataUI();
		initDataGridUI();
	}
	
	protected void initSubjectUI() {
		if(isEditable()) {
			getSubjectView().addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
				
				@Override
				public void afterTextChanged(Editable s) {
					if(s.toString().length() > 0)
						getController().setTitle(s.toString());
				}
			});
		}
	}
	
	protected void initRatingBarUI() {
		if(isEditable()) {
			getTotalRatingBar().setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
						getController().setRating(rating);
				}
			});
		}
	}

	protected void initAddDataUI() {
		if(isEditable()) {
			getAddDataButton().setText(getAddDataButtonText());
			getAddDataButton().setTextColor(getSubjectView().getTextColors().getDefaultColor());
			getAddDataButton().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onAddDataButtonClick();
				}
			});
		} else
			getAddDataButton().setVisibility(View.GONE);
	}
	
	protected void initDataGridUI(){
		getGridView().setAdapter(getGridViewCellAdapter());
		getGridView().setColumnWidth(getGridCellWidth());
		getGridView().setNumColumns(getNumberColumns());
		getGridView().setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            onGridItemClick(parent, v, position, id);
	        }
	    });
	};

	protected void onAddDataButtonClick() {
		
	}
	
	protected void setAddDataButtonText(String addDataButtonText) {
		mAddDataButtonText = addDataButtonText;
	}
	
	protected String getAddDataButtonText() {
		return mAddDataButtonText;
	}
	
	protected void setIsEditable(boolean isEditable) {
		mIsEditable = isEditable;
	}
	
	protected boolean isEditable() {
		return mIsEditable;
	}
	
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		
	}
	
	protected void updateUI() {
		updateSubjectTextUI();
		updateRatingBarUI();
		updateGridDataUI();
	}

	protected void updateSubjectTextUI() {
		if(getController() != null)
			getSubjectView().setText(getController().getTitle());
	}
	
	protected void updateRatingBarUI() {
		if(getController() != null)
			getTotalRatingBar().setRating(getController().getRating());
	}

	protected void updateGridDataUI() {
		((GridViewCellAdapter)getGridView().getAdapter()).notifyDataSetChanged();
	}

	protected void setGridCellDimension(CellDimension width, CellDimension height) {
		mCellWidthDivider = 1;
		mCellHeightDivider = 1;
		
		if(width == CellDimension.HALF)
			mCellWidthDivider = 2;
		else if(width == CellDimension.QUARTER)
			mCellWidthDivider = 4;
		
		if(height == CellDimension.HALF)
			mCellHeightDivider = 2;
		else if(height == CellDimension.QUARTER)
			mCellHeightDivider = 4;
	}
	
	protected int getGridCellWidth() {
		return mMaxGridCellWidth / mCellWidthDivider;
	}

	protected int getGridCellHeight() {
		return mMaxGridCellHeight / mCellHeightDivider;
	}
	
	protected int getNumberColumns() {
		return mCellWidthDivider;
	}
	
	protected TextView getSubjectView() {
		return mSubjectView;
	}
	
	protected RatingBar getTotalRatingBar() {
		return mTotalRatingBar;
	}
	
	protected Button getAddDataButton() {
		return mAddDataButton;
	}
	
	protected GridView getGridView() {
		return mGridView;
	}

	protected ControllerReviewNode getController() {
		return mController;
	}
	
	protected void setController(ControllerReviewNode controller) {
		mController = controller;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean ret = super.onOptionsItemSelected(item);
		updateUI();
		return ret;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		updateUI();
	}
}
