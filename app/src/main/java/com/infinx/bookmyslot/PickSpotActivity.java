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

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.infinx.lbs.MyLocationListener;
import com.infinx.model.LocationDetails;
import com.infinx.parsers.CityMallDetailsParser;
import com.infinx.util.DateTimeHelper;
import com.infinx.util.Helper;
import com.infinx.webservices.AppConfig;
import com.infinx.webservices.CallWebServices;
import com.infinx.webservices.CallWebServices.Webmethods;
import com.infinx.webservices.SoapObjectsHelper;
import com.infinx.webservices.WebcallResponse;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355
 * 
 *      Created On 01-Dec-2014 ,12:14:43 pm
 */
public class PickSpotActivity extends FragmentActivity {

	private Context mCtx;
	Helper mHelper = null;

	Spinner spCity, spMall;

	ArrayAdapter<String> adpCity = null;
	ArrayAdapter<String> adpMall = null;

	boolean bShowLoader = true;

	private GoogleMap mMap;

	// Google Places
	// GooglePlaces googlePlaces;

	private LocationManager locationManager;

	private SlidingDrawer drawer;
	private Button handle;

	EditText etBookingDate, etBookingTime;

	public boolean isToShowOnMap = true;
	Marker mrkr = null;
	MyLocationListener mylistner = null;

