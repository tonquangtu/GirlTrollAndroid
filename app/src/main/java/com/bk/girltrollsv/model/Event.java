package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Dell on 22-Aug-16.
 */
public class Event extends EventBase implements Parcelable {

    private String sortContent;

    private String content;

    private ArrayList<ImageInfo> images;

    private int type;

    private String policy;

    private boolean active;


    protected Event(Parcel in) {
        super(in);
        sortContent = in.readString();
        content = in.readString();
        images = in.createTypedArrayList(ImageInfo.CREATOR);
        type = in.readInt();
        policy = in.readString();
        active = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(sortContent);
        dest.writeString(content);
        dest.writeTypedList(images);
        dest.writeInt(type);
        dest.writeString(policy);
        dest.writeByte((byte) (active ? 1 : 0));
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }



    public String getSortContent() {
        return sortContent;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<ImageInfo> getImages() {
        return images;
    }

    public int getType() {
        return type;
    }

    public String getPolicy() {
        return policy;
    }

    public boolean isActive() {
        return active;
    }
}
