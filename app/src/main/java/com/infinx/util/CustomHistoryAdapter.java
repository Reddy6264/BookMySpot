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

package com.infinx.util;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinx.bookmyslot.R;
import com.infinx.model.BookingHistoryObj;
import com.infinx.webservices.AppConfig;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355
 * 
 *      Created On 12-Dec-2014 ,6:39:03 pm
 */
public class CustomHistoryAdapter extends ArrayAdapter<BookingHistoryObj> {

	private Activity activity;
	private List<BookingHistoryObj> items;
	private int row;
	private BookingHistoryObj objBean;
	Helper mHelper = new Helper();

	public CustomHistoryAdapter(Activity act, int row,
			List<BookingHistoryObj> items) {
		super(act, row, items);

		this.activity = act;
		this.row = row;
		this.items = items;

	}
	@Override
	public boolean isEnabled(int position) {
		try {
			objBean = items.get(position);
			Date date1 = AppConfig.YYYY_MM_DD_HH_MM_SS.parse(objBean
					.getArrivalTime());
			Date date2 = AppConfig.DISPLAY_DATE_FORMAT2
					.parse(mHelper.getDate());

			if (date1.before(date2)) {
				return false;

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		Animation animGrowFromMiddle = AnimationUtils.loadAnimation(
				activity.getApplicationContext(), R.anim.grow_from_middle);

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((items == null) || ((position + 1) > items.size()))
			return view;

		view.startAnimation(animGrowFromMiddle);

		objBean = items.get(position);

		holder.tvDate = (TextView) view.findViewById(R.id.tv_b_date);
		holder.tvMall = (TextView) view.findViewById(R.id.tv_b_mall);
		holder.tvSpot = (TextView) view.findViewById(R.id.tv_b_spot);
		holder.tvId = (TextView) view.findViewById(R.id.tv_b_id);

		holder.llParent = (LinearLayout) view.findViewById(R.id.ll_parent);

		try {
			Date date1 = AppConfig.YYYY_MM_DD_HH_MM_SS.parse(objBean
					.getArrivalTime());
			Date date2 = AppConfig.DISPLAY_DATE_FORMAT2
					.parse(mHelper.getDate());

			if (date1.equals(date2)) {

				holder.llParent.setBackgroundColor(Color.parseColor("#7498d8"));

			} else if (date1.after(date2)) {

				holder.llParent.setBackgroundColor(Color.parseColor("#7498d8"));

			} else if (date1.before(date2)) {
				holder.llParent.setBackgroundColor(Color.parseColor("#adacac"));
//				view.setEnabled(isEnabled(position));

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// if (isPastBooking(objBean.getArrivalTime())) {
		// // holder.tvId.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		// // holder.llParent.setFocusable(false);
		// // holder.llParent.setEnabled(false);
		// holder.llParent.setBackgroundColor(Color.parseColor("#7498d8"));
		// }

		if (holder.tvId != null && null != objBean.getBookingCode()) {
			holder.tvId.setText(Html.fromHtml(objBean.getBookingCode()));
		}

		if (holder.tvMall != null && null != objBean.getLocationName()) {
			holder.tvMall.setText(Html.fromHtml(objBean.getLocationName()));
		}

		if (holder.tvDate != null && null != objBean.getArrivalTime()) {
			holder.tvDate.setText(Html.fromHtml(objBean.getArrivalTime()));
			holder.tvDate.setText(Html.fromHtml(Helper.convertDateFormat(
					objBean.getArrivalTime(),
					AppConfig.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS,
					AppConfig.DATE_TIME_FORMAT_DD_MMM_YYYY_HH_MM_AA)));
		}

		if (holder.tvSpot != null && null != objBean.getSlotName()) {
			holder.tvSpot.setText(Html.fromHtml(objBean.getFloorName() + " / "
					+ objBean.getSlotName()));
		}

		return view;
	}
	public class ViewHolder {

		public TextView tvDate, tvMall, tvSpot, tvId;
		LinearLayout llParent;

	}

	private boolean isPastBooking(String bookingTime) {
		try {
			Date date1 = AppConfig.YYYY_MM_DD_HH_MM_SS.parse(bookingTime);
			Date date2 = AppConfig.DISPLAY_DATE_FORMAT2
					.parse(mHelper.getDate());

			if (date1.equals(date2)) {

			} else if (date1.after(date2)) {

			} else if (date1.before(date2)) {
				return true;

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
