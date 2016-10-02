package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 16-Aug-16.
 */
public class Member extends BaseInfoMember implements Parcelable {

    private float rank;

    private int like;

    private int totalImage;

    protected Member(Parcel in) {
        super(in);
        rank = in.readFloat();
        like = in.readInt();
        totalImage = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeFloat(rank);
        dest.writeInt(like);
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

    public float getRank() {
        return rank;
    }

    public int getLike() {
        return like;
    }

    public int getTotalImage() {
        return totalImage;
    }
}
