package com.bk.girltrollsv.model.dataserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 21-Aug-16.
 */
public class Pager implements Parcelable{

    private String before;

    private String after;

    public Pager() {

    }

    protected Pager(Parcel in) {
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

    public static final Creator<Pager> CREATOR = new Creator<Pager>() {
        @Override
        public Pager createFromParcel(Parcel in) {
            return new Pager(in);
        }

        @Override
        public Pager[] newArray(int size) {
            return new Pager[size];
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
