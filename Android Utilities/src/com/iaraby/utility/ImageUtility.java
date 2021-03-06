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
import java.net.HttpURLConnection;
import java.net.URL;

import android.R;
import android.app.NativeActivity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

/**
 * Image Utility contain common methods to read and custom different Bitmaps and
 * Imageviews
 */
public class ImageUtility {

	/**
	 * Set mobile wallpaper
	 * 
	 * @param Bitmap
	 *            bitmap
	 * @throws IOException
	 */
	public static void setWallpaper(Bitmap bitmap, Context context)
			throws IOException {
		WallpaperManager wManager = WallpaperManager.getInstance(context);
		wManager.clear();
		wManager.setBitmap(bitmap);
	} // method: set wallpaper

	/**
	 * Set mobile wallpaper
	 * 
	 * @param ImageView
	 *            image
	 * @throws IOException
	 */
	public void setWallpaper(ImageView image, Context context)
			throws IOException {
		Bitmap bitmap = image.getDrawingCache();
		setWallpaper(bitmap, context);
	} // method: set wallpaper

	/**
	 * Convert bitmap to rounded corner image
	 * 
	 * @param Bitmap
	 *            bitmap
	 * @param int amount of rounded corner in pixel
	 * @return Bitmap
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int roundPixel) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(output);
		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setColor(0xff424242);
		canvas.drawRoundRect(rectF, roundPixel, roundPixel, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * Convert Image view to rounded corner image
	 * 
	 * @param ImageView
	 *            image to convert
	 * @param int amount of rounded corner in pixel
	 */
	public static void convertImageTpRoundedCorner(ImageView image,
			int roundPixel) {
		Bitmap bitmap = getBitmapFromImageView(image);
		Bitmap round = getRoundedCornerBitmap(bitmap, roundPixel);
		image.setImageBitmap(round);
		bitmap.recycle();
	}

	/**
	 * Convert bitmap to circulate image
	 * 
	 * @param Bitmap
	 *            bitmap
	 * @return Bitmap Circle
	 */
	public static Bitmap getCircleBitmap(Bitmap bitmap) {
		if (bitmap == null)
			return bitmap;

		Bitmap circle = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(circle);
		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
				bitmap.getWidth() / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return circle;
	}

	/**
	 * Convert Imageview to circle image
	 * 
	 * @param Bitmap
	 *            bitmap
	 * 
	 */
	public static void convertImageToCircle(ImageView image) {
		if (image == null)
			return;

		Bitmap bitmap = getBitmapFromImageView(image);
		Bitmap circle = getCircleBitmap(bitmap);
		image.setImageBitmap(circle);
		bitmap.recycle();

	}

