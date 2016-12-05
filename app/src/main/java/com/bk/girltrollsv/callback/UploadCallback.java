package com.bk.girltrollsv.callback;

/**
 * Created by Dell on 01-Dec-16.
 */
public interface UploadCallback {

    public void uploadComplete(int success, String message, long postedCode);

    public void uploadFail(long postedCode);
}
