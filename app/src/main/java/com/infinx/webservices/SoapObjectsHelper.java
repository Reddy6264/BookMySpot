/**************************************************************************
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
 ***************************************************************************/

package com.infinx.webservices;

import org.ksoap2.serialization.SoapObject;

import com.infinx.model.ConfirmedBookingDtls;
import com.infinx.model.CreditCardDetails;
import com.infinx.model.RegistrationDetails;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355 Created On 04-Sep-2014 ,11:55:03 AM
 */
public class SoapObjectsHelper {

	/**
	 * create registration soap object
	 * 
	 * @param regDtls
	 * @param method
	 * @param objName
	 * @return
	 */
	public static SoapObject createRegistrationObj(RegistrationDetails regDtls,
			String method) {
		SoapObject registrationSoapObject = new SoapObject(
				AppConfig.SOAP_NAMESPACE, method);

		registrationSoapObject.addProperty("ImeiNumber",
				regDtls.getImeiNumber());
		registrationSoapObject.addProperty("FirstName", regDtls.getFirstName());
		registrationSoapObject.addProperty("LastName", regDtls.getLastName());
		registrationSoapObject.addProperty("MobileNumber",
				regDtls.getMobileNum());
		registrationSoapObject.addProperty("EmailId", regDtls.getEmailId());

		return registrationSoapObject;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * create user name soap object
	 * 
	 * @param method
	 * @param mzone
	 * @param mDate
	 * @return
	 */
	public static SoapObject validateUserSoapObject(String imeiNum,
			String method) {
		SoapObject mUserSoapObj = new SoapObject(AppConfig.NAMESPACE, method);
		mUserSoapObj.addProperty("ImeiNumber", imeiNum);

		return mUserSoapObj;
	}

	// -----------------------------------------------------------------------------------
	/**
	 * create notification soap object
	 * 
	 * @param method
	 * @param notification
	 *            id
	 * 
	 * @return
	 */
	public static SoapObject createCreditCardSoapObject(
			CreditCardDetails mCardDtls, String method) {
		SoapObject mCardSoapObj = new SoapObject(AppConfig.NAMESPACE, method);
		mCardSoapObj.addProperty("UserId", mCardDtls.getUserId());
		mCardSoapObj.addProperty("CardNumber", mCardDtls.getCardNumber());
		mCardSoapObj.addProperty("CreditCardExpiry", mCardDtls.getExpiryDate());
		mCardSoapObj.addProperty("CardHolderName",
				mCardDtls.getCardHolderName());
		mCardSoapObj.addProperty("CreditCardType", mCardDtls.getCardType());

		return mCardSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * create user name soap object
	 * 
	 * @param method
	 * @param mzone
	 * @param mDate
	 * @return
	 */
	public static SoapObject crearteCityMallSoapObject(String method) {
		SoapObject mMallSoapObj = new SoapObject(AppConfig.NAMESPACE, method);

		return mMallSoapObj;
	}
	// -----------------------------------------------------------------------------------
	public static SoapObject crearteParkingSlotsSoapObject(String locationId,
			String bookingDateTime, String method) {
		SoapObject mSlotoapObj = new SoapObject(AppConfig.NAMESPACE, method);
		mSlotoapObj.addProperty("LocationId", locationId);
		mSlotoapObj.addProperty("BookingArrivalTime", bookingDateTime);

		return mSlotoapObj;
	}
	// -----------------------------------------------------------------------------------

	public static SoapObject crearteCardInfoSoapObject(String userId,
			String method) {
		SoapObject mCardInfo = new SoapObject(AppConfig.NAMESPACE, method);
		mCardInfo.addProperty("UserId", userId);

		return mCardInfo;
	}
	// -----------------------------------------------------------------------------------

	public static SoapObject crearteHistorySoapObject(String userId,
			String method) {
		SoapObject mHistory = new SoapObject(AppConfig.NAMESPACE, method);
		mHistory.addProperty("UserId", userId);

		return mHistory;
	}
	// -----------------------------------------------------------------------------------

	public static SoapObject createConfirmBookingSoapObject(
			ConfirmedBookingDtls mbookingDtls, String method) {
		SoapObject mBookingSoapObj = new SoapObject(AppConfig.NAMESPACE, method);
		mBookingSoapObj.addProperty("UserId", mbookingDtls.getUserId());
		mBookingSoapObj.addProperty("SlotId", mbookingDtls.getSlotId());
		mBookingSoapObj.addProperty("RateId", mbookingDtls.getRateId());
		mBookingSoapObj.addProperty("CarRTONumber",
				mbookingDtls.getVehicleNum());
		mBookingSoapObj.addProperty("BookingArrivalTime  ",
				mbookingDtls.getArrivalTime());

		mBookingSoapObj.addProperty("Duration", mbookingDtls.getDuration());
		mBookingSoapObj
				.addProperty("BookingCost", mbookingDtls.getBooingCost());
		mBookingSoapObj.addProperty("BookingCardId",
				mbookingDtls.getBookingCardId());

		return mBookingSoapObj;
	}

	// -----------------------------------------------------------------------------------
	/**
	 * Create forgot password soap object
	 * 
	 * @param loginDtls
	 * @param method
	 * @param objName
	 * @return
	 */
	// public static SoapObject
	// createForgotPasswordSoapObject(
	// LoginDetails loginDtls, String method, String
	// objName) {
	// SoapObject forgotPasswordSoapObj = new
	// SoapObject(AppConfig.NAMESPACE,
	// method);
	//
	// SoapObject forgotPwdObj = new
	// SoapObject(AppConfig.NAMESPACE, objName);
	// SoapObject forgotPwdHelperSoapObj = new
	// SoapObject(AppConfig.NAMESPACE,
	// "ResetPasswordRequest");
	// forgotPwdHelperSoapObj.addProperty("LoginId",
	// loginDtls.getUserName());
	// // forgotPwdHelperSoapObj.addProperty("Password",
	// // loginDtls.getPassword());
	// forgotPwdHelperSoapObj.addProperty("LoginIdDecryptionKey",
	// loginDtls.getEncryptionKey());
	// forgotPwdHelperSoapObj
	// .addProperty("ImeiNo", loginDtls.getImeiNumbers());
	//
	// forgotPasswordSoapObj.addProperty(objName,
	// forgotPwdHelperSoapObj);
	// forgotPasswordSoapObj.addProperty(objName,
	// forgotPwdObj);
	//
	// return forgotPasswordSoapObj;
	// }

	// -----------------------------------------------------------------------------------

	/**
	 * Create news soap object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createNewsSoapObject(String fromDate,
			String toDate, String newsCategoryId, String method, String objName) {
		SoapObject mNewsSoapObj = new SoapObject(AppConfig.NAMESPACE, method);

		SoapObject newsObj = new SoapObject(AppConfig.NAMESPACE, objName);
		SoapObject newsObjHelperSoapObj = new SoapObject(AppConfig.NAMESPACE,
				"NewsRequest");
		newsObjHelperSoapObj.addProperty("NewsFromDate", fromDate);

		newsObjHelperSoapObj.addProperty("NewsToDate", toDate);

		newsObjHelperSoapObj.addProperty("NewsCategoryID", newsCategoryId);

		mNewsSoapObj.addProperty(objName, newsObjHelperSoapObj);
		mNewsSoapObj.addProperty(objName, newsObj);

		return mNewsSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create news like dislike object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createLikeDislikeSoapObject(String newsId,
			String likeDislike, String method, String objName) {
		SoapObject mNewsLikeDislikeSoapObj = new SoapObject(
				AppConfig.NAMESPACE, method);

		SoapObject newsLikeDislikeObj = new SoapObject(AppConfig.NAMESPACE,
				objName);
		SoapObject newsObjHelperSoapObj = new SoapObject(AppConfig.NAMESPACE,
				"NewsLikeDisLikeRequest");
		newsObjHelperSoapObj.addProperty("NewsID", newsId);

		newsObjHelperSoapObj.addProperty("NewsLikeDislike", likeDislike);

		mNewsLikeDislikeSoapObj.addProperty(objName, newsObjHelperSoapObj);
		mNewsLikeDislikeSoapObj.addProperty(objName, newsLikeDislikeObj);

		return mNewsLikeDislikeSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create advertisement hit object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createAdvertisementHitSoapObject(
			String advertisementId, String method, String objName) {
		SoapObject mAdvertisementHitSoapObj = new SoapObject(
				AppConfig.NAMESPACE, method);

		SoapObject AdvertisementHitObj = new SoapObject(AppConfig.NAMESPACE,
				objName);
		SoapObject AdvertisementHitObjHelperSoapObj = new SoapObject(
				AppConfig.NAMESPACE, "AdvertisementHitRequest");
		AdvertisementHitObjHelperSoapObj.addProperty("AdvertisementID",
				advertisementId);

		mAdvertisementHitSoapObj.addProperty(objName,
				AdvertisementHitObjHelperSoapObj);
		mAdvertisementHitSoapObj.addProperty(objName, AdvertisementHitObj);

		return mAdvertisementHitSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create appointment object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createAppointmentSoapObject(String appointmentId,
			String cityName, String sortBy, String designation,
			String formDate, String toDate, String method, String objName) {
		SoapObject mAppointmenttSoapObj = new SoapObject(AppConfig.NAMESPACE,
				method);

		SoapObject AppointmentObj = new SoapObject(AppConfig.NAMESPACE, objName);
		SoapObject AppointmentObjHelperSoapObj = new SoapObject(
				AppConfig.NAMESPACE, "AppointmentRequest");

		AppointmentObjHelperSoapObj.addProperty("AppointmentID", appointmentId);
		AppointmentObjHelperSoapObj
				.addProperty("AppointmentCityName", cityName);
		AppointmentObjHelperSoapObj.addProperty("AppointmentSortBy", sortBy);
		AppointmentObjHelperSoapObj.addProperty("AppointmentDesignation",
				designation);

		AppointmentObjHelperSoapObj
				.addProperty("AppointmentFromDate", formDate);
		AppointmentObjHelperSoapObj.addProperty("AppointmentToDate", toDate);

		mAppointmenttSoapObj.addProperty(objName, AppointmentObjHelperSoapObj);
		mAppointmenttSoapObj.addProperty(objName, AppointmentObj);

		return mAppointmenttSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create city designation object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createCityDesignationSoapObject(String method) {
		SoapObject mCityDesignationSoapObj = new SoapObject(
				AppConfig.NAMESPACE, method);

		return mCityDesignationSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create notices object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createNoticesSoapObject(String vesselName,
			String voyageNumber, String method, String objName) {
		SoapObject mNoticesSoapObj = new SoapObject(AppConfig.NAMESPACE, method);

		SoapObject NoticesObj = new SoapObject(AppConfig.NAMESPACE, objName);
		SoapObject NoticesHelperSoapObj = new SoapObject(AppConfig.NAMESPACE,
				"NoticesRequest");
		NoticesHelperSoapObj.addProperty("VesselName", vesselName);
		NoticesHelperSoapObj.addProperty("VoyageNo", voyageNumber);

		mNoticesSoapObj.addProperty(objName, NoticesHelperSoapObj);
		mNoticesSoapObj.addProperty(objName, NoticesObj);

		return mNoticesSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create vessel name & voyage number object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createVesselNameVoyageSoapObject(String method) {
		SoapObject mVesselNameVoyageSoapObj = new SoapObject(
				AppConfig.NAMESPACE, method);

		return mVesselNameVoyageSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create Pol Pod object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createPolPodSoapObject(String method) {
		SoapObject mPolPodSoapObj = new SoapObject(AppConfig.NAMESPACE, method);

		return mPolPodSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create vessel name object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createVesselNameSoapObject(String eta, String etd,
			String pol, String pod, String method, String objName) {
		SoapObject mVesselMovementNameSoapObj = new SoapObject(
				AppConfig.NAMESPACE, method);

		SoapObject VesselMovementObj = new SoapObject(AppConfig.NAMESPACE,
				objName);
		SoapObject VesselMovementObjHelperSoapObj = new SoapObject(
				AppConfig.NAMESPACE, "VesselNameRequest");
		VesselMovementObjHelperSoapObj.addProperty("ETA", eta);
		VesselMovementObjHelperSoapObj.addProperty("ETD", etd);
		VesselMovementObjHelperSoapObj.addProperty("POL", pol);

		VesselMovementObjHelperSoapObj.addProperty("POD", pod);

		mVesselMovementNameSoapObj.addProperty(objName,
				VesselMovementObjHelperSoapObj);
		mVesselMovementNameSoapObj.addProperty(objName, VesselMovementObj);

		return mVesselMovementNameSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create vessel movement object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createVesselMovementSoapObject(String eta,
			String etd, String pol, String pod, String vesselName,
			String method, String objName) {
		SoapObject mVesselMovementSoapObj = new SoapObject(AppConfig.NAMESPACE,
				method);

		SoapObject VesselMovementObj = new SoapObject(AppConfig.NAMESPACE,
				objName);
		SoapObject VesselMovementObjHelperSoapObj = new SoapObject(
				AppConfig.NAMESPACE, "VesselMovementRequest");
		VesselMovementObjHelperSoapObj.addProperty("ETA", eta);
		VesselMovementObjHelperSoapObj.addProperty("ETD", etd);
		VesselMovementObjHelperSoapObj.addProperty("POL", pol);

		VesselMovementObjHelperSoapObj.addProperty("POD", pod);

		VesselMovementObjHelperSoapObj.addProperty("VesselName", vesselName);

		mVesselMovementSoapObj.addProperty(objName,
				VesselMovementObjHelperSoapObj);
		mVesselMovementSoapObj.addProperty(objName, VesselMovementObj);

		return mVesselMovementSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create vessel movement input details object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createVesselMovementListSoapObject(String method) {

		SoapObject mVesselMovementSoapObj = new SoapObject(AppConfig.NAMESPACE,
				method);

		return mVesselMovementSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create vessel name & voyage number object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createHelpDeskSoapObject(String method) {
		SoapObject mHelpDeskSoapObj = new SoapObject(AppConfig.NAMESPACE,
				method);

		return mHelpDeskSoapObj;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Create contact us request object
	 * 
	 * @param method
	 * @return
	 */

	public static SoapObject createContactUsSoapObject(String cityName,
			String method, String objName) {
		SoapObject mContactUsSoapObj = new SoapObject(AppConfig.NAMESPACE,
				method);

		SoapObject contactUsDislikeObj = new SoapObject(AppConfig.NAMESPACE,
				objName);
		SoapObject contactUsHelperSoapObj = new SoapObject(AppConfig.NAMESPACE,
				"ContactUsRequest");
		contactUsHelperSoapObj.addProperty("BranchName", cityName);

		mContactUsSoapObj.addProperty(objName, contactUsHelperSoapObj);
		mContactUsSoapObj.addProperty(objName, contactUsDislikeObj);

		return mContactUsSoapObj;
	}

	// -----------------------------------------------------------------------------------
	/**
	 * Create soap header
	 * 
	 * @param details
	 * @return
	 */
	// public static Element buildAuthHeader(LoginDetails
	// loginDtls) {
	// // WebServiceHeader Element
	// Element header = new
	// Element().createElement(AppConfig.NAMESPACE,
	// "WebServiceHeader");
	//
	// // Username Element
	// Element username = new
	// Element().createElement(AppConfig.NAMESPACE,
	// "Username");
	// username.addChild(Node.TEXT,
	// loginDtls.getUserName());
	//
	// // Password Element
	// Element password = new
	// Element().createElement(AppConfig.NAMESPACE,
	// "Password");
	// password.addChild(Node.TEXT,
	// loginDtls.getPassword());
	//
	// // DecryptionKey Element
	// Element key = new
	// Element().createElement(AppConfig.NAMESPACE,
	// "DecryptionKey");
	// key.addChild(Node.TEXT,
	// loginDtls.getEncryptionKey());
	//
	// // // CreatedTime Element
	// // Element dateTime = new
	// Element().createElement(AppConfig.NAMESPACE,
	// // "CreatedTime");
	// // dateTime.addChild(Node.TEXT,
	// getCurrentDateTime());
	//
	// // Adding Username Element,Password
	// Element,DecryptionKey Element &
	// // CreatedTime Element to WebServiceHeader Element
	// header.addChild(Node.ELEMENT, username);
	// header.addChild(Node.ELEMENT, password);
	// header.addChild(Node.ELEMENT, key);
	// // header.addChild(Node.ELEMENT, dateTime);
	//
	// String headerData = username.getChild(0) + " " +
	// password.getChild(0)
	// + " " + key.getChild(0) + " ";
	// Log.v("SOAP HEADER", headerData);
	// return header;
	// }
	// ----------------------------------------------------------------------------------
}
