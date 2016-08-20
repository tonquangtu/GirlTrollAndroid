package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 16-Aug-16.
 */
public class VideoFeed extends Feed implements Parcelable{

    private Video video;

    public VideoFeed(String feedId,
                     String title,
                     String time,
                     int like,
                     int comment,
                     int share,
                     int view,
                     String school,
                     Member member, Video video) {

        super(feedId,title, time, like, comment, share, view, school, member );
        this.video = video;
    }

    protected VideoFeed(Parcel in) {
        super(in);
        video = in.readParcelable(Video.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(video, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoFeed> CREATOR = new Creator<VideoFeed>() {
        @Override
        public VideoFeed createFromParcel(Parcel in) {
            return new VideoFeed(in);
        }

        @Override
        public VideoFeed[] newArray(int size) {
            return new VideoFeed[size];
        }
    };

    public Video getVideo() {
        return video;
    }
}
