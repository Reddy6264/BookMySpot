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

import java.text.SimpleDateFormat;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355 Created On 02-Sep-2014 11:20:20 AM
 */
public class AppConfig {

	public static final String URL = "http://114.79.155.219/BookMySlot/BookMySlotService.svc";

	public static final String NAMESPACE = "http://tempuri.org/";

	public static String SOAP_NAMESPACE = "http://tempuri.org/";

	public static String ServiceContract = "http://tempuri.org/IBookMySlotService/";

	// --------------- Time & Date Formats
	// -----------------

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATE_FORMAT_DD_MM_YYYY = "dd-MM-yyyy";
	public static final String TIME_FORMAT = "kk:mm a";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm";
	public static final String DATE_TIME_FORMAT_AA = "yyyy-MM-dd hh:mm aa";
	public static final String DATE_TIME_FORMAT_DISPLAY_AA = "dd-MM-yyyy hh:mm aa";

	public static final String DATE_TIME_FORMAT_DD_MMM_YYYY_HH_MM_AA = "dd-MMM-yyyy hh:mm aa";
	public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd hh:mm";
	
	
	
	public static final String DATE_TIME_FORMAT_MM_DD_YYYY_ = "dd-MM-yyyy hh:mm aa";// edit
	
	

	public static final SimpleDateFormat DISPLAY_DATE_FORMAT1 = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat DISPLAY_DATE_FORMAT2 = new SimpleDateFormat(
			"dd-MMM-yyyy");

	public static final SimpleDateFormat DISPLAY_DATE_FORMAT3 = new SimpleDateFormat(
			"dd-MM-yyyy");

	public static final SimpleDateFormat DISPLAY_TIME_FORMAT = new SimpleDateFormat(
			"hh:mm aa");
	public static final SimpleDateFormat DISPLAY_DATE_TIME_FORMAT = new SimpleDateFormat(
			"dd-MMM-yyyy hh:mm aa");
	
	
	public static final SimpleDateFormat DISPLAY_DATE_TIME_FORMAT_DD_MM = new SimpleDateFormat(
			"dd-MM-yyyy hh:mm aa");
	public static final SimpleDateFormat YYYY_MM_DD_HH_MM_AA = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm aa");

	public static final SimpleDateFormat YYYY_MM_DD_HH_MM_24 = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm");

	public static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static String FONT_TREBUC = "trebuc.ttf";
	public static String FONT_TREBUCD = "trebucbd.ttf";
	public static String FONT_TREBUCBI = "trebucbi.ttf";
	public static String FONT_TREBUCIT = "trebucit.ttf";

}
