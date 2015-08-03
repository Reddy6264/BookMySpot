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
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.infinx.model.BookingHistoryObj;
import com.infinx.parsers.BookingHistoryParser;
import com.infinx.util.CustomHistoryAdapter;
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
 *      Created On 12-Dec-2014 ,6:36:50 pm
 */
public class BookingHistoryActivity extends Activity
		implements
			CreateNdefMessageCallback,
			OnKeyListener {

	// parameters for NFC programming
	NfcAdapter mNfcAdapter;

	// parameters for holding the messages and displaying them

	String messageList = "<:-))";
	String messageToSend = "";
	String messageToGet = "";
	ListView messagesLV;

	int size = 50; // Maximum size of a message

	Button btnPay;

	private Context mCtx;
	Helper mHelper = null;
	ListView lvHistory;
	TextView tvMsg;

	Animation animGrowFromMiddle;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking_history);

		mCtx = this;
		mHelper = new Helper();

		animGrowFromMiddle = AnimationUtils.loadAnimation(this,
				R.anim.grow_from_middle);

		lvHistory = (ListView) findViewById(R.id.lv_history);
		tvMsg = (TextView) findViewById(R.id.textView2);

//		lvHistory.setAnimation(animGrowFromMiddle);

		FetchBookingHistory mBookHistory = new FetchBookingHistory();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			mBookHistory.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					ValidateUserActvity.sUserDtls.getUserId());
		} else {

			mBookHistory.execute(ValidateUserActvity.sUserDtls.getUserId());
		}

		lvHistory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				BookingHistoryObj bObj = (BookingHistoryObj) arg0
						.getItemAtPosition(arg2);
				messageToSend = bObj.getBookingCode();

				// etCardNum.setText("");
				// etValidity.setText("");
				// etCvv.setText("");
				Toast.makeText(getApplicationContext(),
						"Tap on check in device", Toast.LENGTH_LONG).show();
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

	public void get(String messageToGet) {

		// etCardNum.setText(messageToGet.split("~")[0]);
		// etValidity.setText(messageToGet.split("~")[1]);
		// etCvv.setSelection(Integer.parseInt(messageToGet.split("~")[2]));
		// btnPay.setText("Payment Recived");
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// If the event is a key-down event on the "enter" button
		if ((event.getAction() == KeyEvent.ACTION_DOWN)
				&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
			//
			// messageToSend = etCardNum.getText().toString() + "~"
			// + etValidity.getText().toString() + "~"
			// + String.valueOf(etCvv.getSelectedItemPosition());
			//
			// etCardNum.setText("");
			// etValidity.setText("");
			// etCvv.setSelection(0);
			// Toast.makeText(this,
			// "Touch another mobile to share the chat message",
			// Toast.LENGTH_LONG).show();

			return true;
		}
		return false;
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
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processIntent(getIntent());
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

	/**
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent mIntent = new Intent(mCtx, PickSpotActivity.class);
		startActivity(mIntent);
		super.onBackPressed();
	}

	public class FetchBookingHistory extends AsyncTask<String, Void, Void> {
		WebcallResponse response;
		private ProgressDialog mPd;
		ArrayList<BookingHistoryObj> lstHistory = new ArrayList<BookingHistoryObj>();

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
						SoapObjectsHelper.crearteHistorySoapObject(params[0],
								Webmethods.GetBookingHistory.toString()),
						Webmethods.GetBookingHistory);

				response = callWebservice.makeWebserviceRequest();
				lstHistory = BookingHistoryParser.parse(response
						.getResultData());

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
			
			if(lstHistory.size()>0){
				CustomHistoryAdapter adp = new CustomHistoryAdapter(
						BookingHistoryActivity.this, R.layout.custom_history_row,
						lstHistory);
				lvHistory.setAdapter(adp);
			}else{
				tvMsg.setVisibility(View.VISIBLE);
			}
			

		}
	}
}
