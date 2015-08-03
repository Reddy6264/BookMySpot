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

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.infinx.bookmyslot.R;
import com.infinx.model.BookingInfo;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355
 * 
 *      Created On 29-Dec-2014 ,8:57:40 am
 */
public class CustomGridAdapter extends ArrayAdapter<BookingInfo> {

	Context context;
	int layoutResourceId;
	ArrayList<BookingInfo> data = new ArrayList<BookingInfo>();

	public CustomGridAdapter(Context context, int layoutResourceId,
			ArrayList<BookingInfo> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	@Override

	public boolean isEnabled(int position) {
		
		BookingInfo item = data.get(position);

	        if(item.getAvailable().equals("")||item.getAvailable().equals("0"))
	        {
	            return false;
	        }
	        return super.isEnabled(position);
	    }


	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
			holder.flParent = (FrameLayout) row.findViewById(R.id.fl_parent);
			holder.cb = (CheckBox) row.findViewById(R.id.checkBox1);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		BookingInfo item = data.get(position);

		holder.cb.setTag(position);
		holder.txtTitle.setText("Block : "+item.getSlotName());
		holder.txtTitle.setTag(item.getSlotName());
		
		
		if(Integer.parseInt(item.getAvailable())<=0 || item.getAvailable().equals("0")){
			holder.flParent.setBackground(Helper.getDrawable(R.drawable.gradient_booked_bg, context));
			holder.cb.setClickable(false);
			holder.cb.setBackgroundColor(Color.TRANSPARENT);
			holder.txtTitle.setBackgroundColor(Color.TRANSPARENT);
		}

	
		// holder.txtTitle.setText(item.getTitle());
		// holder.imageItem.setButtonDrawable(context.getResources()
		// .getDrawable(R.drawable.ic_redcar));
		return row;

	}

	static class RecordHolder {
		TextView txtTitle;
		FrameLayout flParent; 
		// ImageView imageItem;

//		RadioButton imageItem;

		CheckBox cb;

	}

}
