/*******************************************************************************
 * Copyright (c) 2014, INFINX (TIS Pvt.
 * Ltd.)(www.infinxservices.com). Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may
 * obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless
 * required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/

package com.infinx.webservices;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355 Created On 02-Sep-2014 09:24:21 AM
 */
public class CallWebServices {

	public SoapObject obj;
	public Webmethods whichMethod;

	public SoapObject getParams() {
		return obj;
	}

	public void setParams(SoapObject obj) {
		this.obj = obj;
	}

	public CallWebServices(SoapObject obj, Webmethods methodToCall) {
		super();
		if (obj == null /* || obj.size() == 0 */) {
			return;
		}
		this.obj = obj;
		this.whichMethod = methodToCall;
	}

	/**
	 * Calls the webservice with provided params
	 * 
	 */
	public WebcallResponse makeWebserviceRequest() {

		String response = null;

		String soapAction = AppConfig.NAMESPACE;

		// Define the method to call
		switch (this.whichMethod) {
			case GetValidUser :

				break;

			case RegisterUser :

				break;

			case GetLocationMaster :

				break;

			case InsertCreditCardDetail :

				break;

			case GetBookingInfo :

				break;

			case InsertBookingTransaction :

				break;

			case GetCreditCardInfo :

				break;

			default :

				break;

		}

		try {
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.dotNet = true;

			envelope.setOutputSoapObject(obj);

			Log.v("Request Data", obj.toString());

			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					AppConfig.URL);

			try {

				androidHttpTransport.call(AppConfig.ServiceContract
						+ whichMethod, envelope);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response = envelope.getResponse().toString();

			Log.v("WEB RESPONSE", response.toString());

		} catch (Exception e) {
			e.printStackTrace();

			Log.v("Web service error", e.getMessage().toString());
		}
		return parseJsonResponse(response);
	}

	/**
	 * Parse Json response
	 */

	private WebcallResponse parseJsonResponse(Object response) {
		WebcallResponse mWebResponse = new WebcallResponse();

		if (response != null) {
			String mJsonResponse = response.toString();
			try {

				JSONObject json_data = new JSONObject(mJsonResponse);

				String mResult = json_data.getString("Result").toString();
				String mResponseMsg = json_data.getString("ResponseMsg")
						.toString();
				String mResultData = json_data.getString("ResultData")
						.toString();
				mWebResponse.setResult(mResult);
				mWebResponse.setResponseMsg(mResponseMsg);
				mWebResponse.setResultData(mResultData);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return mWebResponse;
		}
		return null;

	}

	public enum Webmethods {
		GetValidUser, GetLocationMaster, RegisterUser, InsertCreditCardDetail, GetBookingInfo, InsertBookingTransaction, GetCreditCardInfo, GetBookingHistory;
	}

}
