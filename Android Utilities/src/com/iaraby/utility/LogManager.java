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

import android.util.Log;

/**
 * Log manager aim to control log status so no need to search for every debug or
 * error log when you want to turn off logs. on release It is recommended to
 * disable debug logs for performance issues and to prevent analysis of your app
 * form other developers
 * 
 * Tutorial: 1- Make sure for all debug logs call: LogManager.getIns().debug()    
 * 2- On release call: LogManager.getIns().setDebug(false) on the first activity or
 * on application class
 */
public class LogManager {

	private static LogManager instance;
	private boolean isDebug;
	private boolean isError;
	private boolean isInfo;

	public static LogManager getIns() {
		if (instance == null) {
			instance = new LogManager();
		} // check if instance did not created
		return instance;
	} // method: get the only instance

	public void setDebug(boolean res) {
		isDebug = res;
	}

	public void setError(boolean res) {
		isError = res;
	}
	
	public void setInfo(boolean res) {
		isInfo = res;
	}

	/**
	 * Print debug with respect to setDebug method
	 * 
	 * @param String
	 *            tag of log (common to use app name)
	 * @param Sting
	 *            info to log
	 */
	public void d(String tag, String info) {
		if (isDebug)
			Log.d(tag, info);
	}

	/**
	 * Print debug regardless the value of setDebug method (Always log debug)
	 * 
	 * @param String
	 *            tag of log (common to use app name)
	 * @param Sting
	 *            info to log
	 */
	public void dd(String tag, String info) {
		Log.d(tag, info);
	}

	/**
	 * Print error with respect to setError method
	 * 
	 * @param String
	 *            tag of log (common to use app name)
	 * @param Sting
	 *            info to log
	 */
	public void e(String tag, String info) {
		if (isError)
			Log.e(tag, info);
	} // method: print error

	/**
	 * Print error regardless the value of setError method (Always log error)
	 * 
	 * @param String
	 *            tag of log (common to use app name)
	 * @param Sting
	 *            info to log
	 */
	public void ee(String tag, String info) {
		Log.e(tag, info);
	} // method: print error

	/**
	 * Print info with respect to setInfo method
	 * 
	 * @param String
	 *            tag of debug (common to use app name)
	 * @param Sting
	 *            info to debug
	 */
	public void i(String tag, String info) {
		if (isInfo)
			Log.i(tag, info);
	} // method: print error
	
	/**
	 * Print info regardless the value of setError method (Always log error)
	 * @param String
	 *            tag of debug (common to use app name)
	 * @param Sting
	 *            info to debug
	 */
	public void ii(String tag, String info) {
			Log.i(tag, info);
	} // method: print error
	
	
}
