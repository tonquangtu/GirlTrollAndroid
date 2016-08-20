package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Dell on 19-Aug-16.
 */
public class ImageFeed extends Feed implements Parcelable {

    private ArrayList<ImageInfo> images;

    public ImageFeed(String feedId,
                     String title,
                     String time,
                     int like,
                     int comment,
                     int share,
                     int view,
                     String school,
                     Member member, ArrayList<ImageInfo> images) {

        super(feedId,title, time, like, comment, share, view, school, member );
        this.images = images;
    }

    protected ImageFeed(Parcel in) {
        super(in);
        images = in.createTypedArrayList(ImageInfo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageFeed> CREATOR = new Creator<ImageFeed>() {
        @Override
        public ImageFeed createFromParcel(Parcel in) {
            return new ImageFeed(in);
        }

        @Override
        public ImageFeed[] newArray(int size) {
            return new ImageFeed[size];
        }
    };

    public ArrayList<ImageInfo> getImages() {
        return images;
    }
}
