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
 *      Created On 12-Dec-2014 ,6:39:40 pm
 */
public class BookingHistoryObj {

	String locationName = "", address = "", city = "", pinCode = "",
			state = "", country = "", floorName = "", slotName = "",
			parkingType = "", vehicleNum = "", bookingTime = "",
			arrivalTime = "", bookinghours = "", bookingCost = "",
			bookingCode = "";

	/**
	 * @param locationName
	 * @param address
	 * @param city
	 * @param pinCode
	 * @param state
	 * @param country
	 * @param floorName
	 * @param slotName
	 * @param parkingType
	 * @param vehicleNum
	 * @param bookingTime
	 * @param arrivalTime
	 * @param bookinghours
	 * @param bookingCost
	 * @param bookingCode
	 */
	public BookingHistoryObj(String locationName, String address, String city,
			String pinCode, String state, String country, String floorName,
			String slotName, String parkingType, String vehicleNum,
			String bookingTime, String arrivalTime, String bookinghours,
			String bookingCost, String bookingCode) {
		super();
		this.locationName = locationName;
		this.address = address;
		this.city = city;
		this.pinCode = pinCode;
		this.state = state;
		this.country = country;
		this.floorName = floorName;
		this.slotName = slotName;
		this.parkingType = parkingType;
		this.vehicleNum = vehicleNum;
		this.bookingTime = bookingTime;
		this.arrivalTime = arrivalTime;
		this.bookinghours = bookinghours;
		this.bookingCost = bookingCost;
		this.bookingCode = bookingCode;
	}
	/**
 * 
 */
	public BookingHistoryObj() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName
	 *            the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode
	 *            the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the floorName
	 */
	public String getFloorName() {
		return floorName;
	}

	/**
	 * @param floorName
	 *            the floorName to set
	 */
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	/**
	 * @return the slotName
	 */
	public String getSlotName() {
		return slotName;
	}

	/**
	 * @param slotName
	 *            the slotName to set
	 */
	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}

	/**
	 * @return the parkingType
	 */
	public String getParkingType() {
		return parkingType;
	}

	/**
	 * @param parkingType
	 *            the parkingType to set
	 */
	public void setParkingType(String parkingType) {
		this.parkingType = parkingType;
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
	 * @return the bookingTime
	 */
	public String getBookingTime() {
		return bookingTime;
	}

	/**
	 * @param bookingTime
	 *            the bookingTime to set
	 */
	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
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
	 * @return the bookinghours
	 */
	public String getBookinghours() {
		return bookinghours;
	}

	/**
	 * @param bookinghours
	 *            the bookinghours to set
	 */
	public void setBookinghours(String bookinghours) {
		this.bookinghours = bookinghours;
	}

	/**
	 * @return the bookingCost
	 */
	public String getBookingCost() {
		return bookingCost;
	}

	/**
	 * @param bookingCost
	 *            the bookingCost to set
	 */
	public void setBookingCost(String bookingCost) {
		this.bookingCost = bookingCost;
	}

	/**
	 * @return the bookingCode
	 */
	public String getBookingCode() {
		return bookingCode;
	}

	/**
	 * @param bookingCode
	 *            the bookingCode to set
	 */
	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}
}
