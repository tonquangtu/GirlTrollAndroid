package com.bk.girltrollsv.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.bk.girltrollsv.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Dell on 26-Oct-16.
 */
public class FileUtil {

    public static File getFile(Uri uri) {

        return new File(uri.getPath());
    }

    public static void saveImageLocal(Bitmap bitmap, String desFilePath) {

        try {
//            String desFileName = "JPEG_test" + System.currentTimeMillis() + ".jpg";
//            File storageDir = BaseApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//            File desFile = new File(storageDir, desFileName);
            File desFile = new File(desFilePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(desFile));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static File saveImageLocal(String pathFromFile) {

        File desFile = null;
        try {
            String desFileName = "JPEG_test" + System.currentTimeMillis() + ".jpg";
            File storageDir = BaseApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            desFile = new File(storageDir, desFileName);
            File fromFile = new File(pathFromFile);

            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(fromFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 40, new FileOutputStream(desFile));
            String mPhotoPath = desFile.getAbsolutePath();
            Log.e("tuton", mPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return desFile;
    }


}
