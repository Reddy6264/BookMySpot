package com.infinx.lbs;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class MyLocationListener extends Service implements LocationListener {

	private static final String TAG = "MyLocationListener";

	private Context context = null;
	private Location location = null;
	private LocationManager locationManager = null;

	boolean isNetworkEnabled = false;
	boolean isGPSEnabled = false;
	boolean isWifiEnabled = false;
	boolean canGetLocation = false;

	public double latitude = 0.0;
	public double longitude = 0.0;

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	public MyLocationListener(Context ctx) {
		Log.v(TAG + ".MyLocationListener",
				"MyLocationListener constructor called");
		this.context = ctx;
		getLocationValue();
	}

	public Location getLocationValue() {
		Log.v(TAG + ".getLocationValue", "getLocationValue method called");

		try {
			locationManager = (LocationManager) context
					.getSystemService(LOCATION_SERVICE);

			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			

			if (isNetworkEnabled) {

				// Toast.makeText(context, "Net", 1).show();
				Log.v(TAG + ".getLocationValue", "Network provider enabled");
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

				if (locationManager != null) {
					location = locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();
						Log.v(TAG, "Co-ordinates are: " + latitude + " "
								+ longitude);

					}
				}

			}

			if (isGPSEnabled) {

				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

				if (locationManager != null) {
					location = locationManager
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					if (location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}
	
	///-----------------
	
	public static Location getBestLocation(Context ctxt) {
	    Location gpslocation = getLocationByProvider(
	        LocationManager.GPS_PROVIDER, ctxt);
	    Location networkLocation = getLocationByProvider(
	        LocationManager.NETWORK_PROVIDER, ctxt);
	    Location fetchedlocation = null;
	    // if we have only one location available, the choice is easy
	    if (gpslocation != null) {
	        Log.i("New Location Receiver", "GPS Location available.");
	        fetchedlocation = gpslocation;
	    } else {
	        Log.i("New Location Receiver",
	            "No GPS Location available. Fetching Network location lat="
	                + networkLocation.getLatitude() + " lon ="
	                + networkLocation.getLongitude());
	        fetchedlocation = networkLocation;
	    }
	    return fetchedlocation;
	}

	/**
	 * get the last known location from a specific provider (network/gps)
	 */
	private static Location getLocationByProvider(String provider, Context ctxt) {
	    Location location = null;
	    // if (!isProviderSupported(provider)) {
	    // return null;
	    // }
	    LocationManager locationManager = (LocationManager) ctxt
	            .getSystemService(Context.LOCATION_SERVICE);
	    try {
	        if (locationManager.isProviderEnabled(provider)) {
	            location = locationManager.getLastKnownLocation(provider);
	        }
	    } catch (IllegalArgumentException e) {
	        Log.i("New Location Receiver", "Cannot access Provider " + provider);
	    }
	    return location;
	}
	//---------------------

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app
	 * */
	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(MyLocationListener.this);
		}
	}

	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}
		return latitude;
	}

	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}
		return longitude;
	}

	/**
	 * Function to check GPS/wifi enabled
	 * 
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	/**
	 * Function to show settings alert dialog On pressing Settings button will
	 * lauch Settings Options
	 * */
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		alertDialog.setTitle("GPS Settings");
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						context.startActivity(intent);
					}
				});
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alertDialog.show();
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}