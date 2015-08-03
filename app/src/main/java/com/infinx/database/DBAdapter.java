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

package com.infinx.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.infinx.model.BookingInfo;
import com.infinx.model.LocationDetails;
import com.infinx.util.Helper;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355 Created On 03-Dec-2014 ,06:22:15 PM
 */
public class DBAdapter {

	public static final int DATABASE_VERSION = 9;
	public static final String TAG = "DBAdapter";

	public static final String KEY_ROWID = "_id";

	// --- Initializing variables for Zone Details
	// section.---

	public static final String KEY_LOCATION_ID = "location_id";
	public static final String KEY_LOCATION_NAME = "loaction_name";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_CITY = "city";

	public static final String KEY_PINCODE = "pincode";
	public static final String KEY_STATE = "state";
	public static final String KEY_COUNTRY = "country";
	public static final String KEY_LATTITUDE = "lattitude";

	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_IS_ACTIVE = "isactive";

	// Booking info

	public static final String KEY_FLOOR_NAME = "floor_name";
	public static final String KEY_FLOOR_MAP = "floor_map";
	public static final String KEY_SLOT_ID = "slot_id";
	public static final String KEY_SLOT_NAME = "slot_name";

	public static final String KEY_RATE_ID = "rate_id";
	public static final String KEY_RATE = "rate";
	public static final String KEY_PARKING_TYPE_NAME = "parking_type";
	public static final String KEY_NUMBER_OF_HOURS = "number_of_hours";

	public static final String KEY_CAPACITY = "capacity";
	public static final String KEY_BOOKED = "booked";
	public static final String KEY_AVAILABLE = "available";

	// ----Database Name----
	public static final String DATABASE_NAME = "BookMySpotDb";
	// Database Table Names
	public static final String CITY_MALLS_TABLE = "city_malls_table";
	public static final String BOOKING_INFO_TABLE = "booking_info_table";

	public static final String DATABASE_CREATE_CITY_MALLS = "create table city_malls_table(_id integer primary key autoincrement, "
			+ "location_id text not null UNIQUE ,loaction_name text , address text , city text , "
			+ "pincode text , state text , country text , lattitude text , longitude text ,isactive text  );";

	public static final String DATABASE_CREATE_BOOKING_INFO = "create table booking_info_table(_id integer primary key autoincrement, "
			+ "floor_name text  ,floor_map text ,slot_id text ,slot_name text , "
			+ "rate_id text ,rate text ,parking_type text ,number_of_hours text ,capacity text ,booked text ,available text );";

	final Context context;

