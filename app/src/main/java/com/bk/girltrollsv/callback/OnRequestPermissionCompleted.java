package com.bk.girltrollsv.callback;

import android.support.annotation.NonNull;

/**
 * Created by Dell on 02-Nov-16.
 */
public interface OnRequestPermissionCompleted {

    void onRequestComplete(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
