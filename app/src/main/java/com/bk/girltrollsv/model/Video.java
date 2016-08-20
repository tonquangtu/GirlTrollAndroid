package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 16-Aug-16.
 */
public class Video  implements Parcelable{

    private String feedId;

    private String videoId;

    private String urlVideo;

    private int type;

    public Video(String feedId, String videoId, String urlVideo, int type) {
        this.feedId = feedId;
        this.videoId = videoId;
        this.urlVideo = urlVideo;
        this.type = type;
    }

    protected Video(Parcel in) {
        feedId = in.readString();
        videoId = in.readString();
        urlVideo = in.readString();
        type = in.readInt();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getFeedId() {
        return feedId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public int getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(feedId);
        dest.writeString(videoId);
        dest.writeString(urlVideo);
        dest.writeInt(type);
    }
}
