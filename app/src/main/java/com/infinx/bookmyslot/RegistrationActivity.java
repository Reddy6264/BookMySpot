package com.infinx.bookmyslot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.infinx.model.RegistrationDetails;
import com.infinx.parsers.UserDetailsParser;
import com.infinx.util.Helper;
import com.infinx.webservices.CallWebServices;
import com.infinx.webservices.CallWebServices.Webmethods;
import com.infinx.webservices.SoapObjectsHelper;
import com.infinx.webservices.WebcallResponse;

public class RegistrationActivity extends Activity {

	EditText etFirstName, etLastName, etMobileNum, etEmailId;
	private Context mCtx;
	Helper mHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		mCtx = this;
		mHelper = new Helper();

		etFirstName = (EditText) findViewById(R.id.et_first_name);
		etLastName = (EditText) findViewById(R.id.et_last_name);
		etMobileNum = (EditText) findViewById(R.id.et_mobile_number);
		etEmailId = (EditText) findViewById(R.id.et_email_id);

		etEmailId
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.register || id == EditorInfo.IME_NULL) {
							registerUser(null);
							return true;
						}
						return false;
					}
				});

	}

	public class Register extends AsyncTask<RegistrationDetails, Void, Void> {
		WebcallResponse response;
		private ProgressDialog mPd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mPd = new ProgressDialog(mCtx);
			mPd.setMessage(Html.fromHtml("Registering user..."));
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
		protected Void doInBackground(RegistrationDetails... params) {

			try {
				CallWebServices callWebservice = new CallWebServices(
						SoapObjectsHelper.createRegistrationObj(params[0],
								Webmethods.RegisterUser.toString()),
						Webmethods.RegisterUser);

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
			mPd.dismiss();

			if (response != null)
				if (!ValidateUserActvity.sUserDtls.getUserId().equals("1"))
					showCreditCardScreen(null);
				else

					mHelper.showAlert(mCtx, "", response.getResponseMsg(), null);

			else
				mHelper.showAlert(mCtx, "",
						Helper.getString(R.string.str_netwrk_erroe, mCtx), null);
		}

		// if (response != null)
		// if (response.getResultData().equalsIgnoreCase("True"))
		// showCreditCardScreen(null);
		// else
		// mHelper.showAlert(mCtx, "", response.getResponseMsg(), null);
		// else
		// mHelper.showAlert(mCtx, "",
		// Helper.getString(R.string.str_netwrk_erroe, mCtx), null);
		//
		// }

	}

	public void registerUser(View v) {

		if (Helper.isConnectedToInternet(mCtx)) {
			if (validateForm()) {

				RegistrationDetails mRegDtls = new RegistrationDetails();

				mRegDtls.setFirstName(etFirstName.getText().toString());
				mRegDtls.setLastName(etLastName.getText().toString());
				mRegDtls.setMobileNum(etMobileNum.getText().toString());
				mRegDtls.setEmailId(etEmailId.getText().toString());
				mRegDtls.setImeiNumber(Helper.getIMEI(mCtx));

				Register mreg = new Register();

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

					mreg.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
							mRegDtls);
				} else {

					mreg.execute(mRegDtls);
				}
			}
		} else {
			mHelper.showAlertWithInput(mCtx, "",
					Helper.getString(R.string.info_no_internet_msg, mCtx),
					Helper.switchToWirelessSettings(mCtx), "Enable", "Cancel");
		}

	}

	public void showCreditCardScreen(View v) {
		Intent mIntent = new Intent(getApplicationContext(),
				CreditCardDetailsActivity.class);
		ValidateUserActvity.isFromPayment = false;
		startActivity(mIntent);
	}

	public boolean validateForm() {

		boolean isFormValid = false;

		if (etFirstName.getText().toString().equals("")) {
			isFormValid = false;
			etFirstName.requestFocus();
			etFirstName.setError(Helper.getString(
					R.string.prompt_edittext_error, mCtx));
		} else {
			isFormValid = true;
		}

		if (etLastName.getText().toString().equals("")) {
			isFormValid = false;
			etLastName.requestFocus();
			etLastName.setError(Helper.getString(
					R.string.prompt_edittext_error, mCtx));
		} else {
			isFormValid = true;
		}

		if (etMobileNum.getText().toString().equals("")) {
			isFormValid = false;
			etMobileNum.requestFocus();
			etMobileNum.setError(Helper.getString(
					R.string.prompt_edittext_error, mCtx));
		} else {
			isFormValid = true;
		}

		if (etMobileNum.getText().length() < 10) {
			isFormValid = false;
			etMobileNum.requestFocus();
			etMobileNum.setError("Invalid mobile number");
		} else {
			isFormValid = true;
		}

		if (etEmailId.getText().toString().equals("")) {
			isFormValid = false;
			etEmailId.requestFocus();
			etEmailId.setError(Helper.getString(R.string.prompt_edittext_error,
					mCtx));
		} else {
			isFormValid = true;
		}

		if (!etEmailId.getText().toString().contains("@")) {
			isFormValid = false;
			etEmailId.requestFocus();
			etEmailId.setError("Inavlid email id");
		} else {
			isFormValid = true;
		}
		return isFormValid;

	}
}
