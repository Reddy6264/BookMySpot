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

package com.infinx.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @author : MADHU REDDY KR
 * @email : madhusudhana.reddy@infinxservices.com
 * @mob : +91-9699221355,+91-9849221355 Created On
 * 02-Sep-2014 11:24:51 AM
 */
public class DataParserHelper {
	
	public static String readStream(InputStream stream) throws IOException,
			UnsupportedEncodingException {
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		StringBuilder out = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null)
		{
			out.append(line);
		}
		return out.toString();
		
	}
}
