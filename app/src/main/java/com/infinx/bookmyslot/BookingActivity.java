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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infinx.model.BookingInfo;
import com.infinx.model.ConfirmedBookingDtls;
import com.infinx.model.CreditCardDetails;
import com.infinx.parsers.CardDetailsParser;
import com.infinx.util.Helper;
import com.infinx.webservices.CallWebServices;
import com.infinx.webservices.CallWebServices.Webmethods;
import com.infinx.webservices.SoapObjectsHelper;
import com.infinx.webservices.WebcallResponse;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355
 * 
 *      Created On 02-Dec-2014 ,6:18:45 pm
 */
public class BookingActivity extends Activity {

	private Context mCtx;
	Helper mHelper = null;

	Spinner spDuration, spCards;
	TextView tvCharge, tvMall, tvType, tvSpot, tvArrivalTime;
	EditText etVehicleNum;
	ImageView ivAddCard;

	BookingInfo mBookingInfo = null;
	CreditCardDetails mCardInfo = null;

	public static ArrayList<CreditCardDetails> sLstSavedCards = null;
	public int mSpinnerState = 0;
	public boolean isFirstTime = true;

	String mDuration = "0";
	String mBookingCardId = "";
	float mTotalCharge = 0.f;
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking_details);
		
		LinearLayout rlParent = (LinearLayout) findViewById(R.id.rl_parent);

		mCtx = this;
		mHelper = new Helper();
		
		
