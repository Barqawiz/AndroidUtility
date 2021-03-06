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

import com.iarbay.utility.R;

import android.app.Activity;

/**
 * Collection of most common used animation between application activities
 * 
 */
public class ActivityAnimation {
	
	/**
	 * push the current activity right out and the new activity right in.
	 * Hint: this method good to integrate with animateSlideLeft as opposite direction.
	 * 
	 * @param Activity current activity
	 */
	public static void animateSlideRight(Activity activity) {
		activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	/**
	 * push the current activity left out and the new activity left in.
	 * Hint: this method good to integrate with animateSlideRight as opposite direction.
	 * 
	 * @param Activity current activity
	 */
	public static void animateSlideLeft(Activity activity) {
		activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	/**
	 * push the new Activity from bottom to top
	 * 
	 * @param Activity current activity
	 */
	public static void animatePushUpIn(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_up_in, R.anim.none);
	}
	
	/**
	 * push the current activity out down.
	 * Hint: use this method in onBackPressed()
	 * 
	 * @param Activity current activity
	 */
	public static void animatePushDownOut(Activity activity) {
		activity.overridePendingTransition(R.anim.none, R.anim.slide_down_out);
	}
	
	/**
	 * Zoom animation
	 * 
	 * @param Activity current activity
	 */
	public static void animateZoom(Activity activity) {
		activity.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/**
	 * Smooth animation: fade-out current Activity and fade-in the new activity 
	 * 
	 * 
	 * @param Activity current activity
	 */
	public static void animateFade(Activity activity) {
		activity.overridePendingTransition(R.anim.fade, R.anim.fadeout);
	}
	
} //class: activity animation
