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
import com.infinx.model.LocationDetails;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355
 * 
 *      Created On 04-Dec-2014 ,9:56:59 am
 */
public class CityMallDetailsParser {

	static InputStream mInputStream = null;
	private static final String ns = null;

	public static LocationDetails parse(String xmlData)
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
	private static LocationDetails readResultData(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "ResultData");
		
		LocationDetails mLocationDtls = null;
		ValidateUserActvity.sDbAdapter.open();
		
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();

			if (name.equals("LocationMaster")) {
				mLocationDtls = parseLocationMaster(parser);
				
				ValidateUserActvity.sDbAdapter.insertLocationDetails(mLocationDtls);

			} else {
				skip(parser);
			}

		}

		
		return null;
	}

	private static LocationDetails parseLocationMaster(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "LocationMaster");
		LocationDetails mLocationDtls = null;

		String loationId = "", locationName = "", address = "", cityName = "", pincode = "", state = "", country = "", lattitude = "", longitude = "", isActive = "";
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();

			if (name.equals("LocationId")) {
				loationId = readTags(parser, "LocationId");

				Log.v("", loationId);
			} else if (name.equals("LocationName")) {
				locationName = readTags(parser, "LocationName");

				Log.v("", locationName);
			} else if (name.equals("Address")) {
				address = readTags(parser, "Address");

				Log.v("", address);
			} else if (name.equals("City")) {
				cityName = readTags(parser, "City");

				Log.v("", cityName);

			} else if (name.equals("Pincode")) {
				pincode = readTags(parser, "Pincode");

				Log.v("", pincode);
			} else if (name.equals("State")) {
				state = readTags(parser, "State");

				Log.v("", state);
			} else if (name.equals("Country")) {
				country = readTags(parser, "Country");

				Log.v("", country);

			} else if (name.equals("Latitude")) {
				lattitude = readTags(parser, "Latitude");

				Log.v("", lattitude);
			} else if (name.equals("Longitude")) {
				longitude = readTags(parser, "Longitude");

				Log.v("", longitude);
			} else if (name.equals("IsActive")) {
				isActive = readTags(parser, "IsActive");

				Log.v("", isActive);

			}

			else {
				skip(parser);
			}

		}

		mLocationDtls = new LocationDetails(loationId, locationName, address, cityName, pincode, state, country, lattitude, longitude, isActive);
		// mLocationDtls.setLocationId(loationId);
		// mLocationDtls.setLocationName(locationName);
		// mLocationDtls.setAddress(address);
		// mLocationDtls.setCityName(cityName);
		// mLocationDtls.setPincode(pincode);
		// mLocationDtls.setState(state);
		// mLocationDtls.setCountry(country);
		// mLocationDtls.setLattitude(lattitude);
		// mLocationDtls.setLongitude(longitude);
		// mLocationDtls.setIsActive(isActive);

		return mLocationDtls;
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
