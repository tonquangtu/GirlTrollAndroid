package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Dell on 16-Aug-16.
 */
public class Feed implements Parcelable{

    private int feedId;

    private String title;

    private String time;

    private int isLike;

    private int like;

    private int comment;

    private String school;

    private Video video;

    private ArrayList<ImageInfo> images;

    private BaseInfoMember member;

    public Feed(int feedId,
                String title,
                String time,
                int isLike,
                int like,
                int comment,
                String school,
                BaseInfoMember member,
                Video video,
                ArrayList<ImageInfo> images) {

        this.feedId = feedId;
        this.title = title;
        this.time = time;
        this.isLike = isLike;
        this.like = like;
        this.comment = comment;
        this.school = school;
        this.video = video;
        this.images = images;
        this.member = member;
    }

    public Feed() {}

    protected Feed(Parcel in) {
        feedId = in.readInt();
        title = in.readString();
        time = in.readString();
        isLike = in.readInt();
        like = in.readInt();
        comment = in.readInt();
        school = in.readString();
        video = in.readParcelable(Video.class.getClassLoader());
        images = in.createTypedArrayList(ImageInfo.CREATOR);
        member = in.readParcelable(BaseInfoMember.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(feedId);
        dest.writeString(title);
        dest.writeString(time);
        dest.writeInt(isLike);
        dest.writeInt(like);
        dest.writeInt(comment);
        dest.writeString(school);
        dest.writeParcelable(video, flags);
        dest.writeTypedList(images);
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

    public int getFeedId() {
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

    public String getSchool() {
        return school;
    }

    public BaseInfoMember getMember() {
        return member;
    }

    public Video getVideo() {
        return video;
    }

    public ArrayList<ImageInfo> getImages() {
        return images;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setNumLike(int numLike) {
        this.like = numLike;
    }

    public void setLikeState(int isLike) {
        this.isLike = isLike;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public void setImages(ArrayList<ImageInfo> images) {
        this.images = images;
    }

    public void setMember(BaseInfoMember member) {
        this.member = member;
    }
}
