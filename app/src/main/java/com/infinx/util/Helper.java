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

package com.infinx.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.infinx.bookmyslot.R;
import com.infinx.webservices.AppConfig;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355 Created On 02-Sep-2014 11:18:10 AM
 */
public class Helper {

	static NotificationCompat.Builder builder;
	private static NotificationManager mNotificationManager;

	// --------------------------------------------------------------------
	/**
	 * Show toast message.
	 */
	public static Toast showToast(Context ctx, String message, int duration) {
		Toast toast = Toast.makeText(ctx, message, duration);
		LinearLayout toastLayout = (LinearLayout) toast.getView();
		TextView toastTV = (TextView) toastLayout.getChildAt(0);

		// toastTV.setTextSize(getDimensions(R.dimen.dialog_text_size,
		// ctx));
		// toastTV.setTextColor(Color.MAGENTA);
		// toastTV.setBackgroundColor(Color.BLACK);
		toast.show();
		return toast;
	}

	// --------------------------------------------------------------------

	/**
	 * Get string resources from string.xml file
	 * 
	 * @param id
	 * @param ctx
	 * @return string
	 */
	public static String getString(int id, Context ctx) {

		return ctx.getResources().getString(id);
	}

	// --------------------------------------------------------------------
	/**
	 * Get Drawable resources from drawable folder
	 * 
	 * @param id
	 * @param ctx
	 * @return drawable
	 */
	public static Drawable getDrawable(int id, Context ctx) {
		return ctx.getResources().getDrawable(id);
	}

	// --------------------------------------------------------------------
	/**
	 * Get Color resources from values folder
	 * 
	 * @param id
	 * @param ctx
	 * @return color
	 */
	public static int getColor(int id, Context mCtx) {
		return mCtx.getResources().getColor(id);
	}

	// --------------------------------------------------------------------

	/**
	 * Get dimension resources from values folder
	 * 
	 * @param id
	 * @param ctx
	 * @return dimension
	 */
	public static float getDimensions(int id, Context ctx) {
		return ctx.getResources().getDimension(id);
	}

	// --------------------------------------------------------------------

	/**
	 * Get boolean resources from values folder
	 * 
	 * @param id
	 * @param ctx
	 * @return dimension
	 */
	public static boolean getBools(int id, Context ctx) {
		return ctx.getResources().getBoolean(id);
	}

	// --------------------------------------------------------------------

	/**
	 * Get integer resources from values folder
	 * 
	 * @param id
	 * @param ctx
	 * @return integer
	 */
	public static int getInteger(int id, Context ctx) {
		return ctx.getResources().getInteger(id);
	}

	// --------------------------------------------------------------------

