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

import java.nio.charset.Charset;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infinx.model.CreditCardDetails;
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
 *      Created On 01-Dec-2014 ,11:53:33 am
 */
public class CreditCardDetailsActivity extends Activity
		implements
			CreateNdefMessageCallback {

	// parameters for NFC programming
	NfcAdapter mNfcAdapter;

	// parameters for holding the messages and displaying them

	String messageList = "<:-))";
	String messageToSend = "";
	String messageToGet = "";
	ListView messagesLV;

	int size = 50; // Maximum size of a message
	EditText tvCardNum, tvNameOnCard, tvExpiryDate;
	Spinner spCardType;

	private Context mCtx;
	Helper mHelper = null;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_card_details);

		mCtx = this;
		mHelper = new Helper();

		tvCardNum = (EditText) findViewById(R.id.tv_card_num);
		tvNameOnCard = (EditText) findViewById(R.id.tv_name_on_card);
		tvExpiryDate = (EditText) findViewById(R.id.tv_expiry_date);
		spCardType = (Spinner) findViewById(R.id.sp_card_type);
		spCardType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
				((TextView) arg0.getChildAt(0)).setTextAppearance(mCtx, R.style.Custom_EditTextStyle);
//		        ((TextView) arg0.getChildAt(0)).setTextSize(5);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			Toast.makeText(this, "No available NFC adapter", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mNfcAdapter.setNdefPushMessageCallback(this, this);

		// Parse messageList
		String thisMessage;
		char token;
		int start = 0;
		int end;
		while (messageList.length() > 0) {
			token = messageList.substring(start, start + 1).charAt(0);
			end = messageList.substring(start + 1).indexOf(token) + 1;
			thisMessage = messageList.substring(start, end + 1);

			start = end + 1;

			if (messageList.substring(start) != null)
				messageList = messageList.substring(start);
			else
				messageList = "";

			start = 0;
		}
	}

	public void storeCardDetails(View v) {

		if (Helper.isConnectedToInternet(mCtx)) {
			if (validateForm()) {

				CreditCardDetails mCardDtls = new CreditCardDetails();
				mCardDtls.setUserId(ValidateUserActvity.sUserDtls.getUserId());
				mCardDtls.setCardNumber(tvCardNum.getText().toString());
				mCardDtls.setCardHolderName(tvNameOnCard.getText().toString());
				mCardDtls.setExpiryDate(tvExpiryDate.getText().toString());
				mCardDtls.setCardType(spCardType.getSelectedItem().toString());

				StoreCreditCardDetails mStroeCard = new StoreCreditCardDetails();

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

					mStroeCard.executeOnExecutor(
							AsyncTask.THREAD_POOL_EXECUTOR, mCardDtls);
				} else {

					mStroeCard.execute(mCardDtls);
				}

			} else {
				mHelper.showAlert(mCtx, "",
						"Please tap on NFC enabled card, to add card details",
						null);
			}
		} else {
			mHelper.showAlertWithInput(mCtx, "",
					Helper.getString(R.string.info_no_internet_msg, mCtx),
					Helper.switchToWirelessSettings(mCtx), "Enable", "Cancel");
		}

	}

	public boolean validateForm() {

		boolean isFormValid = true;
		if (tvCardNum.getText().toString().equals("")) {

			isFormValid = false;

		} else if (tvNameOnCard.getText().toString().equals("")) {

			isFormValid = false;

		} else if (tvExpiryDate.getText().toString().equals("")) {

			isFormValid = false;

		} else if (spCardType.getSelectedItemPosition() == 0) {

			isFormValid = false;

		}
		return isFormValid;
	}

	public void showPickScreen(View v) {

		if (ValidateUserActvity.isFromPayment) {
			finish();
			onBackPressed();
			//
			// final Handler handler = new Handler();
			// handler.postDelayed(new Runnable() {
			// @Override
			// public void run() {
			// onBackPressed();
			// }
			// }, 100);
		} else {
			Intent mIntent = new Intent(getApplicationContext(),
					PickSpotActivity.class);
			startActivity(mIntent);
		}

	}
	public void get(String messageToGet) {

		tvCardNum.setText(messageToGet.split("~")[0]);

		tvNameOnCard.setText(messageToGet.split("~")[1]);
		tvExpiryDate.setText(messageToGet.split("~")[2]);
		spCardType.setSelection(Integer.parseInt(messageToGet.split("~")[3]));

	}

	public class StoreCreditCardDetails
			extends
				AsyncTask<CreditCardDetails, Void, Void> {
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
		protected Void doInBackground(CreditCardDetails... params) {

			try {
				CallWebServices callWebservice = new CallWebServices(
						SoapObjectsHelper.createCreditCardSoapObject(params[0],
								Webmethods.InsertCreditCardDetail.toString()),
						Webmethods.InsertCreditCardDetail);

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
			mPd.dismiss();

			if (response != null)
				if (response.getResultData().equalsIgnoreCase("True")) {

					showPickScreen(null);
					Helper.showToast(mCtx, response.getResponseMsg(),
							Toast.LENGTH_LONG);

				} else {
					if (ValidateUserActvity.isFromPayment) {
						Runnable r = new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								finish();
							}
						};
						mHelper.showAlert(mCtx, "", response.getResponseMsg(),
								r);
					} else {
						mHelper.showAlert(mCtx, "", response.getResponseMsg(),
								null);
					}
				}

			else

			if (ValidateUserActvity.isFromPayment) {
				Runnable r = new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						finish();
					}
				};

				mHelper.showAlert(mCtx, "",
						Helper.getString(R.string.str_netwrk_erroe, mCtx), r);
			} else {
				mHelper.showAlert(mCtx, "",
						Helper.getString(R.string.str_netwrk_erroe, mCtx), null);
			}

		}

	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		NdefMessage message = create_MIME_NdefMessage("application/nfcchat",
				messageToSend);
		messageToSend = "";
		return message;
	}

	public NdefMessage create_MIME_NdefMessage(String mimeType, String payload) {
		NdefRecord mimeRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				mimeType.getBytes(), new byte[0], payload.getBytes(Charset
						.forName("US-ASCII")));
		NdefMessage message = new NdefMessage(new NdefRecord[]{mimeRecord});
		return message;
	}

	@Override
	public void onResume() {
		super.onResume();

		if (Helper.isNfcCompatible(mCtx)) {

			if (Helper.isNfcEnabled(mCtx)) {
				if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent()
						.getAction())) {
					processIntent(getIntent());
				}
			} else {
				mHelper.showAlertWithInput(mCtx, "",
						Helper.getString(R.string.info_nfc_enable_msg, mCtx),
						Helper.switchToWirelessSettings(mCtx), "Enable",
						"Cancel");
			}

		} else {
			mHelper.showAlert(mCtx, "",
					Helper.getString(R.string.info_no_nfc_msg, mCtx), null);
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
	}

	void processIntent(Intent intent) {
		NdefMessage[] messages = getNdefMessages(getIntent());

		for (int i = 0; i < messages.length; i++) {
			for (int j = 0; j < messages[0].getRecords().length; j++) {
				NdefRecord record = messages[i].getRecords()[j];
				String payload = new String(record.getPayload());
				get(payload);
			}
		}
	}

	NdefMessage[] getNdefMessages(Intent intent) {
		NdefMessage[] msgs = null;
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
			Parcelable[] rawMsgs = intent
					.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			} else {
				byte[] empty = new byte[]{};
				NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
						empty, empty, empty);
				NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
				msgs = new NdefMessage[]{msg};
			}
		} else {
			Log.d("NFC Chat", "Unknown intent.");
			finish();
		}
		return msgs;
	}
}