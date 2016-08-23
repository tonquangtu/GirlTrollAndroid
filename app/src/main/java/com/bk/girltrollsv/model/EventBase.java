package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 22-Aug-16.
 */
public class EventBase implements Parcelable{

    private String eventId;

    private String title;

    private TimeEvent timeEvent;

    protected EventBase(Parcel in) {
        eventId = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventId);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EventBase> CREATOR = new Creator<EventBase>() {
        @Override
        public EventBase createFromParcel(Parcel in) {
            return new EventBase(in);
        }

        @Override
        public EventBase[] newArray(int size) {
            return new EventBase[size];
        }
    };

    public String getEventId() {
        return eventId;
    }

    public String getTitle() {
        return title;
    }

    public TimeEvent getTimeEvent() {
        return timeEvent;
    }
}
