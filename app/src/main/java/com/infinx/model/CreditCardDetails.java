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

import java.util.ArrayList;
import java.util.List;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355
 * 
 *      Created On 03-Dec-2014 ,7:24:42 pm
 */
public class CreditCardDetails {

	String userId = "", cardNumber = "", expiryDate = "", cardHolderName = "",
			cardType = "";

	String cardId = "", isActive = "";

	public List<String> lstCards = new ArrayList<String>();

	/**
	 * @return the lstCards
	 */
	public List<String> getLstCards() {
		return lstCards;
	}
	/**
	 * @param lstCards
	 *            the lstCards to set
	 */
	public void setLstCards(List<String> lstCards) {
		this.lstCards = lstCards;
	}
	/**
	 * @param userId
	 * @param cardNumber
	 * @param expiryDate
	 * @param cardHolderName
	 * @param cardType
	 * @param cardId
	 * @param isActive
	 */
	public CreditCardDetails(String userId, String cardNumber,
			String expiryDate, String cardHolderName, String cardType,
			String cardId, String isActive) {
		super();
		this.userId = userId;
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.cardHolderName = cardHolderName;
		this.cardType = cardType;
		this.cardId = cardId;
		this.isActive = isActive;
	}
	public CreditCardDetails() {

	}

	/**
	 * @return the cardId
	 */
	public String getCardId() {
		return cardId;
	}

	/**
	 * @param cardId
	 *            the cardId to set
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber
	 *            the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the expiryDate
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            the expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the cardHolderName
	 */
	public String getCardHolderName() {
		return cardHolderName;
	}

	/**
	 * @param cardHolderName
	 *            the cardHolderName to set
	 */
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @param cardType
	 *            the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CreditCardDetails))
			return false;

		return cardNumber.equals(((CreditCardDetails) obj).getCardNumber());
	}

	@Override
	public int hashCode() {
		return cardNumber.hashCode();
	}
	// @Override
	// public int hashCode() {
	// return (lstCards == null) ? 0 : lstCards.hashCode();
	// }

}
