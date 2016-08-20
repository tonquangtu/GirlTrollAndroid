package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 16-Aug-16.
 */
public class ImageInfo implements Parcelable{

    private String feedId;

    private String imageId;

    private String urlImage;

    private int type;

    private String linkFace;

    private String urlImageThumbnail;

    protected ImageInfo(Parcel in) {
        feedId = in.readString();
        imageId = in.readString();
        urlImage = in.readString();
        type = in.readInt();
        linkFace = in.readString();
        urlImageThumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(feedId);
        dest.writeString(imageId);
        dest.writeString(urlImage);
        dest.writeInt(type);
        dest.writeString(linkFace);
        dest.writeString(urlImageThumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageInfo> CREATOR = new Creator<ImageInfo>() {
        @Override
        public ImageInfo createFromParcel(Parcel in) {
            return new ImageInfo(in);
        }

        @Override
        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };

    public String getFeedId() {
        return feedId;
    }

    public String getImageId() {
        return imageId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public int getType() {
        return type;
    }

    public String getLinkFace() {
        return linkFace;
    }

    public String getUrlImageThumbnail() {
        return urlImageThumbnail;
    }
}
