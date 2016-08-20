package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 16-Aug-16.
 */
public class Member implements Parcelable {

    private String memberId;

    private String username;

    private String rank;

    private int like;

    private String avatarUrl;

    private int totalImage;

    protected Member(Parcel in) {
        memberId = in.readString();
        username = in.readString();
        rank = in.readString();
        like = in.readInt();
        avatarUrl = in.readString();
        totalImage = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeString(username);
        dest.writeString(rank);
        dest.writeInt(like);
        dest.writeString(avatarUrl);
        dest.writeInt(totalImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    public String getMemberId() {
        return memberId;
    }

    public String getUsername() {
        return username;
    }

    public String getRank() {
        return rank;
    }

    public int getLike() {
        return like;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getTotalImage() {
        return totalImage;
    }
}
