package com.bk.girltrollsv.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Dell on 01-Dec-16.
 */
public class BitmapUtil {

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodePhotoWithNewSize(String photoPath, int targetW, int targetH) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);

        // Calculate inSampleSize
        bmOptions.inSampleSize = calculateInSampleSize(bmOptions, targetW, targetH);

        // Decode bitmap with inSampleSize set
        bmOptions.inJustDecodeBounds = false;
        return  BitmapFactory.decodeFile(photoPath, bmOptions);

    }

    public static Bitmap decodePhoto2WithNewSize(String photoPath, int targetW, int targetH) {

        // set inJustDecodeBounds --> avoid allocated memory i while decode bitmap
        // it just change outHeight and change outWidth
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }
}