	public static ArrayList<LocationDetails> gLocDtlsLst = null;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_malls);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		mCtx = this;
		mHelper = new Helper();
		mylistner = new MyLocationListener(this);
		gLocDtlsLst = new ArrayList<LocationDetails>();

		initCompo();

		spCity = (Spinner) findViewById(R.id.sp_city);
		spMall = (Spinner) findViewById(R.id.sp_mall);
		handle = (Button) findViewById(R.id.handle);
		etBookingDate = (EditText) findViewById(R.id.et_date);
		etBookingTime = (EditText) findViewById(R.id.et_time);

		ValidateUserActvity.sDbAdapter.open();

		if (ValidateUserActvity.sDbAdapter.getCityList().size() > 1) {
			bShowLoader = false;
		}
		// showDefaults();
		getCityMallList();

		adpCity = new ArrayAdapter<String>(mCtx,
				android.R.layout.simple_spinner_item,
				ValidateUserActvity.sDbAdapter.getCityList());
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCity.setAdapter(adpCity);
		spCity.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				showOnMap(spCity.getSelectedItem().toString());
				adpMall = new ArrayAdapter<String>(mCtx,
						android.R.layout.simple_spinner_item,
						ValidateUserActvity.sDbAdapter.getMallList(spCity
								.getSelectedItem().toString()));
				adpMall.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spMall.setAdapter(adpMall);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		spMall.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				if (spMall.getSelectedItemPosition() != 0) {
					moveToMarker(spMall.getSelectedItem().toString());
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		drawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);

		drawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				handle.setText("-");

			}
		});

		drawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

			@Override
			public void onDrawerClosed() {
				handle.setText("+");

			}
		});

		etBookingDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimeHelper.showDatePicker(mCtx, "Booking Date",
						etBookingDate, true, false);

			}
		});
		etBookingTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimeHelper.showTimePicker(mCtx, "Booking Time",
						etBookingTime, false, true, false, 2);

			}
		});

		try {
			currentLocation();
		} catch (CursorIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (Helper.isGpsCompatible(mCtx)) {

			if (Helper.isGpsEnabled(mCtx)) {

			} else {
				mHelper.showAlertWithInput(mCtx, "",
						Helper.getString(R.string.info_gps_enable_msg, mCtx),
						Helper.switchToGpsSettings(mCtx), "Enable", "Cancel");
			}

		} else {
			mHelper.showAlert(mCtx, "",
					Helper.getString(R.string.info_no_gps_msg, mCtx), null);
		}
		// try {
		// currentLocation();
		// } catch (CursorIndexOutOfBoundsException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action
		// bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_history :

				Intent mIntent = new Intent(mCtx, BookingHistoryActivity.class);
				startActivity(mIntent);

				return true;

			default :
				return super.onOptionsItemSelected(item);
		}
	}

	private void showOnMap(String city) {
		// currentLocation();

		try {
			ArrayList<LocationDetails> mLocDtlsLst = ValidateUserActvity.sDbAdapter
					.getLocationList(city.replaceAll("^\\s+|\\s+$", ""));

			if (mLocDtlsLst.size() > 0) {
				gLocDtlsLst = mLocDtlsLst;
			}

			int j = 0;
			for (int i = 0; i < mLocDtlsLst.size(); i++) {

				mMap.addMarker(new MarkerOptions()
						.title(mLocDtlsLst.get(i).getLocationName())
						.position(
								new LatLng(Double.parseDouble(mLocDtlsLst
										.get(i).getLattitude()), Double
										.parseDouble(mLocDtlsLst.get(i)
												.getLongitude())))
						.icon(BitmapDescriptorFactory
								.fromBitmap(writeTextOnDrawable(R.drawable.pin,
										mLocDtlsLst.get(i).getLocationName())))

				);
				// .snippet("Book My Spot")
				j = i;

				// mLocDtlsLst.get(i).getVicinity()
			}

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(Double.parseDouble(mLocDtlsLst.get(j)
							.getLattitude()), Double.parseDouble(mLocDtlsLst
							.get(j).getLongitude()))) // Sets the center of the
					// map to
					// Mountain View
					.zoom(11) // Sets the zoom
					.tilt(30) // Sets the tilt of the camera to 30 degrees
					.build(); // Creates a CameraPosition from the builder
			mMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void moveToMarker(String mMallName) {

		try {
			LocationDetails mLocDtlsLst = ValidateUserActvity.sDbAdapter
					.getLocationByMall(mMallName);

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(Double.parseDouble(mLocDtlsLst
							.getLattitude()), Double.parseDouble(mLocDtlsLst
							.getLongitude()))) // Sets the center of the
					// map to
					// Mountain View
					.zoom(13) // Sets the zoom
					.tilt(30) // Sets the tilt of the camera to 30 degrees
					.build(); // Creates a CameraPosition from the builder
			mMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private Bitmap writeTextOnDrawable(int drawableId, String text) {

		Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId)
				.copy(Bitmap.Config.ARGB_8888, true);

		Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLUE);
		paint.setTypeface(tf);
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(convertToPixels(mCtx, 15));

		Rect textRect = new Rect();
		paint.getTextBounds(text, 0, text.length(), textRect);

		Canvas canvas = new Canvas(bm);

		// If the text is bigger than the canvas , reduce the font size
		if (textRect.width() >= (canvas.getWidth() - 4)) // the padding on
															// either sides is
															// considered as 4,
															// so as to
															// appropriately fit
															// in the text
			paint.setTextSize(convertToPixels(mCtx, 7)); // Scaling needs to be
															// used for
															// different dpi's

		// Calculate the positions
		int xPos = (canvas.getWidth() / 2) - 2; // -2 is for regulating the x
												// position offset

		// "- ((paint.descent() + paint.ascent()) / 2)" is the distance from the
		// baseline to the center.
		int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint
				.ascent()) / 2));

		canvas.drawText(text, xPos, yPos, paint);

		return bm;
	}

	public static int convertToPixels(Context context, int nDP) {
		final float conversionScale = context.getResources()
				.getDisplayMetrics().density;

		return (int) ((nDP * conversionScale) + 0.5f);

	}

	private void currentLocation() {

		Location myLoc = MyLocationListener.getBestLocation(mCtx);

		if (String.valueOf(mylistner.getLatitude()).length() > 3) {
			mMap.addMarker(new MarkerOptions()
					.title("You are here !")
					.position(
							new LatLng(mylistner.getLatitude(), mylistner
									.getLongitude()))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.gps))
					.snippet("Book My Spot"));

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(mylistner.getLatitude(), mylistner
							.getLongitude())) // Sets the center of the
					// map to
					// Mountain View
					.zoom(11) // Sets the zoom
					.tilt(30) // Sets the tilt of the camera to 30 degrees
					.build(); // Creates a CameraPosition from the builder
			mMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
			if (spCity.getSelectedItemPosition() == 0) {
				isToShowOnMap = true;
				GetCityFromCoordinate getAdd = new GetCityFromCoordinate();

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

					getAdd.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
							mylistner.getLatitude(), mylistner.getLongitude());
				} else {

					getAdd.execute(mylistner.getLatitude(),
							mylistner.getLongitude());
				}

			}
		}

	}
	/**
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if (drawer.isOpened()) {
			drawer.close();
		} else {
			Runnable r = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					// super.onBackPressed();
					moveTaskToBack(true);
					// LoginActivity.sSelectedTab = 0;
					// Intent mIntent = new Intent(mCtx, LoginActivity.class);
					// startActivity(mIntent);
				}
			};
			mHelper.showAlertWithInput(mCtx, "",
					Helper.getString(R.string.info_exit_msg, mCtx), r,
					Helper.getString(R.string.btn_yes, mCtx),
					Helper.getString(R.string.btn_no, mCtx));
		}

	}

	public void showParkingPlaceScreen(View v) {

		if (validateForm()) {
			Intent mIntent = new Intent(getApplicationContext(),
					ParkinigPlaceActivity.class);

			mIntent.putExtra("CITY", spCity.getSelectedItem().toString());
			mIntent.putExtra("MALL", spMall.getSelectedItem().toString());
			mIntent.putExtra("BOOKING_DATE_TIME", etBookingDate.getText()
					.toString() + " " + etBookingTime.getText().toString());
			startActivity(mIntent);
		}

	}

	public void getCityMallList() {
		FetchCityMallDetails mCityMall = new FetchCityMallDetails();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			mCityMall.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {

			mCityMall.execute();
		}
	}

	public class FetchCityMallDetails extends AsyncTask<Void, Void, Void> {
		WebcallResponse response;
		private ProgressDialog mPd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if (bShowLoader) {
				mPd = new ProgressDialog(mCtx);
				mPd.setMessage(Html.fromHtml("Loading..."));
				mPd.getWindow().setGravity(Gravity.CENTER);

				mPd.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

							}
						});

				mPd.show();
			}

		}
		@Override
		protected Void doInBackground(Void... params) {

			try {
				CallWebServices callWebservice = new CallWebServices(
						SoapObjectsHelper.crearteCityMallSoapObject(Webmethods.GetLocationMaster
								.toString()), Webmethods.GetLocationMaster);

				response = callWebservice.makeWebserviceRequest();
				CityMallDetailsParser.parse(response.getResultData());

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
			if (mPd != null) {
				mPd.dismiss();
			}

			adpCity = new ArrayAdapter<String>(mCtx,
					android.R.layout.simple_spinner_item,
					ValidateUserActvity.sDbAdapter.getCityList());
			adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spCity.setAdapter(adpCity);

		}
	}

	private void initCompo() {
		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.fragment_map)).getMap();

		// mMap.setOnMapClickListener(new OnMapClickListener() {
		//
		// @Override
		// public void onMapClick(LatLng arg0) {
		// // TODO Auto-generated method stub
		// Log.d("arg0", arg0.latitude + "-" + arg0.longitude);
		// }
		// });

		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub

				drawer.open();

				isToShowOnMap = false;
				mrkr = arg0;

				String city = getCityFromMarker(mrkr);

				spCity.setSelection(getPostionFromAdapter(city, adpCity));

				if (mrkr.getTitle().contains("You are here !")) {
					spMall.setSelection(0);
				} else {
					spMall.setSelection(getPostionFromAdapter(mrkr.getTitle(),
							adpMall));
				}

				// String stateName =
				// addresses.get(0).getAddressLine(1);
				// String countryName =
				// addresses.get(0).getAddressLine(2);
				Log.d("MARKER DATA", mrkr.getId() + " " + mrkr.getTitle() + " "
						+ mrkr.getSnippet());

				// GetCityFromCoordinate getAdd = new GetCityFromCoordinate();
				//
				// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				//
				// getAdd.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				// arg0.getPosition().latitude,
				// arg0.getPosition().longitude);
				// } else {
				//
				// getAdd.execute(arg0.getPosition().latitude,
				// arg0.getPosition().longitude);
				// }

				return false;
			}
		});
	}
	@SuppressWarnings("unchecked")
	private int getPostionFromAdapter(String str, ArrayAdapter<String> adp) {

		ArrayList<String> underlying = new ArrayList<String>();
		for (int i = 0; i < adp.getCount(); i++)
			underlying.add(adp.getItem(i));

		return underlying.indexOf(str);
	}

	public boolean validateForm() {
		boolean isFormValid = false;

		if (spCity.getSelectedItemPosition() == 0) {
			isFormValid = false;
			Helper.showToast(mCtx, "Please select city", Toast.LENGTH_LONG);
		} else if (spMall.getSelectedItemPosition() == 0) {
			isFormValid = false;
			Helper.showToast(mCtx, "Please select mall", Toast.LENGTH_LONG);
		} else if (!etBookingDate.getText().toString().contains("-")) {
			isFormValid = false;
			etBookingDate.requestFocus();
			Helper.showToast(mCtx, "Please select booking date",
					Toast.LENGTH_LONG);
		} else if (!etBookingTime.getText().toString().contains(":")) {
			isFormValid = false;
			etBookingTime.requestFocus();
			Helper.showToast(mCtx, "Please select booking time",
					Toast.LENGTH_LONG);
		} else {

			try {
				Date date1 = AppConfig.DISPLAY_DATE_FORMAT2.parse(etBookingDate
						.getText().toString());
				Date date2 = AppConfig.DISPLAY_DATE_FORMAT2.parse(mHelper
						.getDate());

				if (date1.equals(date2)) {

					Calendar date = Calendar.getInstance();
					date.setTime(AppConfig.DISPLAY_DATE_TIME_FORMAT
							.parse(etBookingDate.getText().toString() + " "
									+ etBookingTime.getText().toString())); // Parse
																			// into
																			// Date
																			// object
					Calendar now = Calendar.getInstance(); // Get time now
					long differenceInMillis = date.getTimeInMillis()
							- now.getTimeInMillis();
					long differenceInHours = (differenceInMillis) / 1000L / 60L / 60L; // Divide
																						// by
																						// millis/sec,
																						// secs/min,
																						// mins/hr
					int diff = (int) differenceInHours;

					Log.d("Time Diff", String.valueOf(diff));

					if (diff >= 1) {
						isFormValid = true;
					} else {

						isFormValid = false;

						Helper.showToast(
								mCtx,
								"Booking time should be more than 1 hr from current time.",
								Toast.LENGTH_LONG);
					}

				} else if (date1.after(date2)) {
					isFormValid = true;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return isFormValid;

	}
	public class Time_picker extends DialogFragment {

		OnTimeSetListener ontimeSet;

		int minHour;
		int minMinute;

		private int maxHour = 24;
		private int maxMinute = 00;
		DateFormat dateFormat;
		public Time_picker() {

		}

		public void setCallBack(OnTimeSetListener ontime) {

			ontimeSet = ontime;

		}

		private int hours, minutes;
		@Override
		public void setArguments(Bundle args) {
			super.setArguments(args);
			hours = args.getInt("hours");
			minutes = args.getInt("minute");

			minHour = hours;// current time plus 2:30hrs
			minMinute = -1;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			TimePickerDialog _time = new TimePickerDialog(getActivity(),
					ontimeSet, hours, minutes, false) {

				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay,
						int minute) {
					// TODO Auto-generated method stub

					try {
						boolean validTime;
						if (hourOfDay < minHour) {
							validTime = false;
						} else if (hourOfDay == minHour) {
							validTime = minute >= minMinute;
						} else if (hourOfDay == maxHour) {
							validTime = minute <= maxMinute;
						} else {
							validTime = true;
						}

						if (validTime) {
							hours = hourOfDay;
							minutes = minute;
						} else {
							updateTime(hours, minutes);
						}

					} catch (Exception e) {

					}
				}
			};
			return _time;
		}
	}

	public class GetCityFromCoordinate
			extends
				AsyncTask<Double, Void, ArrayList<Address>> {

		private ProgressDialog mPd;
		ArrayList<Address> lstAddress = new ArrayList<Address>();

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mPd = new ProgressDialog(mCtx);
			mPd.setMessage(Html.fromHtml("Loading..."));

			mPd.setProgress(0);
			mPd.setCancelable(false);
			// mPd.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
			// new DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// dialog.dismiss();
			// // mFetchPickupDtls.cancel(true);
			// }
			// });
			mPd.setCanceledOnTouchOutside(false);
			mPd.show();

		}

		/**
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected ArrayList<Address> doInBackground(Double... params) {
			// TODO Auto-generated method stub

			String address = String
					.format(Locale.ENGLISH,
							"http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$f,%2$f&sensor=true&language="
									+ Locale.getDefault().getCountry(),
							params[0], params[1]);
			HttpGet httpGet = new HttpGet(address);
			HttpClient client = new DefaultHttpClient();
			org.apache.http.HttpResponse response;
			StringBuilder stringBuilder = new StringBuilder();

			List<Address> retList = null;

			try {
				response = client.execute(httpGet);
				HttpEntity entity = response.getEntity();
				InputStream stream = entity.getContent();
				int b;
				while ((b = stream.read()) != -1) {
					stringBuilder.append((char) b);
				}

				JSONObject jsonObject = new JSONObject();
				jsonObject = new JSONObject(stringBuilder.toString());

				retList = new ArrayList<Address>();

				if ("OK".equalsIgnoreCase(jsonObject.getString("status"))) {
					JSONArray results = jsonObject.getJSONArray("results");
					for (int i = 0; i < results.length(); i++) {
						JSONObject result = results.getJSONObject(i);
						String indiStr = result.getString("formatted_address");
						Address addr = new Address(Locale.getDefault());
						addr.setAddressLine(0, indiStr);
						retList.add(addr);
					}
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.lstAddress = (ArrayList<Address>) retList;

			return lstAddress;

		}

		/**
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		/**
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(ArrayList<Address> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mPd != null) {
				mPd.dismiss();
			}
			if (isToShowOnMap) {

				if (lstAddress != null & lstAddress.size() > 0) {
					Address address = lstAddress.get(0);

					String mCity = lstAddress.get(0).getAddressLine(0);

					String[] data = mCity.split(",");

					showOnMap(data[data.length - 3]);
				}

			} else {

				if (lstAddress != null & lstAddress.size() > 0) {

					Address address = lstAddress.get(0);

					// String mCity = address.getLocality();

					String mCity = lstAddress.get(0).getAddressLine(0);

					String[] data = mCity.split(",");

					spCity.setSelection(getPostionFromAdapter(
							data[data.length - 3].replaceAll("^\\s+|\\s+$", ""),
							adpCity));
					spMall.setSelection(getPostionFromAdapter(mrkr.getTitle(),
							adpMall));

					// String stateName =
					// addresses.get(0).getAddressLine(1);
					// String countryName =
					// addresses.get(0).getAddressLine(2);
					Log.d("MARKER DATA", mrkr.getId() + " " + mrkr.getTitle()
							+ " " + mrkr.getSnippet());
				}
			}
		}

	}

	private String getCityFromMarker(Marker marker) {

		LocationDetails mLdtls = new LocationDetails();

		for (int i = 0; i < gLocDtlsLst.size(); i++) {

			mLdtls = gLocDtlsLst.get(i);
			if (mLdtls.getLattitude().contains(
					String.valueOf(marker.getPosition().latitude))
					&& mLdtls.getLongitude().contains(
							String.valueOf(marker.getPosition().longitude))) {
				// return mLdtls.getCityName();
			}

		}
		return mLdtls.getCityName();

	}
}
