package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 16-Aug-16.
 */
public class Video  implements Parcelable{

    private String videoId;

    private String urlVideo;

    private int type;

    private String urlVideoThumbnail;

    public Video(String videoId, String urlVideo, int type, String urlVideoThumbnail) {
        this.videoId = videoId;
        this.urlVideo = urlVideo;
        this.type = type;
        this.urlVideoThumbnail = urlVideoThumbnail;
    }

    protected Video(Parcel in) {
        videoId = in.readString();
        urlVideo = in.readString();
        type = in.readInt();
        urlVideoThumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoId);
        dest.writeString(urlVideo);
        dest.writeInt(type);
        dest.writeString(urlVideoThumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getVideoId() {
        return videoId;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public int getType() {
        return type;
    }


    public String getUrlVideoThumbnail() {
        return urlVideoThumbnail;
    }
}
