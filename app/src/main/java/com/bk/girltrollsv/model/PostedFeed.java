package com.bk.girltrollsv.model;

/**
 * Created by Dell on 03-Dec-16.
 */
public class PostedFeed extends Feed {

    public static final int UPLOADING = 0;
    public static final int UPLOAD_FAIL = 1;
    public static final int UPLOAD_SUCCESS = 2;
    public static final int REJECT = 3;
    public static final int ACCEPTED = 4;

    private int uploadState;

    private long postedCode;

    public PostedFeed() {
        this.uploadState = UPLOADING;
    }

    public int getUploadState() {
        return uploadState;
    }

    public void setUploadState(int uploadState) {
        this.uploadState = uploadState;
    }

    public long getPostedCode() {
        return postedCode;
    }

    public void setPostedCode(long postedCode) {
        this.postedCode = postedCode;
    }
}
