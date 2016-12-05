package com.bk.girltrollsv.model.dataserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 21-Aug-16.
 */
public class Paging implements Parcelable{

    private String before;

    private String after;

    public Paging() {

    }

    protected Paging(Parcel in) {
        before = in.readString();
        after = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(before);
        dest.writeString(after);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Paging> CREATOR = new Creator<Paging>() {
        @Override
        public Paging createFromParcel(Parcel in) {
            return new Paging(in);
        }

        @Override
        public Paging[] newArray(int size) {
            return new Paging[size];
        }
    };

    public String getBefore() {
        return before;
    }

    public String getAfter() {
        return after;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public void setAfter(String after) {
        this.after = after;
    }

}
