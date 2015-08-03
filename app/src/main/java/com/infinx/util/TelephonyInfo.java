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

import java.lang.reflect.Method;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355 Created On
 * 02-Sep-2014 11:22:20 AM
 */
public class TelephonyInfo {
	
	private static TelephonyInfo	telephonyInfo;
	private String					imeiSIM1;
	private String					imeiSIM2;
	private String					imeiSIM3;
	private String					imeiSIM4;
	
	public String getImeiSIM4() {
		return imeiSIM4;
	}
	
	public void setImeiSIM4(String imeiSIM4) {
		this.imeiSIM4 = imeiSIM4;
	}
	
	public String getImeiSIM3() {
		return imeiSIM3;
	}
	
	public void setImeiSIM3(String imeiSIM3) {
		this.imeiSIM3 = imeiSIM3;
	}
	
	public String getImeiSIM1() {
		return imeiSIM1;
	}
	
	/*
	 * public static void setImeiSIM1(String imeiSIM1) {
	 * TelephonyInfo.imeiSIM1 = imeiSIM1; }
	 */
	
	public String getImeiSIM2() {
		return imeiSIM2;
	}
	
	/*
	 * public static void setImeiSIM2(String imeiSIM2) {
	 * TelephonyInfo.imeiSIM2 = imeiSIM2; }
	 */
	
	private TelephonyInfo() {
	}
	
	public static TelephonyInfo getInstance(Context context) {
		
		if (telephonyInfo == null)
		{
			
			telephonyInfo = new TelephonyInfo();
			
			TelephonyManager telephonyManager = ((TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE));
			
			telephonyInfo.imeiSIM1 = telephonyManager.getDeviceId();
			;
			telephonyInfo.imeiSIM2 = null;
			telephonyInfo.imeiSIM3 = null;
			telephonyInfo.imeiSIM4 = null;
			
			try
			{
				telephonyInfo.imeiSIM1 = getDeviceIdBySlot(context,
						"getDeviceIdGemini", 0);
				telephonyInfo.imeiSIM2 = getDeviceIdBySlot(context,
						"getDeviceIdGemini", 1);
				telephonyInfo.imeiSIM3 = getDeviceIdBySlot(context,
						"getDeviceIdGemini", 2);
				telephonyInfo.imeiSIM4 = getDeviceIdBySlot(context,
						"getDeviceIdGemini", 3);
			} catch (GeminiMethodNotFoundException e)
			{
				e.printStackTrace();
				
				try
				{
					telephonyInfo.imeiSIM1 = getDeviceIdBySlot(context,
							"getDeviceId", 0);
					telephonyInfo.imeiSIM2 = getDeviceIdBySlot(context,
							"getDeviceId", 1);
					telephonyInfo.imeiSIM3 = getDeviceIdBySlot(context,
							"getDeviceId", 2);
					telephonyInfo.imeiSIM4 = getDeviceIdBySlot(context,
							"getDeviceId", 3);
				} catch (GeminiMethodNotFoundException e1)
				{
					// Call here for next
					// manufacturer's
					// predicted method
					// name
					// if you wish
					e1.printStackTrace();
				}
			}
			
		}
		
		return telephonyInfo;
	}
	
	private static String getDeviceIdBySlot(Context context,
			String predictedMethodName, int slotID)
			throws GeminiMethodNotFoundException {
		
		String imei = null;
		
		TelephonyManager telephony = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		
		try
		{
			
			Class<?> telephonyClass = Class.forName(telephony.getClass()
					.getName());
			
			Class<?>[] parameter = new Class[1];
			parameter[0] = int.class;
			Method getSimID = telephonyClass.getMethod(predictedMethodName,
					parameter);
			
			Object[] obParameter = new Object[1];
			obParameter[0] = slotID;
			Object ob_phone = getSimID.invoke(telephony, obParameter);
			
			if (ob_phone != null)
			{
				imei = ob_phone.toString();
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new GeminiMethodNotFoundException(predictedMethodName);
		}
		
		return imei;
	}
	
	private static class GeminiMethodNotFoundException extends Exception {
		
		private static final long	serialVersionUID	= -996812356902545308L;
		
		public GeminiMethodNotFoundException(String info) {
			super(info);
		}
	}
	
}
