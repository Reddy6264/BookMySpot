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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.infinx.bookmyslot.ValidateUserActvity;
import com.infinx.model.BookingInfo;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355
 * 
 *      Created On 11-Dec-2014 ,7:21:45 pm
 */
public class BookingInfoParser {

	static InputStream mInputStream = null;
	private static final String ns = null;

	public static BookingInfo parse(String xmlData)
			throws XmlPullParserException, IOException {
		try {
			try {
				mInputStream = new ByteArrayInputStream(
						xmlData.getBytes("UTF-8"));

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

		return null;

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
	private static BookingInfo readResultData(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "ResultData");

		BookingInfo mBookingInfo = null;
		ValidateUserActvity.sDbAdapter.open();

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();

			if (name.equals("BookingInfo")) {
				mBookingInfo = parseBookingInfo(parser);

				ValidateUserActvity.sDbAdapter.insertBookingInfo(mBookingInfo);

			} else {
				skip(parser);
			}

		}

		return null;
	}

	private static BookingInfo parseBookingInfo(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "BookingInfo");
		BookingInfo mBookingInfo = null;

		String flooName = "", floorMap = "", slotId = "", slotName = "", rateId = "", rate = "", parkingType = "", numberOfHours = "", capacity = "", booked = "", available = "";
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();

			if (name.equals("FloorName")) {
				flooName = readTags(parser, "FloorName");

				Log.v("", flooName);
			} else if (name.equals("FloorMap")) {
				floorMap = readTags(parser, "FloorMap");

				Log.v("", floorMap);
			} else if (name.equals("SlotId")) {
				slotId = readTags(parser, "SlotId");

				Log.v("", slotId);
			} else if (name.equals("SlotName")) {
				slotName = readTags(parser, "SlotName");

				Log.v("", slotName);

			} else if (name.equals("RateId")) {
				rateId = readTags(parser, "RateId");

				Log.v("", rateId);
			} else if (name.equals("Rate")) {
				rate = readTags(parser, "Rate");

				Log.v("", rate);
			} else if (name.equals("ParkingTypeName")) {
				parkingType = readTags(parser, "ParkingTypeName");

				Log.v("", parkingType);

			} else if (name.equals("NumberOfHours")) {
				numberOfHours = readTags(parser, "NumberOfHours");

				Log.v("", numberOfHours);
			} else if (name.equals("Capacity")) {
				capacity = readTags(parser, "Capacity");

				Log.v("", capacity);
			} else if (name.equals("Booked")) {
				booked = readTags(parser, "Booked");

				Log.v("", booked);

			} else if (name.equals("Available")) {
				available = readTags(parser, "Available");

				Log.v("", available);

			}

			else {
				skip(parser);
			}

		}

		mBookingInfo = new BookingInfo(flooName, floorMap, slotId, slotName,
				rateId, rate, parkingType, numberOfHours, capacity, booked,
				available);

		return mBookingInfo;
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
