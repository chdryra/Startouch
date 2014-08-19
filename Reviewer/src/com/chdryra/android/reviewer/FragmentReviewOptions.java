package com.chdryra.android.reviewer;

import android.app.ActionBar.LayoutParams;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.FunctionPointer;
import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVDualString;
import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.FragmentReviewOptions.GVCellManagerList.GVCellManager;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.ReviewDataOptions.ReviewDataOption;
import com.google.android.gms.maps.model.LatLng;

public class FragmentReviewOptions extends FragmentReviewGrid<GVCellManager> {
	public final static int LOCATION_MAP = 22;
	public final static int URL_BROWSE = 52;
	
	private GVCellManagerList mCellManagerList;
	private HelperReviewImage mHelperReviewImage;
	private Button mPublishButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initCellManagerList();
		
		setGridViewData(mCellManagerList);
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		setDismissOnDone(false);
		setBannerButtonText(getResources().getString(R.string.button_add_review_data));
		setIsEditable(true);
		setBackgroundImageAlpha(0);
		
		mHelperReviewImage = HelperReviewImage.getInstance(getController());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		getBannerButton().setClickable(false);
		View divider = inflater.inflate(R.layout.horizontal_divider, container, false);
		mPublishButton = (Button)inflater.inflate(R.layout.review_banner_button, container, false);
		mPublishButton.setText(getResources().getString(R.string.button_publish));
		mPublishButton.getLayoutParams().height = LayoutParams.MATCH_PARENT;
		mPublishButton.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				if(getController().getData(GVType.TAGS).size() == 0) {
					Toast.makeText(getActivity(), R.string.toast_enter_tag, Toast.LENGTH_SHORT).show();
				} 
			}
		});
		
		getGridView().getLayoutParams().height = LayoutParams.WRAP_CONTENT;
		getLayout().addView(mPublishButton);
		getLayout().addView(divider);
		
		return v;
	}
	
	private void initCellManagerList() {
		mCellManagerList = new GVCellManagerList();
		mCellManagerList.add(GVType.TAGS);
		mCellManagerList.add(GVType.CRITERIA);
		mCellManagerList.add(GVType.IMAGES);
		mCellManagerList.add(GVType.COMMENTS);
		mCellManagerList.add(GVType.LOCATIONS);
		mCellManagerList.add(GVType.FACTS);
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		GVCellManager cellManager = (GVCellManager)parent.getItemAtPosition(position);
		cellManager.executeIntent(false);
	}
	
	@Override
	protected void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {
		GVCellManager cellManager = (GVCellManager)parent.getItemAtPosition(position);
		cellManager.executeIntent(true);
	}
	
	@Override
	protected void onDoneSelected() {
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_review_options, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_average_rating:
			getController().setReviewRatingAverage(true);
			getTotalRatingBar().setRating(getController().getRating());
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private ReviewDataOption getOption(GVType dataType) {
		return ReviewDataOptions.get(dataType);
	}
	
	private void requestIntent(ReviewDataOption option) {
		Intent i = new Intent(getActivity(), option.getActivityRequestClass());
		Controller.pack(getController(), i);
		startActivityForResult(i, option.getActivityRequestCode());
	}

	private <T> void requestIntent(Class<T> c, int requestCode, Intent data) {
		Intent i = new Intent(getActivity(), c);
		i.putExtras(data);
		startActivityForResult(i, requestCode);
	}

	private void showQuickDialog(ReviewDataOption option) {
		DialogFragment dialog = option.getDialogFragment();
		dialog.setTargetFragment(FragmentReviewOptions.this, option.getDialogRequestCode());
		Bundle args = Controller.pack(getController());
		args.putBoolean(DialogAddReviewDataFragment.QUICK_SET, true);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), option.getDialogTag());
	}
	
	private void showQuickImageDialog() {	
        startActivityForResult(mHelperReviewImage.getImageChooserIntents(getActivity()), 
        		getOption(GVType.IMAGES).getActivityRequestCode());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		ActivityResultCode resCode = ActivityResultCode.get(resultCode);
		if (requestCode == getOption(GVType.IMAGES).getActivityRequestCode()) {
			if(mHelperReviewImage.processOnActivityResult(getActivity(), resultCode, data))
				addImage();
		} else if (requestCode == getOption(GVType.LOCATIONS).getDialogRequestCode()) {
			if(resCode.equals(DialogLocationFragment.RESULT_MAP.getResultCode()))
				requestIntent(ActivityReviewLocationMap.class, LOCATION_MAP, data);
		} else if (requestCode == LOCATION_MAP) {
			if(resCode.equals(ActivityResultCode.DONE))
				addLocation(data);
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
		updateUI();
	}
	
	private void addImage() {
		final GVImageList images = new GVImageList();
		mHelperReviewImage.addReviewImage(getActivity(), images, new FunctionPointer<Void>() {
			@Override
			public void execute(Void data) {
				images.getItem(0).setIsCover(true);
				getController().setData(images);
				updateUI();
			}
		});
	}
	
	private void addLocation(Intent data) {
		LatLng latLng = data.getParcelableExtra(FragmentReviewLocationMap.LATLNG);
		String name = (String)data.getSerializableExtra(FragmentReviewLocationMap.NAME);
		if(latLng != null && name != null) {
			GVLocationList list = new GVLocationList();
			list.add(latLng, name);
			getController().setData(list);
		}
	}
	
//	private void addURL(Intent data) {
//		URL url = (URL)data.getSerializableExtra(FragmentReviewURLBrowser.URL);
//		if(url != null) {
//			GVUrlList urls = new GVUrlList();
//			urls.add(url);
//			getController().setData(urls);
//		}
//	}
	
	@Override
	protected GridViewCellAdapter getGridViewCellAdapter() {
		return new ReviewOptionsGridCellAdapter();
	}
	
	class ReviewOptionsGridCellAdapter extends GridViewCellAdapter {
		public ReviewOptionsGridCellAdapter() {
			super(getActivity(), getGridData(), getGridCellWidth(), getGridCellHeight());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = getGridData().getItem(position).updateView(parent);
			convertView.getLayoutParams().height = getGridCellHeight();
			convertView.getLayoutParams().width = getGridCellWidth();
			
			return convertView;
		}
	}
	
	class GVCellManagerList extends GVReviewDataList<GVCellManagerList.GVCellManager> {
		public GVCellManagerList() {
			super(null);
		}
		
		private void add(GVType dataType) {
			add(new GVCellManager(dataType));
		}
					
		class GVCellManager implements GVData {
			private GVType mDataType;

			private GVCellManager(GVType dataType) {
				mDataType = dataType;
			}

			private void executeIntent(boolean forceRequestIntent) {
				if(getController().getData(mDataType).size() == 0 && !forceRequestIntent) {
					if(mDataType == GVType.IMAGES)
						showQuickImageDialog();
					else
						showQuickDialog(getOption(mDataType));
				}
				else
					requestIntent(getOption(mDataType));
			}

			@Override
			public ViewHolder getViewHolder() {
				return null;
			}
			
			public View updateView(ViewGroup parent) {
				int size = getController().getData(mDataType).size();
				
				if(size == 0)
					return getNoDatumView(parent);

				return size > 1 || mDataType == GVType.IMAGES? getMultiDataView(parent) : getSingleDatumView(parent, 0); 
			}
			
			public View getNoDatumView(ViewGroup parent) {
				ViewHolder vh = new VHTextView();
				vh.inflate(getActivity(), parent);
				return vh.updateView(new GVString(mDataType.getDataString()));
			}

			public View getSingleDatumView(ViewGroup parent, int position) {
				if(position > getController().getData(mDataType).size())
					return getNoDatumView(parent);

				ViewHolder vh = getOption(mDataType).getViewHolder();
				vh.inflate(getActivity(), parent);
				return vh.updateView(getController().getData(mDataType).getItem(position));
			}
						
			public View getMultiDataView(ViewGroup parent) {
				int number = getController().getData(mDataType).size();
				String type = number == 1? mDataType.getDatumString() : mDataType.getDataString();
				
				ViewHolder vh = new VHTextDualView();
				vh.inflate(getActivity(), parent);
				return vh.updateView(new GVDualString(String.valueOf(number), type));
			}
			
			public View getProConSummaryView(ViewGroup parent) {
				GVProConList pros = (GVProConList) getController().getData(GVType.PROS);
				GVProConList cons = (GVProConList) getController().getData(GVType.CONS);
				
				String proString = getResources().getString(R.string.text_view_pro_prefix);
				String conString = getResources().getString(R.string.text_view_con_prefix);
				
				if(pros.size() == 0)
					proString += GVType.PROS.getDataString();
				else
					proString += pros.size() == 1? pros.getItem(0).toString() : String.valueOf(pros.size()) + " " + GVType.PROS.getDataString();
				
				if(cons.size() == 0)
					conString += GVType.CONS.getDataString();
				else
					conString += cons.size() == 1? cons.getItem(0).toString() : String.valueOf(cons.size()) + " " + GVType.CONS.getDataString();
				
				ViewHolder vh = new VHTextDualView();
				vh.inflate(getActivity(), parent);
				
				return vh.updateView(new GVDualString(proString, conString));
			}
		}
	}
}