	DatabaseHelper DBHelper;
	SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE_CITY_MALLS);
				db.execSQL(DATABASE_CREATE_BOOKING_INFO);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS city_malls_table");
			db.execSQL("DROP TABLE IF EXISTS booking_info_table");

			onCreate(db);
		}
	}

	// ---opens the database---
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	public long insertLocationDetails(LocationDetails obj) {

		ContentValues initialValues = new ContentValues();
		long status = 0;
		initialValues.put(KEY_LOCATION_ID, obj.getLocationId());
		initialValues.put(KEY_LOCATION_NAME, obj.getLocationName());
		initialValues.put(KEY_ADDRESS, obj.getAddress());
		initialValues.put(KEY_CITY, obj.getCityName());
		initialValues.put(KEY_PINCODE, obj.getPincode());
		initialValues.put(KEY_STATE, obj.getState());
		initialValues.put(KEY_COUNTRY, obj.getCountry());
		initialValues.put(KEY_LATTITUDE, obj.getLattitude());
		initialValues.put(KEY_LONGITUDE, obj.getLongitude());
		initialValues.put(KEY_IS_ACTIVE, obj.getIsActive());

		status = db.insert(CITY_MALLS_TABLE, null, initialValues);

		Log.v(TAG, String.valueOf(status));

		return status;

	}

	public long insertBookingInfo(BookingInfo obj) {

		ContentValues initialValues = new ContentValues();
		long status = 0;
		initialValues.put(KEY_FLOOR_NAME, obj.getFlooName());
		initialValues.put(KEY_FLOOR_MAP, obj.getFloorMap());
		initialValues.put(KEY_SLOT_ID, obj.getSlotId());
		initialValues.put(KEY_SLOT_NAME, obj.getSlotName());
		initialValues.put(KEY_RATE_ID, obj.getRateId());
		initialValues.put(KEY_RATE, obj.getRate());
		initialValues.put(KEY_PARKING_TYPE_NAME, obj.getParkingType());
		initialValues.put(KEY_NUMBER_OF_HOURS, obj.getNumberOfHours());
		initialValues.put(KEY_CAPACITY, obj.getCapacity());
		initialValues.put(KEY_BOOKED, obj.getBooked());
		initialValues.put(KEY_AVAILABLE, obj.getAvailable());

		status = db.insert(BOOKING_INFO_TABLE, null, initialValues);

		//

		Log.v(TAG, String.valueOf(status));

		return status;

	}
	// Get all location details

	public synchronized ArrayList<LocationDetails> getLocationList(
			String cityName) throws SQLException {
		ArrayList<LocationDetails> locLst = new ArrayList<LocationDetails>();

		LocationDetails mLocDtls = null;

		Cursor mCur = db.rawQuery("select * from city_malls_table where city='"
				+ cityName + "'", null);
		try {
			while (mCur.moveToNext()) {

				mLocDtls = new LocationDetails(mCur.getString(mCur
						.getColumnIndex("location_id")), mCur.getString(mCur
						.getColumnIndex("loaction_name")), mCur.getString(mCur
						.getColumnIndex("address")), mCur.getString(mCur
						.getColumnIndex("city")), mCur.getString(mCur
						.getColumnIndex("pincode")), mCur.getString(mCur
						.getColumnIndex("state")), mCur.getString(mCur
						.getColumnIndex("country")), mCur.getString(mCur
						.getColumnIndex("lattitude")), mCur.getString(mCur
						.getColumnIndex("longitude")), mCur.getString(mCur
						.getColumnIndex("isactive")));
				locLst.add(mLocDtls);

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return locLst;

	}

	// Get location details by mall name

	public synchronized LocationDetails getLocationByMall(String mallName)
			throws SQLException {
	

		LocationDetails mLocDtls = null;

		Cursor mCur = db.rawQuery("select * from city_malls_table where loaction_name='"
				+ mallName + "'", null);
		try {
			while (mCur.moveToNext()) {

				mLocDtls = new LocationDetails(mCur.getString(mCur
						.getColumnIndex("location_id")), mCur.getString(mCur
						.getColumnIndex("loaction_name")), mCur.getString(mCur
						.getColumnIndex("address")), mCur.getString(mCur
						.getColumnIndex("city")), mCur.getString(mCur
						.getColumnIndex("pincode")), mCur.getString(mCur
						.getColumnIndex("state")), mCur.getString(mCur
						.getColumnIndex("country")), mCur.getString(mCur
						.getColumnIndex("lattitude")), mCur.getString(mCur
						.getColumnIndex("longitude")), mCur.getString(mCur
						.getColumnIndex("isactive")));
				
			
				

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return mLocDtls;

	}

	// ---
	@SuppressWarnings("unchecked")
	public synchronized ArrayList<String> getCityList() throws SQLException {

		ArrayList<String> areaList = new ArrayList<String>();
		areaList.add("Select City");

		// Cursor mCur = db.rawQuery(
		// "select * from city_malls_table where column_name = ?",
		// new String[]{"city"});
		Cursor mCur = db.rawQuery("select * from city_malls_table", null);
		try {
			while (mCur.moveToNext()) {
				areaList.add(mCur.getString(mCur.getColumnIndex("city")));

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return (ArrayList<String>) Helper.removeDuplicates(areaList);

	}
	@SuppressWarnings("unchecked")
	public synchronized ArrayList<String> getMallList(String cityName)
			throws SQLException {

		ArrayList<String> mallList = new ArrayList<String>();
		mallList.add("Select Mall");

		Cursor mCur = db.rawQuery("select * from city_malls_table where city='"
				+ cityName + "'", null);
		try {
			while (mCur.moveToNext()) {
				mallList.add(mCur.getString(mCur
						.getColumnIndex("loaction_name")));

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return (ArrayList<String>) Helper.removeDuplicates(mallList);

	}
	// /-----------------------------------
	// ---
	@SuppressWarnings("unchecked")
	public synchronized ArrayList<String> getLevelsList() throws SQLException {

		ArrayList<String> levelList = new ArrayList<String>();
//		levelList.add("Level");

		Cursor mCur = db.rawQuery("select * from booking_info_table", null);
		try {
			while (mCur.moveToNext()) {
				levelList
						.add(mCur.getString(mCur.getColumnIndex("floor_name")));

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return (ArrayList<String>) Helper.removeDuplicates(levelList);

	}
	@SuppressWarnings("unchecked")
	public synchronized ArrayList<BookingInfo> getSlotsList(String level)
			throws SQLException {
		
		BookingInfo mBookInfo = new BookingInfo();

		ArrayList<BookingInfo> slotsList = new ArrayList<BookingInfo>();
		mBookInfo.setSlotName("Block");
		mBookInfo.setAvailable("1");
		slotsList.add(mBookInfo);

		Cursor mCur = db.rawQuery(
				"select * from booking_info_table where floor_name='" + level
						+ "'", null);
		try {
			while (mCur.moveToNext()) {
				mBookInfo = new BookingInfo();
				mBookInfo.setSlotName(mCur.getString(mCur.getColumnIndex("slot_name")));
				mBookInfo.setAvailable(mCur.getString(mCur.getColumnIndex("available")));
				slotsList.add(mBookInfo);

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return (ArrayList<BookingInfo>) Helper.removeDuplicates(slotsList);

	}

	public BookingInfo getSlotDetails(String level, String block) {
		BookingInfo mBookingInfo = null;
		Cursor mCur = db.rawQuery(
				"select * from booking_info_table where floor_name = '" + level
						+ "' and slot_name = '" + block + "'", null);

		try {
			while (mCur.moveToNext()) {

				String flooName = mCur.getString(mCur
						.getColumnIndex("floor_name"));
				String floorMap = mCur.getString(mCur
						.getColumnIndex("floor_map"));
				String slotId = mCur.getString(mCur.getColumnIndex("slot_id"));
				String slotName = mCur.getString(mCur
						.getColumnIndex("slot_name"));
				String rateId = mCur.getString(mCur.getColumnIndex("rate_id"));
				String rate = mCur.getString(mCur.getColumnIndex("rate"));
				String parkingType = mCur.getString(mCur
						.getColumnIndex("parking_type"));
				String numberOfHours = mCur.getString(mCur
						.getColumnIndex("number_of_hours"));
				String capacity = mCur.getString(mCur
						.getColumnIndex("capacity"));
				String booked = mCur.getString(mCur.getColumnIndex("booked"));
				String available = mCur.getString(mCur
						.getColumnIndex("available"));

				mBookingInfo = new BookingInfo(flooName, floorMap, slotId,
						slotName, rateId, rate, parkingType, numberOfHours,
						capacity, booked, available);

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mBookingInfo;

	}

	public String getLocationId(String city, String mall) {
		String locId = "";
		Cursor mCur = db.rawQuery(
				"select * from city_malls_table where city = '" + city
						+ "' and loaction_name = '" + mall + "'", null);

		try {
			while (mCur.moveToNext()) {
				locId = mCur.getString(mCur.getColumnIndex("location_id"));

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return locId;

	}
	// ---deletes all data from Zones table table---
	public void truncateCityMallTable() {

		int i = db.delete(CITY_MALLS_TABLE, null, null);
		Log.v("CITY_MALLS_TABLE Delete status", String.valueOf(i));
	}

	public void truncateBookingInfoTable() {

		int i = db.delete(BOOKING_INFO_TABLE, null, null);
		Log.v("BOOKING_INFO_TABLE Delete status", String.valueOf(i));
	}

}
