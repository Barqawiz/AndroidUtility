/*{This application to help developers create apps faster by collecting
 * the most used methods for android apps in one place, all you
 * have is to call the method you need and receive the results}
 Copyright (C) {2014} {Ahmad Sameer AlBarqawi} {iAraby}

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along
 with this program; if not, write to the Free Software Foundation, Inc.,
 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

 For contact you can reach me on twitter: @Barqawi88
 */
package com.iaraby.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Basic connection layer methods that is similar when make any Http connection,
 * no need to re write it again, just use it from the methods her
 */
public class HttpUtility {

	/**
	 * Create basic Http params with received timeout
	 * This called when create new instance from DefaultHttpClient to set
	 * timeout for it
	 * 
	 * @param timeOut in milliseconds
	 * @return HttpParams
	 */
	public static HttpParams createHttpParams(int timeOut) {
		HttpParams httpParameters = new BasicHttpParams();
		httpParameters = new BasicHttpParams();

		int timeoutConnection = timeOut;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);

		int timeoutSocket = timeOut;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		return httpParameters;

	} //method: create basic HttpParams

	/**
	 * Read the Http response bytes and return the String value
	 * String value could be row data or josn or xml data 
	 * 
	 * @param HttpResponse
	 * @return String value 
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String readHttpResponse(HttpResponse response) throws IllegalStateException, IOException {
		HttpEntity entity = response.getEntity();
		InputStream inputStream = entity.getContent();

		ByteArrayOutputStream content = new ByteArrayOutputStream();

		int readBytes = 0;
		byte[] sBuffer = new byte[512];
		while ((readBytes = inputStream.read(sBuffer)) != -1) {
			content.write(sBuffer, 0, readBytes);
		}
		
		inputStream.close();
		
		return(new String(content.toByteArray()));

	}
	
	/**
	 * Read HttpResponse return data as Json array
	 * 
	 *  Call this method only if you know that the return data is JSON format
	 * 
	 * @param HttpResponse
	 * @return JSONArray 
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws JSONException 
	 */
	public static JSONArray readHttpResponseJson(HttpResponse response) throws IllegalStateException, IOException, JSONException {
		
		String data = readHttpResponse(response);
		
		return new JSONArray(data);

	}
	
	
	/**
	 * Execute Http GET connection with the server and receive the response from server
	 * 
	 * @param HttpClient client
	 * @param String link
	 * @return server response as String value
	 * @throws ClientProtocolException
	 * @throws IOException if the returned code form server != 200
	 */
	public static String executeGetHttpCommand(HttpClient client, String link)
			throws ClientProtocolException, IOException {

		HttpGet get = new HttpGet(link);

		HttpResponse response = client.execute(get);

		final int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode != 200) {
			throw new IOException("Http error code :" + statusCode);
		}
		
		//read response using http util.
		String data = readHttpResponse(response);

		return data;
	}
	
	/**
	 * @param HttpClient client
	 * @param String link for API
	 * @param List params
	 * @return server response as String value
	 * @throws ClientProtocolException
	 * @throws IOException if error connect with the server or 
	 *         if the returned code form server != 200 (server error)
	 */
	public String executeHttpPostWithParam(HttpClient client, String link,
			List<NameValuePair> params) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(link);

		post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

		HttpResponse response = client.execute(post);

		final int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode != 200) {
			throw new IOException("Http error code :" + statusCode);
		}

		String data = readHttpResponse(response);

		return data;
	}
	
	
	
} // class: HttpUtility

//Utility library for common and daily used android methods