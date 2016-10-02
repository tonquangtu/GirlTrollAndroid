package com.bk.girltrollsv.callback;

import android.content.DialogInterface;

/**
 * Created by Dell on 01-Oct-16.
 */
public interface ConfirmDialogListener {

    void onPositivePress(DialogInterface dialog, int which);

    void onNegativePress(DialogInterface dialog, int which);
}