	/**
	 * Hide soft keypad
	 * 
	 * @param ctx
	 */
	public static void dismisKeypad(Context ctx) {

		try {
			InputMethodManager inputManager = (InputMethodManager) ctx
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(((Activity) ctx)
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------

	/**
	 * Method to hide soft keypad
	 * 
	 * @param et
	 */
	public static void hideSoftKeypad(EditText et, Context ctx) {
		InputMethodManager imm = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
		// Ending of method hideSoftKeypad
	}

	// --------------------------------------------------------------------
	public static void playAnimation(View view, int id, Context mCtx) {

		Animation mAnimation = AnimationUtils.loadAnimation(mCtx, id);
		view.setAnimation(mAnimation);

	}

	// --------------------------------------------------------------------

	/**
	 * Checking Internet connection
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean isConnectedToInternet(Context ctx) {
		boolean connected = false;

		ConnectivityManager connection = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connection != null
				&& (connection.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED)
				|| (connection.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)) {
			connected = true;
		} else {
			connected = false;
		}
		return connected;

	}

	// --------------------------------------------------------------------

	/**
	 * Goto Internet settings
	 * 
	 * @param ctx
	 * @return
	 */
	public static Runnable switchToWirelessSettings(final Context ctx) {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ctx.startActivity(new Intent(
						android.provider.Settings.ACTION_WIRELESS_SETTINGS));
			}
		};
		return r;

	}

	// --------------------------------------------------------------------
	/**
	 * Checking nfc enabled or not
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean isNfcEnabled(Context ctx) {
		NfcManager manager = (NfcManager) ctx
				.getSystemService(Context.NFC_SERVICE);
		NfcAdapter adapter = manager.getDefaultAdapter();
		if (adapter != null && adapter.isEnabled()) {
			// adapter exists and is enabled.
			return true;
		} else {
			return false;
		}

		// startActivity(new
		// Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
	}

	// --------------------------------------------------------------------
	/**
	 * Checking nfc compatible or not
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean isNfcCompatible(Context ctx) {
		PackageManager pm = ctx.getPackageManager();
		if (pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
			return true;
		} else {
			return false;
		}
		// startActivity(new
		// Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
	}
	// --------------------------------------------------------------------
	/**
	 * Checking gps enabled or not
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean isGpsEnabled(Context ctx) {
		LocationManager manager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);

		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			return false;
		} else {
			return true;
		}

		// startActivity(new
		// Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

	}
	// --------------------------------------------------------------------
	/**
	 * Checking gps compatible or not
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean isGpsCompatible(Context ctx) {
		PackageManager pm = ctx.getPackageManager();

		if (pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)) {
			return true;
		} else {
			return false;
		}

		// startActivity(new
		// Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

	}

	// --------------------------------------------------------------------

	/**
	 * Goto GPS settings
	 * 
	 * @param ctx
	 * @return
	 */
	public static Runnable switchToGpsSettings(final Context ctx) {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ctx.startActivity(new Intent(
						android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			}
		};
		return r;

	}

	// --------------------------------------------------------------------
	/**
	 * Set font style for views
	 */

	public static void applyFont(final Context context, final View root,
			final String fontName) {
		try {
			if (root instanceof ViewGroup) {
				ViewGroup viewGroup = (ViewGroup) root;
				for (int i = 0; i < viewGroup.getChildCount(); i++)
					applyFont(context, viewGroup.getChildAt(i), fontName);
			} else if (root instanceof EditText) {

				((EditText) root).setTypeface(Typeface.createFromAsset(
						context.getAssets(), fontName));

				// ((EditText) root)
				// .setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
				// ((EditText)
				// root).setSingleLine(true);

			} else if (root instanceof ListView) {
				((TextView) root).setTypeface(Typeface.createFromAsset(
						context.getAssets(), fontName));
			} else if (root instanceof TabHost) {
				((TextView) root).setTypeface(Typeface.createFromAsset(
						context.getAssets(), fontName));

			} else if (root instanceof TextView)
				((TextView) root).setTypeface(Typeface.createFromAsset(
						context.getAssets(), fontName));
			else if (root instanceof Spinner) {

				// Spinner.setTypeface(Typeface.createFromAsset(
				// context.getAssets(), fontName));

			}

		} catch (Exception e) {
			Log.e("ProjectName", String.format(
					"Error occured when trying to apply %s font for %s view",
					fontName, root));
			e.printStackTrace();
		}
	}

	public static void disableInputs(final Context context, final View root) {

		try {
			if (root instanceof ViewGroup) {
				ViewGroup viewGroup = (ViewGroup) root;
				for (int i = 0; i < viewGroup.getChildCount(); i++)
					disableInputs(context, viewGroup.getChildAt(i));
			} else if (root instanceof EditText) {

				((EditText) root).setEnabled(false);
				((EditText) root).setBackground(null);

				// ((EditText) root)
				// .setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
				// ((EditText)
				// root).setSingleLine(true);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void enableInputs(final Context context, final View root) {

		try {
			if (root instanceof ViewGroup) {
				ViewGroup viewGroup = (ViewGroup) root;
				for (int i = 0; i < viewGroup.getChildCount(); i++)
					enableInputs(context, viewGroup.getChildAt(i));
			} else if (root instanceof EditText) {

				((EditText) root).setEnabled(true);
				((EditText) root).setBackground(getDrawable(
						R.drawable.edittext_focude_effect, context));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	/**
	 * Making all views in layout empty
	 * 
	 * @param context
	 * @param root
	 */
	public static void makeFieldsEmpty(final Context context, final View root) {

		if (root instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup) root;
			for (int i = 0, count = viewGroup.getChildCount(); i < count; ++i) {

				if (root instanceof EditText) {
					((EditText) root).setText("");
				} else if (root instanceof ListView) {
					((TextView) root).setText("");
				}
			}
		}

	}

	// --------------------------------------------------------------------

	/**
	 * Get key hashes for facebook integration.
	 * 
	 * @param ctx
	 * @param fully
	 *            qualifiedPackageName(i.e package name from manifest file)
	 * @return keyhashes
	 */

	public static String getKeyHashes(Context ctx, String qualifiedPackageName) {
		String keyHashes = "";
		try {
			PackageInfo info = ctx.getPackageManager().getPackageInfo(
					qualifiedPackageName, PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());

				keyHashes = android.util.Base64.encodeToString(md.digest(),
						android.util.Base64.DEFAULT);

				Log.e("KeyHash:", keyHashes);

			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
		return keyHashes;

	}

	// --------------------------------------------------------------------
	/**
	 * Get IMEI numbers of device.
	 */
	public static String getIMEI(Context context) {

		StringBuilder imeiNumbers = new StringBuilder();

		TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(context);

		String imeiSIM1 = telephonyInfo.getImeiSIM1();
		imeiNumbers.append(imeiSIM1);
		String imeiSIM2 = telephonyInfo.getImeiSIM2();
		imeiNumbers.append(" " + ",");
		String imeiSIM3 = telephonyInfo.getImeiSIM3();
		imeiNumbers.append(" " + ",");
		String imeiSIM4 = telephonyInfo.getImeiSIM4();
		imeiNumbers.append(" " + ",");

		if (!(null == imeiSIM2) && !imeiSIM2.equals("")
				&& !imeiSIM2.equals("0")) {
			imeiNumbers.append(" " + ",");
			imeiNumbers.append(imeiSIM2);
		} else if (!(null == imeiSIM3) && !imeiSIM3.equals("")
				&& !imeiSIM3.equals("0")) {
			imeiNumbers.append(" " + ",");
			imeiNumbers.append(imeiSIM3);
		} else if (!(null == imeiSIM4) && !imeiSIM4.equals("")
				&& !imeiSIM4.equals("0")) {
			imeiNumbers.append(" " + ",");
			imeiNumbers.append(imeiSIM4);
		}

		Log.v("IMEI", imeiNumbers.toString());
		return imeiNumbers.toString();

	}

	// --------------------------------------------------------------------
	/**
	 * Show alert message with natural button
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 */
	public void showAlert(Context context, String title, String msg,
			final Runnable func) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(title);
		TextView myMsg = new TextView(context);
		myMsg.setTextColor(Color.BLACK);
		// myMsg.setTextSize(getDimensions(R.dimen.dialog_text_size,
		// context));
		myMsg.setPadding(10, 10, 10, 10);
		myMsg.setText(msg);
		// applyFont(context, myMsg,
		// AppConfig.FONT_PT_SERIF_CAPTION_WEB);
		myMsg.setGravity(Gravity.CENTER);
		// alertDialog.setView(myMsg);

		alertDialog.setMessage(msg);

		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						if (!(func == null)) {
							func.run();
						} else {
							dialog.cancel();
						}

					}
				});

		AlertDialog alert = alertDialog.create();
		alert.show();

		alert.getWindow().getAttributes();

		Button btn1 = alert.getButton(DialogInterface.BUTTON_POSITIVE);
		// btn1.setTextSize(getDimensions(R.dimen.dialog_text_size,
		// context));

		// applyFont(context, btn1,
		// AppConfig.FONT_TREBUC);
	}

	// --------------------------------------------------------------------
	/**
	 * Show alert message with natural button
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 */
	public void showAlertWithInput(Context context, String title, String msg,
			final Runnable func, String positiveBtn, String negativeBtn) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		alertDialog.setTitle(title);
		TextView myMsg = new TextView(context);
		myMsg.setTextColor(Color.BLACK);
		// myMsg.setTextSize(getDimensions(R.dimen.dialog_text_size,
		// context));
		myMsg.setPadding(10, 10, 10, 10);
		myMsg.setText(Html.fromHtml(msg));
		// applyFont(context, myMsg,
		// AppConfig.FONT_PT_SERIF_CAPTION_WEB);
		myMsg.setGravity(Gravity.CENTER);

		// alertDialog.setView(myMsg);
		alertDialog.setMessage(msg);

		alertDialog.setNegativeButton(Html.fromHtml(negativeBtn),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						dialog.cancel();
					}
				});

