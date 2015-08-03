/**************************************************************************
 * Copyright (c) 2014,   INFINX (TIS Pvt. Ltd.)(www.infinxservices.com). 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/

package com.infinx.bookmyslot;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infinx.model.BookingInfo;
import com.infinx.parsers.BookingInfoParser;
import com.infinx.util.CustomGridAdapter;
import com.infinx.util.Helper;
import com.infinx.webservices.AppConfig;
import com.infinx.webservices.CallWebServices;
import com.infinx.webservices.CallWebServices.Webmethods;
import com.infinx.webservices.SoapObjectsHelper;
import com.infinx.webservices.WebcallResponse;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355
 * 
 *      Created On 04-Dec-2014 ,8:27:15 pm
 */
public class ParkinigPlaceActivity extends Activity {

	private Context mCtx;
	Helper mHelper = null;
	Spinner spLevels, spSlots;

	GridView gvPremium, gvNormal;

	LinearLayout llGvParent;

	// TextView tvAvailable,tvBooked, tvPrice, tvType;

	ArrayAdapter<String> adpLevel = null;
	ArrayAdapter<String> adpSlot = null;
	public static String sFormattedDateTime = "";
	public static String sMallName = "";
	public static String sBookingDateTime = "";
	public static String sBookingSpot = "";

