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

package com.infinx.parsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.infinx.bookmyslot.ValidateUserActvity;
import com.infinx.model.BookingHistoryObj;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355
 * 
 *      Created On 12-Dec-2014 ,7:02:10 pm
 */
public class BookingHistoryParser {

	static InputStream mInputStream = null;
	private static final String ns = null;
	static ArrayList<BookingHistoryObj> lstHisObj = null;

	public static ArrayList<BookingHistoryObj> parse(String xmlData)
			throws XmlPullParserException, IOException {
		try {
			try {

				mInputStream = new ByteArrayInputStream(
						xmlData.getBytes("UTF-8"));
				lstHisObj = new ArrayList<BookingHistoryObj>();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(mInputStream, null);
			parser.nextTag();
			readResponse(parser);

		} finally {
			mInputStream.close();
		}

		return lstHisObj;

	}

	private static void readResponse(XmlPullParser parser)
			throws XmlPullParserException, IOException {

		parser.require(XmlPullParser.START_TAG, ns, "Response");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("ResultData")) {
				readResultData(parser);
			} else {
				skip(parser);
			}
		}

	}

	// to their respective &quot;read&quot; methods for processing. Otherwise,
	// skips the tag.
	private static BookingHistoryObj readResultData(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "ResultData");

		BookingHistoryObj mBookingInfo = null;
		ValidateUserActvity.sDbAdapter.open();

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();

			if (name.equals("BookingHistory")) {
				mBookingInfo = parseBookingInfo(parser);

				lstHisObj.add(mBookingInfo);

			} else {
				skip(parser);
			}

		}

		return null;
	}

	private static BookingHistoryObj parseBookingInfo(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "BookingHistory");
		BookingHistoryObj mHistoryObj = null;

		String locationName = "", address = "", city = "", pinCode = "", state = "", country = "", floorName = "", slotName = "", parkingType = "", vehicleNum = "", bookingTime = "", arrivalTime = "", bookinghours = "", bookingCost = "", bookingCode = "";
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();

			if (name.equals("LocationName")) {
				locationName = readTags(parser, "LocationName");

				Log.v("", locationName);
			} else if (name.equals("Address")) {
				address = readTags(parser, "Address");

				Log.v("", address);
			} else if (name.equals("City")) {
				city = readTags(parser, "City");

				Log.v("", city);
			} else if (name.equals("Pincode")) {
				pinCode = readTags(parser, "Pincode");

				Log.v("", pinCode);

			} else if (name.equals("State")) {
				state = readTags(parser, "State");

				Log.v("", state);
			} else if (name.equals("Country")) {
				country = readTags(parser, "Country");

				Log.v("", country);
			} else if (name.equals("FloorName")) {
				floorName = readTags(parser, "FloorName");

				Log.v("", floorName);

			} else if (name.equals("SlotName")) {
				slotName = readTags(parser, "SlotName");

				Log.v("", slotName);
			} else if (name.equals("ParkingTypeName")) {
				parkingType = readTags(parser, "ParkingTypeName");

				Log.v("", parkingType);
			} else if (name.equals("CarRTONumber")) {
				vehicleNum = readTags(parser, "CarRTONumber");

				Log.v("", vehicleNum);

			} else if (name.equals("BookingTime")) {
				bookingTime = readTags(parser, "BookingTime");

				Log.v("", bookingTime);

			}

			else if (name.equals("BookingArrivalTime")) {
				arrivalTime = readTags(parser, "BookingArrivalTime");

				Log.v("", bookingTime);

			}

			else if (name.equals("BookingHours")) {
				bookinghours = readTags(parser, "BookingHours");

				Log.v("", bookingTime);

			} else if (name.equals("BookingCost")) {
				bookingCost = readTags(parser, "BookingCost");

				Log.v("", bookingTime);

			} else if (name.equals("BookingCode")) {
				bookingCode = readTags(parser, "BookingCode");

				Log.v("", bookingTime);

			}

			else {
				skip(parser);
			}

		}

		mHistoryObj = new BookingHistoryObj(locationName, address, city,
				pinCode, state, country, floorName, slotName, floorName,
				vehicleNum, bookingTime, arrivalTime, bookinghours,
				bookingCost, bookingCode);
		return mHistoryObj;
	}
	private static String readTags(XmlPullParser parser, String tagName)
			throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, tagName);
		String res = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, tagName);
		return res;
	}

	// For the tags title and summary, extracts their text values.
	private static String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	// Skips tags the parser isn't interested in. Uses depth to handle nested
	// tags. i.e.,
	// if the next tag after a START_TAG isn't a matching END_TAG, it keeps
	// going until it
	// finds the matching END_TAG (as indicated by the value of "depth" being
	// 0).
	private static void skip(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
				case XmlPullParser.END_TAG :
					depth--;
					break;
				case XmlPullParser.START_TAG :
					depth++;
					break;
			}
		}
	}
}