	/**
	 * Get bitmap from Imageview
	 * 
	 * @param ImageView
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromImageView(ImageView image) {
		Bitmap output = Bitmap.createBitmap(image.getLayoutParams().width,
				image.getLayoutParams().height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		image.layout(0, 0, image.getLayoutParams().width,
				image.getLayoutParams().height);
		image.draw(canvas);

		return output;
	}

	/**
	 * @param Bitmap
	 * @param int degree for rotation
	 * @return Bitmap rotated
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int degree) { 
		return getRotate(bitmap, degree);
	}
	/**
	 * Get rotated bitmap
	 * @param Bitmap
	 * @param int degree for rotation
	 * @return Bitmap rotated
	 */
	public static Bitmap getRotate(Bitmap bitmap, int degree) {
		
		 Matrix matrix = new Matrix();
		 matrix.postRotate(degree);
		 return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	
	/*Filter Bitmap*/
	
	/**
	 * Get Gray bitmap
	 * @param Bitmap to convert 
	 * @param boolean isTransparent send false if the image does not have transparent background or true
	 * to keep transparent
	 * 
	 * @return Bitmap with gray filter
	 */
	public static Bitmap getGrayBitmap(Bitmap bitmap, boolean isTransparent) { 
		int width  = bitmap.getWidth();
		int height = bitmap.getHeight();
		Config conf;
		if (!isTransparent) {
			conf = Bitmap.Config.RGB_565;
		} else { 
			conf = Bitmap.Config.RGB_565;
		} //check if the image transparent or not
		
		Bitmap output = Bitmap.createBitmap(width, height, conf);
		Canvas canvas = new Canvas(output);
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		
		ColorMatrix matrix = new ColorMatrix();
		matrix.setSaturation(0);
		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		paint.setColorFilter(filter);
		canvas.drawBitmap(bitmap, rect, rect, paint);
		
		return output;
	}
	
	
	/*Filter Bitmap*/
	
	/**
	 * Share image URL using intent
	 * 
	 * @param String
	 *            imageUrl
	 * @param String
	 *            subject
	 * @param String
	 *            desc
	 */
	public static void shareImageUrl(String imageUrl, String subject,
			String desc, Context context) {

		String shareText = desc + "\n" + imageUrl;

		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		share.putExtra(android.content.Intent.EXTRA_TEXT, shareText);

		Intent cusotmeShare = Intent.createChooser(share, "Share using");
		cusotmeShare.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		context.startActivity(cusotmeShare);

	}

	/**
	 * Draw label on the bottom of bitmap
	 * 
	 * @param Bitmap
	 *            bitmap to draw on it
	 * @param String
	 *            the text you want to draw on bitmap
	 * @param int color value (you can get it from the android method Color.rgb)
	 * @param int text size
	 * @param maxChars
	 *            if you dont know the size of text send max character to draw
	 *            on the bitmap to make sure it does not exceed the bitmap size
	 *            or send -1
	 * @return
	 */
	public static Bitmap drawLabelOnBitmap(Bitmap bitmap, String text,
			int textColor, int textSize, int maxChars) {
		if (text.length() >= maxChars && maxChars != -1)
			text = text.substring(0, maxChars);

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		// draw the bitmap
		Canvas canvas = new Canvas(output);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		canvas.drawBitmap(bitmap, rect, rect, paint);

		// draw the label area
		paint.setColor(Color.rgb(0, 0, 0));
		paint.setAlpha(120);
		int labelAreaSize = (int) (bitmap.getHeight() * 0.2);
		canvas.drawRect(0, bitmap.getHeight() - labelAreaSize,
				bitmap.getWidth(), bitmap.getHeight(), paint);

		// draw text
		paint.setColor(textColor);
		paint.setTextSize(textSize);

		canvas.drawText(text, 1, bitmap.getHeight() - 10, paint);

		return output;
	}

	/**
	 * Get bitmap image from URL, make sure to call this method from thread
	 * 
	 * @param String
	 *            imageUrl
	 * @return Bitmap
	 * @throws IOException
	 * @throws OutOfMemoryError
	 */
	public static Bitmap getBitmapFromURL(String imageUrl) throws IOException,
			OutOfMemoryError {

		URL url = new URL(imageUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.connect();
		InputStream input = connection.getInputStream();

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPurgeable = true;
		Bitmap urlBitmap = BitmapFactory.decodeStream(input, null, opts);

		return urlBitmap;

	}

	/**
	 * Convert bitmap for byte, this can be use to cache bitmaps or encrypt it
	 * 
	 * Hint: this method integrated with BytesToBitmap
	 * 
	 * @param Bitmap
	 * @return
	 */
	public static byte[] BitmapToByte(Bitmap bitmap) {
		ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 0, arrayStream);
		byte[] data = arrayStream.toByteArray();

		return data;
	}

	/**
	 * Convert bytes to bitmap, make sure the bytes came from bitmap
	 * 
	 * Hint: this method integrated with BitmapToByte
	 * 
	 * @param byte[] bitmap bytes
	 * 
	 * @return bitmap
	 */
	public static Bitmap BytesToBitmap(byte[] bitmapBytes) {
		Bitmap output = BitmapFactory.decodeByteArray(bitmapBytes, 0,
				bitmapBytes.length);

		return output;
	}

	/**
	 * Sometimes we need to make sure the bitmap does not exceed x size, this
	 * method will return the bitmap with max width and height based on received
	 * value and will keep the ratio of the bitmap
	 * 
	 * @param Bitmap
	 * @param int max size for width and height
	 * @return Bitmap does not exceed the received size
	 */
	public static Bitmap getBitmapWithMaxSize(Bitmap bitmap, int maxSize) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float ratio = (float) width / (float) height;
		if (ratio > 0) {
			width = maxSize;
			height = (int) (width / ratio);
		} else {
			height = maxSize;
			width = (int) (height * ratio);
		}
		return Bitmap.createScaledBitmap(bitmap, width, height, true);
	}

} // class: Image Utility