//		Helper.playAnimation(rlParent, R.anim.slide_in_left, mCtx);

		mBookingInfo = new BookingInfo();
		mCardInfo = new CreditCardDetails("", "Saved Cards", "", "", "", "", "");
		sLstSavedCards = new ArrayList<CreditCardDetails>();
		sLstSavedCards.add(mCardInfo);

		spDuration = (Spinner) findViewById(R.id.sp_duration);
		spCards = (Spinner) findViewById(R.id.sp_card);
		tvCharge = (TextView) findViewById(R.id.tv_charge);
		etVehicleNum = (EditText) findViewById(R.id.et_vehicle_num);
		ivAddCard = (ImageView) findViewById(R.id.iv_add_card);

		//

		tvMall = (TextView) findViewById(R.id.tv_b_mall);
		tvType = (TextView) findViewById(R.id.tv_type);
		tvSpot = (TextView) findViewById(R.id.tv_b_spot);
		tvArrivalTime = (TextView) findViewById(R.id.tv_b_date);

		tvMall.setText(ParkinigPlaceActivity.sMallName);
		tvType.setText(ParkinigPlaceActivity.sBookingInfo.getParkingType());
		tvSpot.setText(ParkinigPlaceActivity.sBookingSpot);
		tvArrivalTime.setText(ParkinigPlaceActivity.sBookingDateTime);

		spDuration.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				if (spDuration.getSelectedItemPosition() != 0) {
					float mDur = Float.parseFloat(spDuration.getSelectedItem()
							.toString().split(" ")[0]);

					mDuration = String.valueOf(mDur);

					float mCharge = Float
							.parseFloat(ParkinigPlaceActivity.sBookingInfo
									.getRate());
					mTotalCharge = mDur * mCharge;

					tvCharge.setText(String.valueOf(mTotalCharge));
					etVehicleNum.setFocusable(true);
					etVehicleNum.requestFocus();

					etVehicleNum.setFocusableInTouchMode(true);
					//
				} else {
					etVehicleNum.setFocusable(false);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void showCreditCardScreen(View v) {
		Intent mIntent = new Intent(getApplicationContext(),
				CreditCardDetailsActivity.class);
		ValidateUserActvity.isFromPayment = true;
		startActivity(mIntent);
	}
	/**
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		FetchCardDetsils mFetchCardDtls = new FetchCardDetsils();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			mFetchCardDtls.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					ValidateUserActvity.sUserDtls.getUserId());
		} else {

			mFetchCardDtls.execute(ValidateUserActvity.sUserDtls.getUserId());
		}

	}

	/**
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub

		mSpinnerState = spCards.getSelectedItemPosition();
		super.onPause();
	}
	public void book(View v) {

		if (validateForm()) {

			ConfirmedBookingDtls mConDtls = new ConfirmedBookingDtls(
					ValidateUserActvity.sUserDtls.getUserId(),
					ParkinigPlaceActivity.sBookingInfo.getSlotId(),
					ParkinigPlaceActivity.sBookingInfo.getRateId(),
					etVehicleNum.getText().toString(),
					ParkinigPlaceActivity.sFormattedDateTime, mDuration,
					String.valueOf(mTotalCharge), mBookingCardId);

			MakeBooking mBooking = new MakeBooking();

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

				mBooking.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
						mConDtls);
			} else {

				mBooking.execute(mConDtls);
			}
		}

	}

	public boolean validateForm() {
		boolean isFormValid = false;

		if (spDuration.getSelectedItemPosition() == 0) {
			isFormValid = false;
			Helper.showToast(mCtx, "Please select parking duration",
					Toast.LENGTH_LONG);
		} else if (etVehicleNum.getText().toString().equals("")) {
			isFormValid = false;
			etVehicleNum.requestFocus();
			Helper.showToast(mCtx, "Please enter vehicle number",
					Toast.LENGTH_LONG);
		} else if (spCards.getSelectedItemPosition() == 0) {
			isFormValid = false;
			Helper.showToast(mCtx, "Please select payment", Toast.LENGTH_LONG);
		} else {
			isFormValid = true;
		}

		return isFormValid;

	}
	public class MakeBooking
			extends
				AsyncTask<ConfirmedBookingDtls, Void, Void> {
		WebcallResponse response;
		private ProgressDialog mPd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			mPd = new ProgressDialog(mCtx);
			mPd.setMessage(Html.fromHtml("Processing..."));
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
		protected Void doInBackground(ConfirmedBookingDtls... params) {

			try {
				CallWebServices callWebservice = new CallWebServices(
						SoapObjectsHelper.createConfirmBookingSoapObject(
								params[0],
								Webmethods.InsertBookingTransaction.toString()),
						Webmethods.InsertBookingTransaction);

				response = callWebservice.makeWebserviceRequest();

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
			if (mPd != null) {
				mPd.dismiss();
			}
			Runnable r = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent mIntent = new Intent(mCtx,
							BookingHistoryActivity.class);
					startActivity(mIntent);
				}
			};

			if (response != null)
				if (response.getResult().contains("SUCCESS"))
					mHelper.showAlert(mCtx, "", response.getResponseMsg(), r);

				else

					mHelper.showAlert(mCtx, "", response.getResponseMsg(), null);

			else
				mHelper.showAlert(mCtx, "",
						Helper.getString(R.string.str_netwrk_erroe, mCtx), null);

		}
	}

	public class FetchCardDetsils extends AsyncTask<String, Void, Void> {
		WebcallResponse response;
		private ProgressDialog mPd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if (isFirstTime) {
				mPd = new ProgressDialog(mCtx);
				mPd.setMessage(Html.fromHtml("Loading..."));
				mPd.getWindow().setGravity(Gravity.CENTER);

				mPd.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

							}
						});

				mPd.show();
			}

		}
		@Override
		protected Void doInBackground(String... params) {

			try {
				CallWebServices callWebservice = new CallWebServices(
						SoapObjectsHelper.crearteCardInfoSoapObject(params[0],
								Webmethods.GetCreditCardInfo.toString()),
						Webmethods.GetCreditCardInfo);

				response = callWebservice.makeWebserviceRequest();
				CardDetailsParser.parse(response.getResultData());

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
			if (mPd != null) {
				mPd.dismiss();
			}
			isFirstTime = false;
			@SuppressWarnings("unchecked")
			Set<CreditCardDetails> unique = new LinkedHashSet<CreditCardDetails>(
					sLstSavedCards);
			sLstSavedCards = new ArrayList<CreditCardDetails>(unique);
			CardAdapter adpCards = new CardAdapter(mCtx,
					android.R.layout.simple_spinner_dropdown_item,
					sLstSavedCards);
			adpCards.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spCards.setAdapter(adpCards);
			spCards.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

					CreditCardDetails mCard = (CreditCardDetails) spCards
							.getAdapter().getItem(arg2);
					mBookingCardId = mCard.getCardId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});

			spCards.setSelection(mSpinnerState);

		}
	}

	public class CardAdapter extends ArrayAdapter<CreditCardDetails> {

		// Your sent context
		private Context context;
		// Your custom values for the spinner (User)
		private List<CreditCardDetails> values;

		/**
		 * @param context
		 * @param resource
		 * @param textViewResourceId
		 * @param objects
		 */
		public CardAdapter(Context context, int textViewResourceId,
				List<CreditCardDetails> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub

			this.context = context;
			this.values = objects;
		}

		public int getCount() {
			return values.size();
		}

		public CreditCardDetails getItem(int position) {
			return values.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		// And the "magic" goes here
		// This is for the "passive" state of the spinner
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// I created a dynamic TextView here, but you can reference your own
			// custom layout for each spinner item
			TextView label = new TextView(context);
			label.setTextColor(Color.BLACK);
			// Then you can get the current item using the values array (Users
			// array) and the current position
			// You can NOW reference each method you has created in your bean
			// object (User class)

			if (position == 0) {
				label.setText(values.get(position).getCardNumber());
			} else {
				label.setText(values.get(position).getCardNumber() + " - "
						+ values.get(position).getCardType());
			}

			// And finally return your dynamic (or custom) view for each spinner
			// item
			return label;
		}

		// And here is when the "chooser" is popped up
		// Normally is the same view, but you can customize it if you want
		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			TextView label = new TextView(context);
			label.setTextColor(Color.BLACK);
			label.setPadding(10, 10, 10, 10);

			if (position == 0) {
				label.setText(values.get(position).getCardNumber());
			} else {
				label.setText(values.get(position).getCardNumber() + " - "
						+ values.get(position).getCardType());
			}

			// label.setText(values.get(position).getCardNumber());

			return label;
		}
	}
}
