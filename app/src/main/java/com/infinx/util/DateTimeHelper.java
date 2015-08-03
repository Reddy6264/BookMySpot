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

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.infinx.webservices.AppConfig;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355 Created On 02-Sep-2014 11:21:22 AM
 */
public class DateTimeHelper {

	public static Calendar calendar;
	public static AlertDialog alert;

	/**
	 * Method to return todays date
	 * 
	 * @return today_date
	 */
	public static String getDefaultTodaysDate() {
		String today_date = "";
		Calendar.getInstance();
		today_date = AppConfig.DISPLAY_DATE_FORMAT2.format(Calendar
				.getInstance().getTime());
		return today_date;
	}

	/**
	 * Method to return current time
	 * 
	 * @return current_time
	 */
	public static String getDefaultCurrentTime() {
		String current_time = "";
		current_time = AppConfig.DISPLAY_TIME_FORMAT.format(Calendar
				.getInstance().getTime());
		return current_time;
	}

	/**
	 * Method returning date picker dialog
	 * 
	 * @param context
	 *            UI Context
	 * @param title
	 *            Title of date picker
	 * @param et_date
	 *            edit text in which selected date is to be shown
	 * @param hasPastValidation
	 *            should check if selected date is past date
	 * @param hasFutureValidation
	 *            should check if selected date is future date
	 */
	public static void showDatePicker(final Context context, String title,
			final EditText et_date, final boolean hasPastValidation,
			final boolean hasFutureValidation) {
		if (calendar == null) {
			calendar = Calendar.getInstance();
		}
		DatePickerDialog datePicker = new DatePickerDialog(context,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						calendar.set(year, monthOfYear, dayOfMonth);
						validateDate(context, et_date, hasPastValidation,
								hasFutureValidation);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		if (!title.trim().equals("")) {
			datePicker.setTitle(title);
		}
		datePicker.show();
		// Ending of method showDatePicker
	}

	/**
	 * Method returning time picker dialog
	 * 
	 * @param context
	 *            UI Context
	 * @param title
	 *            Title of time picker
	 * @param et_time
	 *            edit text in which selected time is to be shown
	 * @param is24HrsView
	 *            should show time picker in 24 hours format
	 * @param hasPastValidation
	 *            should check if selected time is past time
	 * @param hasFutureValidation
	 *            should check if selected time is future time
	 */
	public static void showTimePicker(final Context context, String title,
			final EditText et_time, boolean is24HrsView,
			final boolean hasPastValidation, final boolean hasFutureValidation,
			final int showAfter) {
		if (calendar == null) {
			calendar = Calendar.getInstance();
//			calendar.add(Calendar.HOUR_OF_DAY, showAfter);
		}
		TimePickerDialog timePicker = new TimePickerDialog(context,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						calendar.set(Calendar.HOUR_OF_DAY, hourOfDay
								);
						calendar.set(Calendar.MINUTE, minute);
						validateTime(context, et_time, hasPastValidation,
								hasFutureValidation);
					}
				}, calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), is24HrsView);
		
		timePicker.setTitle(title);

		timePicker.show();
		// Ending of method showTimePicker
	}

	/**
	 * Set the date text
	 */
	private static void updateDateText(EditText et_date) {
		String date = AppConfig.DISPLAY_DATE_FORMAT2.format(calendar.getTime());
		et_date.setText(date);
		// Ending of method updateDateText
	}

	/**
	 * Set the start time text
	 */
	private static void updateTimeText(EditText etTime) {
		String time = AppConfig.DISPLAY_TIME_FORMAT.format(calendar.getTime());
		etTime.setText(time);
		// Ending of method updateTimeText
	}

	/**
	 * Method to validate selected date
	 * 
	 * @param context
	 * @param et_date
	 * @param hasPastValidation
	 * @param hasFutureValidation
	 */
	private static void validateDate(Context context, EditText et_date,
			boolean hasPastValidation, boolean hasFutureValidation) {
		String selectedDate = AppConfig.DISPLAY_DATE_FORMAT2.format(calendar
				.getTime());
		if (hasPastValidation && hasFutureValidation) {
			if (!selectedDate.trim().equals(getDefaultTodaysDate().trim())) {
				displayErrDialog(context,
						"Invalid Date.\nPlease select today's date");
				et_date.setText(getDefaultTodaysDate());
			} else {
				updateDateText(et_date);
			}
		} else if (hasPastValidation) {
			if (isPastDate(selectedDate)) {
				displayErrDialog(context, "Invalid Date.\nPast date selected.");
				et_date.setText(getDefaultTodaysDate());
			} else {
				updateDateText(et_date);
			}
		} else if (hasFutureValidation) {
			if (isFutureDate(selectedDate)) {
				displayErrDialog(context,
						"Invalid Date.\nFuture date selected.");
				et_date.setText(getDefaultTodaysDate());
			} else {
				updateDateText(et_date);
			}
		} else {
			updateDateText(et_date);
		}
	}

	/**
	 * Method to validate selected time
	 * 
	 * @param context
	 * @param et_time
	 * @param hasPastValidation
	 * @param hasFutureValidation
	 */
	private static void validateTime(Context context, EditText et_time,
			boolean hasPastValidation, boolean hasFutureValidation) {
		String selectedTime = AppConfig.DISPLAY_DATE_TIME_FORMAT
				.format(calendar.getTime());
		if (hasPastValidation && hasFutureValidation) {
			if (!selectedTime.trim().equals(getDefaultTodaysDate().trim())) {
				displayErrDialog(context,
						"Invalid Time.\nPlease select current time");
				et_time.setText(getDefaultCurrentTime());
			} else {
				updateTimeText(et_time);
			}
		} else if (hasPastValidation) {
			if (isPastTime(selectedTime)) {
				displayErrDialog(context, "Invalid Time.\nPast time selected.");
				et_time.setText(getDefaultCurrentTime());
			} else {
				updateTimeText(et_time);
			}
		} else if (hasFutureValidation) {
			if (isFutureTime(selectedTime)) {
				displayErrDialog(context,
						"Invalid Time.\nFuture time selected.");
				et_time.setText(getDefaultCurrentTime());
			} else {
				updateTimeText(et_time);
			}
		} else {
			updateTimeText(et_time);
		}
	}

	/**
	 * Method to check if selected date is past date
	 * 
	 * @param selectedDate
	 * @return true/false
	 */
	private static boolean isPastDate(String selectedDate) {
		Date selDate = calendar.getTime();
		try {
			selDate = AppConfig.DISPLAY_DATE_FORMAT2.parse(selectedDate);
		} catch (ParseException e) {
			Log.e("During SelDate Parsing", e.getMessage());
		}

		Date currDate = calendar.getTime();
		try {
			currDate = AppConfig.DISPLAY_DATE_FORMAT2
					.parse(getDefaultTodaysDate());
		} catch (ParseException e) {
			Log.e("During CurDate Parsing", e.getMessage());
		}
		if (selDate.before(currDate)) {
			return true;
		}
		return false;
	}

	/**
	 * Method to check if selected date is future date
	 * 
	 * @param selectedDate
	 * @return true/false
	 */
	private static boolean isFutureDate(String selectedDate) {
		Date selDate = calendar.getTime();
		try {
			selDate = AppConfig.DISPLAY_DATE_FORMAT2.parse(selectedDate);
		} catch (ParseException e) {
			Log.e("During SelDate Parsing", e.getMessage());
		}

		Date currDate = calendar.getTime();
		try {
			currDate = AppConfig.DISPLAY_DATE_FORMAT2
					.parse(getDefaultTodaysDate());
		} catch (ParseException e) {
			Log.e("During CurDate Parsing", e.getMessage());
		}
		if (selDate.after(currDate)) {
			return true;
		}
		return false;
	}

	/**
	 * Method to check if selected time is past time
	 * 
	 * @param selectedTime
	 * @return true/false
	 */
	private static boolean isPastTime(String selectedTime) {
		Date selTime = calendar.getTime();
		try {
			selTime = AppConfig.DISPLAY_DATE_TIME_FORMAT.parse(selectedTime);
		} catch (ParseException e) {
			Log.e("During SelDate Parsing", e.getMessage());
		}

		Date currTime = calendar.getTime();
		try {
			currTime = AppConfig.DISPLAY_DATE_TIME_FORMAT
					.parse(getDefaultTodaysDate() + " "
							+ getDefaultCurrentTime());
		} catch (ParseException e) {
			Log.e("During CurDate Parsing", e.getMessage());
		}
		if (selTime.before(currTime)) {
			return true;
		}
		return false;
	}

	/**
	 * Method to check if selected time is future time
	 * 
	 * @param selectedTime
	 * @return true/false
	 */
	private static boolean isFutureTime(String selectedTime) {
		Date selTime = calendar.getTime();
		try {
			selTime = AppConfig.DISPLAY_DATE_TIME_FORMAT.parse(selectedTime);
		} catch (ParseException e) {
			Log.e("During SelDate Parsing", e.getMessage());
		}

		Date currTime = calendar.getTime();
		try {
			currTime = AppConfig.DISPLAY_DATE_TIME_FORMAT
					.parse(getDefaultTodaysDate() + " "
							+ getDefaultCurrentTime());
		} catch (ParseException e) {
			Log.e("During CurDate Parsing", e.getMessage());
		}
		if (selTime.after(currTime)) {
			return true;
		}
		return false;
	}

	private static void displayErrDialog(Context context, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setNeutralButton("Ok",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						alert = null;
					}
				});
		alert = builder.create();
		alert.show();
	}

}
