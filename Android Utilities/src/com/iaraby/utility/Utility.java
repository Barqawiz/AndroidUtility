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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.iarbay.utility.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.WindowManager.BadTokenException;
import android.widget.Toast;

/**
 * General utilities that collect common methods used in most apps to save time
 * on developers
 * 
 * The methods divided into sections and for every section that have a lot of methods will 
 * be moved into another class
 * (Section: System, Files UI, Social, Operations)
 */
public class Utility {

	/* System */

	/**
	 * Check if Internet available even WIFI network or mobile data network
	 * 
	 * @param context
	 * @return true if network available, false otherwise
	 */
	public static boolean isNetworkAvailable(Context context) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] infoArray = cm.getAllNetworkInfo();
		for (NetworkInfo info : infoArray) {
			if (info.getTypeName().equalsIgnoreCase("WIFI")
					&& info.isConnected())
				haveConnectedWifi = true;
			if (info.getTypeName().equalsIgnoreCase("MOBILE")
					&& info.isConnected())
				haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	} // check Internet connection

	/**
	 * Check if the app is visible on foreground to make sure it is not in sleep
	 * or termination state. This method useful after come from long thread and
	 * want to check if the user still using the app now
	 * 
	 * @param context
	 * @return true if the app in foreground, false otherwise
	 */
	public static boolean isAppOnForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses != null) {

			final String packageName = context.getPackageName();
			for (RunningAppProcessInfo appProcess : appProcesses) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
						&& appProcess.processName.equals(packageName)) {
					return true;
				}
			} // loop on all active processes
		} // make sure app process list not null
		return false;
	}

	/**
	 * get country ISO name for the user using the SIM card
	 * 
	 * @param Context
	 * 
	 * @return ISO name for the country
	 */
	public static String getCountryNameFromSim(Context context) {
		// try to get it from sim
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String countryCode = tm.getSimCountryIso();

		return countryCode;
	}

	/**
	 * get carrier(operator) name form SIM card
	 * 
	 * @param Context
	 * @return
	 */
	public static String getCarrierNameFromSim(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String opName = tm.getSimOperatorName();

		return opName;
	}

	/*Files*/
	/**
	 * This method return the name of files and folders in assets 
	 * 
	 * @param String folder name, if you want to list the root send null
	 * 
	 * @return Array of string for items names
	 * @throws IOException 
	 */
	public static String[] listAssetsFiles(Context context, String folder) throws IOException {
		if (folder == null)
			folder = "";
		String[] fileNames = context.getAssets().list(folder);
		return fileNames;
		
	}
	
	/**
	 * Read image from assets
	 * @param Context
	 * @param @param String folder name, if you want the root send null or empty string
	 * @param String image name with extension e.g.image.png
	 * @return Bitmap 
	 * @throws IOException throw exception if the image not found or error while reading
	 */
	public static Bitmap getImageFromAssets(Context context, String folder, String filename)
			throws IOException {
		if (folder == null) folder = "";
		if (folder.trim().length() > 0 && !folder.endsWith("/"))
			folder = folder + "/";
		
		AssetManager manager = context.getAssets();
		InputStream open = manager.open(folder + filename);

		Bitmap bitmap = BitmapFactory.decodeStream(open);

		open.close();

		return bitmap;
	} // read image form assets based on received name

	/* UI */
	/**
	 * show short toast message for the user
	 * 
	 * @param Context
	 * @param String
	 *            message
	 */
	public static void showToastMessage(Context context, String msg) {
		if (msg == null)
			msg = "";

		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	} // method: show message

	/**
	 * Create loading dialog in center of screen, without text and prevent
	 * clicks
	 * 
	 * to get the view call:
	 * findViewById(com.iarbay.utility.R.id.loading_dialog);
	 * 
	 * @return ProgressDialog after make it visible, make sure to save it in
	 *         variable to dismiss it when needed
	 */
	public static ProgressDialog createLoadingDialog(Context context) {
		ProgressDialog dialog = new ProgressDialog(context);

		try {
			dialog.show();
		} catch (BadTokenException e) {
		}
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.progressdialog);
		dialog.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
			}
		});

		return dialog;
	}

	/**
	 * check if screen orientation is portrait
	 * 
	 * @param screenOrientation
	 * @return true if orientation portrait, false otherwise
	 */
	public static boolean isPortrait(int screenOrientation) {
		if (screenOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			return true;
		} else {
			return false;
		} // check orientation
	} // method: check if portrait

	/**
	 * check if screen orientation is landscape
	 * 
	 * @param screenOrientation
	 * @return true if orientation landscape, false otherwise
	 */
	public static boolean isLandscape(int screenOrientation) {
		if (screenOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			return true;
		} else {
			return false;
		} // check orientation
	} // method: check if portrait

	/* Social */
	/**
	 * Call share intent to share data with other apps like Faebook, Twitter,
	 * etc If you don't want to share link send empty String "" or null, don't
	 * send null in message or subject field
	 * 
	 * @param String
	 *            subject
	 * @param String
	 *            message
	 * @param String
	 *            link
	 * @param Activity
	 *            activity
	 */
	public static void share(String subject, String message, String link,
			Activity activity) {

		if (link == null || link.length() == 0) {
			link = "";
		} else {
			link = "\n" + link;
		}

		Intent sharingIntent = new Intent(Intent.ACTION_SEND);

		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message
				+ link);
		activity.startActivity(Intent.createChooser(sharingIntent, "Share"));
	}

	/* Operations */
	/**
	 * check if the received String is numbers only
	 * 
	 * @param String
	 *            value
	 * @return true is the String is numbers only, false otherwise
	 */
	public static boolean isNumber(String value) {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	} // method: check if string number or not

	/**
	 * Copy new array based on received range from start to end if end > start
	 * will return the original array without coping
	 * 
	 * @param String
	 *            [] original array
	 * @param int start coping from
	 * @param int end coping
	 * @return new array from start to end range
	 */
	public static String[] copyOfRange(String[] original, int start, int end) {

		if (start < 0 || end > original.length || start > end)
			return original;

		String[] newArray = new String[end - start];

		int index = 0;
		for (int i = start; i < end; i++) {
			newArray[index++] = original[i];
		}

		return newArray;
	}
	
	/**
	 * copy from InputStream to  OutputStream
	 * @param InputStream in
	 * @param OutputStream out
	 * @throws IOException
	 */
	public static void copyStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

} // class: Utility