	public static BookingInfo sBookingInfo = null;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parking_place_map);

		mCtx = this;
		mHelper = new Helper();

		sBookingInfo = new BookingInfo();

		spLevels = (Spinner) findViewById(R.id.sp_level);
		spSlots = (Spinner) findViewById(R.id.sp_block);

		// tvAvailable = (TextView) findViewById(R.id.tv_available);
		// tvBooked = (TextView) findViewById(R.id.tv_booked);
		// tvPrice = (TextView) findViewById(R.id.tv_price);
		// tvType = (TextView) findViewById(R.id.tv_type);

		gvPremium = (GridView) findViewById(R.id.gv_premium);

		gvNormal = (GridView) findViewById(R.id.gv_normal);

		llGvParent = (LinearLayout) findViewById(R.id.ll_gv_parent);

		String mCityName = getIntent().getStringExtra("CITY");
		String mMall = getIntent().getStringExtra("MALL");
		String mBookingDateTime = getIntent().getStringExtra(
				"BOOKING_DATE_TIME");

		String mLocationId = ValidateUserActvity.sDbAdapter.getLocationId(
				mCityName, mMall);
		sFormattedDateTime = Helper.convertDateFormat(mBookingDateTime,
				AppConfig.DATE_TIME_FORMAT_DD_MMM_YYYY_HH_MM_AA,
				AppConfig.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS);

		sMallName = mMall;
		sBookingDateTime = mBookingDateTime;

		FetchParkingSlots mFetchSlots = new FetchParkingSlots();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			mFetchSlots.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					mLocationId, sFormattedDateTime);
		} else {

			mFetchSlots.execute(mLocationId, sFormattedDateTime);
		}

		spLevels.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				// showOnMap(spCity.getSelectedItem().toString());
				ArrayList<String> lstAllBlocks = new ArrayList<String>();
				ArrayList<String> lstAvailableBlocks = new ArrayList<String>();

				for (int i = 0; i < ValidateUserActvity.sDbAdapter
						.getSlotsList(spLevels.getSelectedItem().toString())
						.size(); i++) {

					BookingInfo mBookInfo = ValidateUserActvity.sDbAdapter
							.getSlotsList(spLevels.getSelectedItem().toString())
							.get(i);
					if (!mBookInfo.getAvailable().equals("")
							&& !mBookInfo.getAvailable().equals("0")) {
						lstAvailableBlocks.add(mBookInfo.getSlotName());
					}

					lstAllBlocks.add(mBookInfo.getSlotName());

				}

				adpSlot = new ArrayAdapter<String>(mCtx,
						android.R.layout.simple_spinner_item,
						lstAvailableBlocks);

				adpSlot.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spSlots.setAdapter(adpSlot);

				// /------------
				// if (spLevels.getSelectedItemPosition() != 0) {

				ArrayList<BookingInfo> lstPremium = new ArrayList<BookingInfo>();
				ArrayList<BookingInfo> lstNormal = new ArrayList<BookingInfo>();

				llGvParent.setVisibility(View.VISIBLE);
				for (int i = 0; i < lstAllBlocks.size(); i++) {
					sBookingInfo = ValidateUserActvity.sDbAdapter
							.getSlotDetails(spLevels.getSelectedItem()
									.toString(), lstAllBlocks.get(i));

					if (sBookingInfo != null) {

						if (sBookingInfo.getParkingType().contains("Premium")) {
							lstPremium.add(sBookingInfo);
						} else if (sBookingInfo.getParkingType().contains(
								"Normal")) {
							lstNormal.add(sBookingInfo);
						}

						final CustomGridAdapter mDapPre = new CustomGridAdapter(
								mCtx, R.layout.row_grid, lstPremium);
						final CustomGridAdapter mDapNor = new CustomGridAdapter(
								mCtx, R.layout.row_grid, lstNormal);

						gvPremium.setNumColumns(getNoOfColumns(lstPremium
								.size()));
						gvNormal.setNumColumns(getNoOfColumns(lstNormal.size()));
						gvPremium.setAdapter(mDapPre);
						gvNormal.setAdapter(mDapNor);

					}

				}

				// } else {
				// llGvParent.setVisibility(View.INVISIBLE);
				// }

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		spSlots.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				if (spSlots.getSelectedItemPosition() != 0) {

					sBookingInfo = ValidateUserActvity.sDbAdapter
							.getSlotDetails(spLevels.getSelectedItem()
									.toString(), spSlots.getSelectedItem()
									.toString());

					for (int i = 0; i < gvNormal.getAdapter().getCount(); i++) {
						View v3 = gvNormal.getChildAt(i);
						CheckBox cb3 = (CheckBox) v3
								.findViewById(R.id.checkBox1);

						cb3.setChecked(false);

					}
					for (int i = 0; i < gvPremium.getAdapter().getCount(); i++) {
						View v3 = gvPremium.getChildAt(i);
						CheckBox cb3 = (CheckBox) v3
								.findViewById(R.id.checkBox1);

						cb3.setChecked(false);

					}

					for (int i = 0; i < gvNormal.getAdapter().getCount(); i++) {
						View v3 = gvNormal.getChildAt(i);
						CheckBox cb3 = (CheckBox) v3
								.findViewById(R.id.checkBox1);
						TextView tv = (TextView) v3
								.findViewById(R.id.item_text);
						if (tv.getTag().equals(
								spSlots.getSelectedItem().toString()))

							cb3.setChecked(true);

					}
					for (int i = 0; i < gvPremium.getAdapter().getCount(); i++) {
						View v3 = gvPremium.getChildAt(i);
						CheckBox cb3 = (CheckBox) v3
								.findViewById(R.id.checkBox1);
						TextView tv = (TextView) v3
								.findViewById(R.id.item_text);
						if (tv.getTag().equals(
								spSlots.getSelectedItem().toString()))

							cb3.setChecked(true);

					}
				} else {
					for (int i = 0; i < gvNormal.getAdapter().getCount(); i++) {
						View v3 = gvNormal.getChildAt(i);
						CheckBox cb3 = (CheckBox) v3
								.findViewById(R.id.checkBox1);

						cb3.setChecked(false);

					}
					for (int i = 0; i < gvPremium.getAdapter().getCount(); i++) {
						View v3 = gvPremium.getChildAt(i);
						CheckBox cb3 = (CheckBox) v3
								.findViewById(R.id.checkBox1);

						cb3.setChecked(false);

					}
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		gvPremium.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {

				CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox1);
				TextView tv = (TextView) v.findViewById(R.id.item_text);

				spSlots.setSelection(getPostionFromAdapter(tv.getTag()
						.toString(), adpSlot));
				for (int i = 0; i < arg0.getCount(); i++) {

					View v2 = arg0.getChildAt(i);
					CheckBox cb2 = (CheckBox) v2.findViewById(R.id.checkBox1);
					if (cb.getTag() != cb2.getTag()) {
						cb2.setChecked(false);
					} else {
						cb2.setChecked(true);
					}
				}

				for (int i = 0; i < gvNormal.getAdapter().getCount(); i++) {
					View v3 = gvNormal.getChildAt(i);
					CheckBox cb3 = (CheckBox) v3.findViewById(R.id.checkBox1);

					cb3.setChecked(false);

				}
			}
		});

		gvNormal.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox1);
				TextView tv = (TextView) v.findViewById(R.id.item_text);

				spSlots.setSelection(getPostionFromAdapter(tv.getTag()
						.toString(), adpSlot));

				for (int i = 0; i < arg0.getCount(); i++) {

					View v2 = arg0.getChildAt(i);
					CheckBox cb2 = (CheckBox) v2.findViewById(R.id.checkBox1);
					if (cb.getTag() != cb2.getTag()) {
						cb2.setChecked(false);
					} else {
						cb2.setChecked(true);
					}
				}
				for (int i = 0; i < gvPremium.getAdapter().getCount(); i++) {
					View v3 = gvPremium.getChildAt(i);
					CheckBox cb3 = (CheckBox) v3.findViewById(R.id.checkBox1);

					cb3.setChecked(false);

				}
			}
		});

	}

	private int getNoOfColumns(int number) {

		int noOfCol;

		if (number % 2 == 0) {
			System.out.println("You entered an even number.");

			noOfCol = number / 2;
		}

		else {
			noOfCol = number + 1 / 2;
			System.out.println("You entered an odd number.");
		}

		return noOfCol;
	}

	@SuppressWarnings("unchecked")
	private int getPostionFromAdapter(String str, ArrayAdapter<String> adp) {

		ArrayList<String> underlying = new ArrayList<String>();
		for (int i = 0; i < adp.getCount(); i++)
			underlying.add(adp.getItem(i));

		return underlying.indexOf(str);
	}
	public void showBookingScreen(View v) {
		// int avilable = Integer.parseInt(sBookingInfo.getAvailable());
		//
		// if (avilable == 0) {
		// Helper.showToast(mCtx,
		// "No slots availble, Please try in different block",
		// Toast.LENGTH_LONG);
		// } else {
		// Intent mIntent = new Intent(getApplicationContext(),
		// BookingActivity.class);
		// startActivity(mIntent);
		// }

		if (spSlots.getSelectedItemPosition() == 0) {
			Helper.showToast(mCtx, "Please select parking block.",
					Toast.LENGTH_LONG);
		} else {
			sBookingSpot = spLevels.getSelectedItem().toString() + "/"
					+ spSlots.getSelectedItem().toString();

			Intent mIntent = new Intent(getApplicationContext(),
					BookingActivity.class);
			startActivity(mIntent);
		}

	}
	public class FetchParkingSlots extends AsyncTask<String, Void, Void> {
		WebcallResponse response;
		private ProgressDialog mPd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			mPd = new ProgressDialog(mCtx);
			mPd.setMessage(Html.fromHtml("Loading..."));
			mPd.getWindow().setGravity(Gravity.CENTER);

			mPd.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

						}
					});

			mPd.show();

		}
		@Override
		protected Void doInBackground(String... params) {

			try {
				CallWebServices callWebservice = new CallWebServices(
						SoapObjectsHelper.crearteParkingSlotsSoapObject(
								params[0], params[1],
								Webmethods.GetBookingInfo.toString()),
						Webmethods.GetBookingInfo);

				response = callWebservice.makeWebserviceRequest();
				ValidateUserActvity.sDbAdapter.truncateBookingInfoTable();
				BookingInfoParser.parse(response.getResultData());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				if (mPd != null) {
					mPd.dismiss();
				}
				adpLevel = new ArrayAdapter<String>(mCtx,
						android.R.layout.simple_spinner_item,
						ValidateUserActvity.sDbAdapter.getLevelsList());
				adpLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spLevels.setAdapter(adpLevel);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
