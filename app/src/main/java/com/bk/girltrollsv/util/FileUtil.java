package com.bk.girltrollsv.util;

import android.net.Uri;

import java.io.File;

/**
 * Created by Dell on 26-Oct-16.
 */
public class FileUtil {

    public static File getFile(Uri uri) {

        return new File(uri.getPath());
    }
}
