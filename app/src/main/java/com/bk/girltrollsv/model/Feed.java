package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Dell on 16-Aug-16.
 */
public class Feed implements Parcelable{

    private String feedId;

    private String title;

    private String time;

    private int like;

    private int comment;

    private int share;

    private int view;

    private String school;

    private Video video;

    private ArrayList<ImageInfo> imageInfos;

    private Member member;


    public Feed(String feedId,
                String title,
                String time,
                int like,
                int comment,
                int share,
                int view,
                String school,
                Member member,
                Video video,
                ArrayList<ImageInfo> imageInfos) {

        this.feedId = feedId;
        this.title = title;
        this.time = time;
        this.like = like;
        this.comment = comment;
        this.share = share;
        this.view = view;
        this.school = school;
        this.video = video;
        this.imageInfos = imageInfos;
        this.member = member;
    }


    protected Feed(Parcel in) {
        feedId = in.readString();
        title = in.readString();
        time = in.readString();
        like = in.readInt();
        comment = in.readInt();
        share = in.readInt();
        view = in.readInt();
        school = in.readString();
        video = in.readParcelable(Video.class.getClassLoader());
        imageInfos = in.createTypedArrayList(ImageInfo.CREATOR);
        member = in.readParcelable(Member.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(feedId);
        dest.writeString(title);
        dest.writeString(time);
        dest.writeInt(like);
        dest.writeInt(comment);
        dest.writeInt(share);
        dest.writeInt(view);
        dest.writeString(school);
        dest.writeParcelable(video, flags);
        dest.writeTypedList(imageInfos);
        dest.writeParcelable(member, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    public String getFeedId() {
        return feedId;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public int getLike() {
        return like;
    }

    public int getComment() {
        return comment;
    }

    public int getShare() {
        return share;
    }

    public int getView() {
        return view;
    }

    public String getSchool() {
        return school;
    }

    public Member getMember() {
        return member;
    }

    public Video getVideo() {
        return video;
    }

    public ArrayList<ImageInfo> getImageInfos() {
        return imageInfos;
    }
}
