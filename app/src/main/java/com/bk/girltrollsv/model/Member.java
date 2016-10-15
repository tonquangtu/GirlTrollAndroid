package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 16-Aug-16.
 */
public class Member extends BaseInfoMember implements Parcelable {

    private String gmail;

    private int rank;

    private int like;

    private int totalImage;

    private int active;

    public Member(String memberId, String username, String avatarUrl, String gmail, int rank, int like, int totalImage, int active) {
        super(memberId, username, avatarUrl);
        this.gmail = gmail;
        this.rank = rank;
        this.like = like;
        this.totalImage = totalImage;
        this.active = active;
    }

    protected Member(Parcel in) {
        super(in);
        gmail = in.readString();
        rank = in.readInt();
        like = in.readInt();
        totalImage = in.readInt();
        active = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(gmail);
        dest.writeInt(rank);
        dest.writeInt(like);
        dest.writeInt(totalImage);
        dest.writeInt(active);
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

    public int getRank() {
        return rank;
    }

    public int getLike() {
        return like;
    }

    public int getTotalImage() {
        return totalImage;
    }

    public int getActive() {
        return active;
    }

    public String getGmail() {
        return gmail;
    }
}
