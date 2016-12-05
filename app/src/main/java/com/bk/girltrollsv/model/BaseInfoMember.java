package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 07-Sep-16.
 */
public class BaseInfoMember implements Parcelable {

    private String memberId;

    private String username;

    private String avatarUrl;

    public BaseInfoMember(String memberId, String username, String avatarUrl) {
        this.memberId = memberId;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    public BaseInfoMember() {}

    protected BaseInfoMember(Parcel in) {
        memberId = in.readString();
        username = in.readString();
        avatarUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeString(username);
        dest.writeString(avatarUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseInfoMember> CREATOR = new Creator<BaseInfoMember>() {
        @Override
        public BaseInfoMember createFromParcel(Parcel in) {
            return new BaseInfoMember(in);
        }

        @Override
        public BaseInfoMember[] newArray(int size) {
            return new BaseInfoMember[size];
        }
    };

    public String getMemberId() {
        return memberId;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
