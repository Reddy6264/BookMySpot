package com.infinx.lbs;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**************************************************************************
 * Copyright (c) 2014, INFINX (TIS Pvt. Ltd.)(www.infinxservices.com). Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ***************************************************************************/
public class Place {
	/**
	 * @author : MADHU REDDY KR
	 * @email : madhusudhana.reddy@infinxservices.com
	 * @mob : +91-9699221355,+91-9849221355
	 * 
	 *      Created On 02-Dec-2014 ,7:26:12 pm
	 */
	private String id;
	private String icon;
	private String name;
	private String vicinity;
	private Double latitude;
	private Double longitude;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	static Place jsonToPontoReferencia(JSONObject pontoReferencia) {
		try {
			Place result = new Place();
			JSONObject geometry = (JSONObject) pontoReferencia.get("geometry");
			JSONObject location = (JSONObject) geometry.get("location");
			result.setLatitude((Double) location.get("lat"));
			result.setLongitude((Double) location.get("lng"));
			result.setIcon(pontoReferencia.getString("icon"));
			result.setName(pontoReferencia.getString("name"));
			result.setVicinity(pontoReferencia.getString("vicinity"));
			result.setId(pontoReferencia.getString("id"));
			return result;
		} catch (JSONException ex) {
			Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@Override
	public String toString() {
		return "Place{" + "id=" + id + ", icon=" + icon + ", name=" + name
				+ ", latitude=" + latitude + ", longitude=" + longitude + '}';
	}

}