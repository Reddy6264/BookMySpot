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


/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355 Created On
 * 02-Sep-2014 11:37:38 AM
 */
public class WebcallResponse {
	
	public String		result		= "";
	public boolean		hasError;
	public String		responseMsg	= "";
	
	public String		response	= "";
	public String		resultData	= null;
	
	public boolean		status		= false;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getResultData() {
		return resultData;
	}

	public void setResultData(String resultData) {
		this.resultData = resultData;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
