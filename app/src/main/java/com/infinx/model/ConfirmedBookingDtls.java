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

package com.infinx.model;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355
 * 
 *      Created On 12-Dec-2014 ,12:36:20 pm
 */
public class ConfirmedBookingDtls {
	String userId = "", slotId = "", rateId = "", vehicleNum = "",
			arrivalTime = "", duration = "", booingCost = "",
			bookingCardId = "";
	/**
	 * 
	 */
	public ConfirmedBookingDtls() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the slotId
	 */
	public String getSlotId() {
		return slotId;
	}
	/**
	 * @param slotId
	 *            the slotId to set
	 */
	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}
	/**
	 * @return the rateId
	 */
	public String getRateId() {
		return rateId;
	}
	/**
	 * @param rateId
	 *            the rateId to set
	 */
	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	/**
	 * @return the vehicleNum
	 */
	public String getVehicleNum() {
		return vehicleNum;
	}
	/**
	 * @param vehicleNum
	 *            the vehicleNum to set
	 */
	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}
	/**
	 * @return the arrivalTime
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}
	/**
	 * @param arrivalTime
	 *            the arrivalTime to set
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}
	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}
	/**
	 * @return the booingCost
	 */
	public String getBooingCost() {
		return booingCost;
	}
	/**
	 * @param booingCost
	 *            the booingCost to set
	 */
	public void setBooingCost(String booingCost) {
		this.booingCost = booingCost;
	}
	/**
	 * @return the bookingCardId
	 */
	public String getBookingCardId() {
		return bookingCardId;
	}
	/**
	 * @param bookingCardId
	 *            the bookingCardId to set
	 */
	public void setBookingCardId(String bookingCardId) {
		this.bookingCardId = bookingCardId;
	}
	/**
	 * @param userId
	 * @param slotId
	 * @param rateId
	 * @param rate
	 * @param vehicleNum
	 * @param arrivalTime
	 * @param duration
	 * @param booingCost
	 * @param bookingCardId
	 */
	public ConfirmedBookingDtls(String userId, String slotId, String rateId,
			String vehicleNum, String arrivalTime, String duration,
			String booingCost, String bookingCardId) {
		super();
		this.userId = userId;
		this.slotId = slotId;
		this.rateId = rateId;

		this.vehicleNum = vehicleNum;
		this.arrivalTime = arrivalTime;
		this.duration = duration;
		this.booingCost = booingCost;
		this.bookingCardId = bookingCardId;
	}

}
