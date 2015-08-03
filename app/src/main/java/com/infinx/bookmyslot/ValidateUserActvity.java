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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.infinx.database.DBAdapter;
import com.infinx.model.UserDetails;
import com.infinx.parsers.UserDetailsParser;
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
 *      Created On 02-Dec-2014 ,7:26:12 pm
 */
public class ValidateUserActvity extends Activity {

	Context mCtx;

	ProgressBar mPd;

	Helper mHelper = null;

	public static UserDetails sUserDtls = null;

	public static DBAdapter sDbAdapter = null;

	public static boolean isFromPayment = false;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validate_user);
		mPd = (ProgressBar) findViewById(R.id.progressBar1);

		mCtx = this;

		mHelper = new Helper();

		sUserDtls = new UserDetails();

		sDbAdapter = new DBAdapter(mCtx);

		showAppVersion();

	}

	/**
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (Helper.isConnectedToInternet(mCtx)) {

			if (Helper.isGpsCompatible(mCtx)) {

				if (Helper.isGpsEnabled(mCtx)) {
					new Handler().postDelayed(new Runnable() {

						// Using handler with postDelayed called runnable run
						// method

						@Override
						public void run() {
							ValidateUser mValidateUser = new ValidateUser();

							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

								mValidateUser
										.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
							} else {

								mValidateUser.execute();
							}

						}
					}, 2 * 1000); // wait for 5 seconds

				} else {
					mHelper.showAlertWithInput(mCtx, "", Helper.getString(
							R.string.info_gps_enable_msg, mCtx), Helper
							.switchToGpsSettings(mCtx), "Enable", "Cancel");
				}

			} else {
				mHelper.showAlert(mCtx, "",
						Helper.getString(R.string.info_no_gps_msg, mCtx), null);
			}

		} else {
			mHelper.showAlertWithInput(mCtx, "",
					Helper.getString(R.string.info_no_internet_msg, mCtx),
					Helper.switchToWirelessSettings(mCtx), "Enable", "Cancel");
		}
	}

	public class ValidateUser extends AsyncTask<Void, Void, Void> {
		WebcallResponse response;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				CallWebServices callWebservice = new CallWebServices(
						SoapObjectsHelper.validateUserSoapObject(
								Helper.getIMEI(mCtx),
								Webmethods.GetValidUser.toString()),
						Webmethods.GetValidUser);

				response = callWebservice.makeWebserviceRequest();
				UserDetailsParser.parse(response.getResultData());

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

			mPd.setVisibility(View.GONE);

			if (response != null)
				if (sUserDtls.getUserId().equals("0"))

					showRegistrationScreen(null);
				else
					showPickSpotScreen(null);

			else
				mHelper.showAlert(mCtx, "",
						Helper.getString(R.string.str_netwrk_erroe, mCtx),
						exit());
		}
	}

	private Runnable exit() {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		};

		return r;
	}

	public void showRegistrationScreen(View v) {
		Intent mIntent = new Intent(getApplicationContext(),
				RegistrationActivity.class);
		startActivity(mIntent);
	}

	public void showPickSpotScreen(View v) {
		Intent mIntent = new Intent(getApplicationContext(),
				PickSpotActivity.class);
		startActivity(mIntent);
	}

	private void showAppVersion() {
		try {
			PackageInfo pinfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);

			// int versionNumber = pinfo.versionCode;
			String versionName = pinfo.versionName;

			TextView tvAppVer = (TextView) findViewById(R.id.tv_app_version);
			tvAppVer.setText("version  " + versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
