package com.bk.girltrollsv.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.callback.ConfirmDialogListener;

/**
 * Created by Dell on 01-Oct-16.
 */
public class ConfirmDialogFragment extends DialogFragment {

    public static String TITLE = "title";
    public static String MESSAGE = "message";
    private int positiveText;
    private int negativeText;
    private ConfirmDialogListener listener;

    public ConfirmDialogFragment() {
        positiveText = R.string.accepted;
        negativeText = R.string.cancel;
    }

    public static ConfirmDialogFragment newInstance(String title, String message) {

        ConfirmDialogFragment confirm = new ConfirmDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        confirm.setArguments(args);
        return confirm;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        String title = args.getString(TITLE);
        String message = args.getString(MESSAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onPositivePress(dialog, which);
                    dismiss();
                }
            }
        });

        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onNegativePress(dialog, which);
                    dismiss();
                }
            }
        });
        return builder.create();
    }

    public void setPositiveText(int positiveText) {
        this.positiveText = positiveText;
    }

    public void setNegativeText(int negativeText) {
        this.negativeText = negativeText;
    }

    public void setListener(ConfirmDialogListener listener) {
        this.listener = listener;
    }

}