		alertDialog.setPositiveButton(Html.fromHtml(positiveBtn),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						if (!(func == null)) {
							func.run();
						} else {
							dialog.cancel();
						}

					}
				});

		AlertDialog alert = alertDialog.create();
		alert.show();

		alert.getWindow().getAttributes();

		// Button btn1 =
		// alert.getButton(DialogInterface.BUTTON_POSITIVE);
		// Button btn2 =
		// alert.getButton(DialogInterface.BUTTON_NEGATIVE);
		// btn1.setTextSize(getDimensions(R.dimen.dialog_text_size,
		// context));
		// btn2.setTextSize(getDimensions(R.dimen.dialog_text_size,
		// context));
		//
		// applyFont(context, btn1,
		// AppConfig.FONT_TREBUC);
		// applyFont(context, btn2,
		// AppConfig.FONT_TREBUC);
	}

	// --------------------------------------------------------------------
	/**
	 * Get device date and time
	 * 
	 * @return date in yyyy-MM-dd HH:mm:ss format
	 */

	public String getDateAndTime() {
		// SimpleDateFormat dateFormatGmt = new
		// SimpleDateFormat(
		// "yyyy:MM:dd HH:mm:ss");
		// dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		// System.out.println(dateFormatGmt.format(new
		// Date()) + "");
		//
		// String dateTime = dateFormatGmt.format(new
		// Date()).toString();
		// String date = dateTime.split(" ")[0];
		// String finalDate = date.replace(":", "-");
		// String time = dateTime.split(" ")[1];
		//
		// return finalDate + " " + time;
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat(
				AppConfig.DATE_TIME_FORMAT_AA);
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}

	// --------------------------------------------------------------------
	/**
	 * Get device date and time and substract 2 minitues
	 * 
	 * @return date in yyyy-MM-dd HH:mm:ss format
	 */

	public String getDateAndTimeBySub2Min() {
		// SimpleDateFormat dateFormatGmt = new
		// SimpleDateFormat(
		// "yyyy:MM:dd HH:mm:ss");
		// dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		// System.out.println(dateFormatGmt.format(new
		// Date()) + "");
		//
		// String dateTime = dateFormatGmt.format(new
		// Date()).toString();
		// String date = dateTime.split(" ")[0];
		// String finalDate = date.replace(":", "-");
		// String time = dateTime.split(" ")[1];
		//
		// return finalDate + " " + time;
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -2);
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat(
				AppConfig.DATE_TIME_FORMAT_AA);
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}

	// --------------------------------------------------------------------
	/**
	 * Get only device date
	 * 
	 * @return date in yyyy-MM-dd format
	 */
	public String getDate() {

		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		// SimpleDateFormat df = new
		// SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = AppConfig.DISPLAY_DATE_FORMAT2.format(c
				.getTime());
		return formattedDate;
	}

	// --------------------------------------------------------------------

	/**
	 * Method to format date
	 * 
	 * @param date
	 * @param convertFromFormat
	 * @param convertToFormat
	 * @return
	 */
	public static String convertDateFormat(String date,
			String convertFromFormat, String convertToFormat) {
		try {
			Date dateTime = new SimpleDateFormat(convertFromFormat).parse(date);
			date = new SimpleDateFormat(convertToFormat).format(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	// --------------------------------------------------------------------
	/**
	 * Load image with url
	 * 
	 * @param imageFullURL
	 * @return
	 */

	public static synchronized Bitmap DownloadImageFromUrl(String imageFullURL) {
		Bitmap bm = null;
		try {
			URL url = new URL(imageFullURL);
			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			bm = BitmapFactory.decodeByteArray(baf.toByteArray(), 0,
					baf.toByteArray().length);
		} catch (IOException e) {
			Log.d("ImageManager", "Error: " + e);
		}
		return bm;
	}

	// --------------------------------------------------------------------
	/**
	 * Remove duplicate elements from list
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List removeDuplicates(List list) {

		Set set = new HashSet();
		@SuppressWarnings("rawtypes")
		List newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		list.clear();
		list.addAll(newList);

		return list;

	}

	// --------------------------------------------------------------------
	// /**
	// * Encrypt Password
	// *
	// * @param password
	// * @return encrypted password
	// */
	// public String endryptData(String userName, String
	// password) {
	//
	// String encPassword = "";
	// String encUserName = "";
	// String finalKey = "";
	// DESede_BC encrypter = new DESede_BC();
	// String key = encrypter.randomKey(24);
	// encrypter = new DESede_BC(key.getBytes());
	//
	// try {
	//
	// encUserName = encrypter.encryptToBase64(userName);
	// encPassword = encrypter.encryptToBase64(password);
	// finalKey = Base64.toBase64String(key.getBytes());
	// finalKey = finalKey.substring(0, 32);
	// Log.v("ENCRYPTED Password", encPassword);
	//
	// } catch
	// (org.bouncycastle.crypto.DataLengthException
	// e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ShortBufferException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalBlockSizeException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (BadPaddingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalStateException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch
	// (org.bouncycastle.crypto.InvalidCipherTextException
	// e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return encUserName + "~" + encPassword + "~" +
	// finalKey;
	//
	// }

	// --------------------------------------------------------------------
	/**
	 * Write file on sd card to check logs
	 * 
	 * @param fileName
	 * @param data
	 */
	public static void writeLog(String fileName, String data) {

		try {
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/" + fileName + ".txt");
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file,
					true));

			writer.write(data.toString());
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------

	@SuppressWarnings({"deprecation", "unused"})
	public static Notification showNotification(Context context, String header,
			String message, String status) {
		Notification note = null;
		NotificationManager mgr = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		if (status.equals("ON")) {
			note = new Notification(R.drawable.ic_launcher, header,
					System.currentTimeMillis());
		} else {
			note = new Notification(R.drawable.ic_launcher, header,
					System.currentTimeMillis());
		}

		note.setLatestEventInfo(context, header, message, null);
		Uri alarmSound = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if (alarmSound == null) {
			alarmSound = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			if (alarmSound == null) {
				alarmSound = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			}
		}

		// note.sound = alarmSound;
		note.defaults |= Notification.DEFAULT_LIGHTS;
		note.flags = Notification.FLAG_NO_CLEAR;
		mgr.notify(0, note);
		return note;
	}

	// --------------------------------------------------------------------
	// public static void issueNotification(Context ctx,
	// String header,
	// String hedline) {
	// mNotificationManager = (NotificationManager) ctx
	// .getSystemService(ctx.NOTIFICATION_SERVICE);
	//
	// // Constructs the Builder object.
	// builder = new NotificationCompat.Builder(ctx)
	// .setSmallIcon(R.drawable.ic_launcher).setContentTitle(header)
	// .setContentText(hedline).setDefaults(Notification.DEFAULT_ALL);
	// // requires VIBRATE permission
	// /*
	// * Sets the big view "big text" style and supplies
	// the
	// text (the user's
	// * reminder message) that will be displayed in the
	// detail area of the
	// * expanded notification. These calls are ignored by
	// the support library
	// * for pre-4.1 devices.
	// */
	// // .setStyle(new
	// NotificationCompat.BigTextStyle().bigText(msg))
	// // .addAction(R.drawable.facebook, "Dismiss",
	// piDismiss)
	// // .addAction(R.drawable.twitter, "OK", piSnooze);
	//
	// /*
	// * Clicking the notification itself displays
	// ResultActivity, which
	// * provides UI for snoozing or dismissing the
	// notification. This is
	// * available through either the normal view or big
	// view.
	// */
	// Intent resultIntent = new Intent(ctx,
	// TabsActivity.class);
	// // resultIntent.putExtra("FROMNOTIFICATION", true);
	//
	// //
	// resultIntent.putExtra(CommonConstants.EXTRA_MESSAGE,
	// msg);
	// resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	//
	// // Because clicking the notification opens a new
	// ("special") activity,
	// // there's
	// // no need to create an artificial back stack.
	// PendingIntent resultPendingIntent =
	// PendingIntent.getActivity(ctx, 0,
	// resultIntent, PendingIntent.FLAG_ONE_SHOT);
	//
	// builder.setContentIntent(resultPendingIntent);
	// builder.setAutoCancel(true);
	// mNotificationManager.notify(0, builder.build());
	// }

	// --------------------------------------------------------------------
	// convert from bitmap to byte array
	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, stream);
		return stream.toByteArray();
	}

	// --------------------------------------------------------------------
	// convert from byte array to bitmap
	public static Bitmap getPhoto(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}

}
