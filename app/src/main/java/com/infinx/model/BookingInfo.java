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

import java.io.Serializable;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355
 * 
 *      Created On 11-Dec-2014 ,6:51:33 pm
 */
public class BookingInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return the flooName
	 */
	public String getFlooName() {
		return flooName;
	}

	/**
	 * @param flooName the flooName to set
	 */
	public void setFlooName(String flooName) {
		this.flooName = flooName;
	}

	/**
	 * @return the floorMap
	 */
	public String getFloorMap() {
		return floorMap;
	}

	/**
	 * @param floorMap the floorMap to set
	 */
	public void setFloorMap(String floorMap) {
		this.floorMap = floorMap;
	}

	/**
	 * @return the slotId
	 */
	public String getSlotId() {
		return slotId;
	}

	/**
	 * @param slotId the slotId to set
	 */
	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}

	/**
	 * @return the slotName
	 */
	public String getSlotName() {
		return slotName;
	}

	/**
	 * @param slotName the slotName to set
	 */
	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}

	/**
	 * @return the rateId
	 */
	public String getRateId() {
		return rateId;
	}

	/**
	 * @param rateId the rateId to set
	 */
	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * @return the parkingType
	 */
	public String getParkingType() {
		return parkingType;
	}

	/**
	 * @param parkingType the parkingType to set
	 */
	public void setParkingType(String parkingType) {
		this.parkingType = parkingType;
	}

	/**
	 * @return the numberOfHours
	 */
	public String getNumberOfHours() {
		return numberOfHours;
	}

	/**
	 * @param numberOfHours the numberOfHours to set
	 */
	public void setNumberOfHours(String numberOfHours) {
		this.numberOfHours = numberOfHours;
	}

	/**
	 * @return the capacity
	 */
	public String getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the booked
	 */
	public String getBooked() {
		return booked;
	}

	/**
	 * @param booked the booked to set
	 */
	public void setBooked(String booked) {
		this.booked = booked;
	}

	/**
	 * @return the available
	 */
	public String getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(String available) {
		this.available = available;
	}

	String flooName = "", floorMap = "", slotId = "", slotName = "",
			rateId = "", rate = "0", parkingType = "", numberOfHours = "",
			capacity = "0", booked = "0", available = "0";

	/**
	 * @param flooName
	 * @param floorMap
	 * @param slotId
	 * @param slotName
	 * @param rateId
	 * @param rate
	 * @param parkingType
	 * @param numberOfHours
	 * @param capacity
	 * @param booked
	 * @param available
	 */
	public BookingInfo(String flooName, String floorMap, String slotId,
			String slotName, String rateId, String rate, String parkingType,
			String numberOfHours, String capacity, String booked,
			String available) {
		super();
		this.flooName = flooName;
		this.floorMap = floorMap;
		this.slotId = slotId;
		this.slotName = slotName;
		this.rateId = rateId;
		this.rate = rate;
		this.parkingType = parkingType;
		this.numberOfHours = numberOfHours;
		this.capacity = capacity;
		this.booked = booked;
		this.available = available;
	}

	/**
	 * 
	 */
	public BookingInfo() {
		// TODO Auto-generated constructor stub
	}

}
