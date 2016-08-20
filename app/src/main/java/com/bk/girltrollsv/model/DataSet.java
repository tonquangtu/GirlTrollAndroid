package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dell on 17-Aug-16.
 */
public class DataSet implements Parcelable {

    private ArrayList<Feed> feeds;

    protected DataSet(Parcel in) {
        feeds = in.createTypedArrayList(Feed.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(feeds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataSet> CREATOR = new Creator<DataSet>() {
        @Override
        public DataSet createFromParcel(Parcel in) {
            return new DataSet(in);
        }

        @Override
        public DataSet[] newArray(int size) {
            return new DataSet[size];
        }
    };

    public void setFeeds(ArrayList<Feed> feeds) {
        this.feeds = feeds;
    }

    public ArrayList<Feed> getFeeds() {
        return feeds;
    }

}
