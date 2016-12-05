package com.bk.girltrollsv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 16-Aug-16.
 */
public class ImageInfo implements Parcelable{

    private String imageId;

    private String urlImage;

    private int type;

    private String linkFace;

    private String urlImageThumbnail;

    public ImageInfo(String imageId, String urlImage, int type, String linkFace, String urlImageThumbnail) {
        this.imageId = imageId;
        this.urlImage = urlImage;
        this.type = type;
        this.linkFace = linkFace;
        this.urlImageThumbnail = urlImageThumbnail;
    }

    public ImageInfo() {}

    protected ImageInfo(Parcel in) {
        imageId = in.readString();
        urlImage = in.readString();
        type = in.readInt();
        linkFace = in.readString();
        urlImageThumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setLinkFace(String linkFace) {
        this.linkFace = linkFace;
    }

    public void setUrlImageThumbnail(String urlImageThumbnail) {
        this.urlImageThumbnail = urlImageThumbnail;
    }
}
