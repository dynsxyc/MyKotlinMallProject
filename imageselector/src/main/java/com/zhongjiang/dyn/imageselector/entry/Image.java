package com.zhongjiang.dyn.imageselector.entry;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 *图片实体类
 */
public class Image implements Parcelable {

    private String path;
    private long time;
    private String name;

    public Image(String path, long time, String name) {
        this.path = path;
        this.time = time;
        this.name = name;
    }

    public Image(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeLong(this.time);
        dest.writeString(this.name);
    }

    protected Image(Parcel in) {
        this.path = in.readString();
        this.time = in.readLong();
        this.name = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }

        Image directory = (Image) o;

        boolean hasId = !TextUtils.isEmpty(name);
        boolean otherHasId = !TextUtils.isEmpty(directory.name);

        if (hasId && otherHasId) {
            if (!TextUtils.equals(name, directory.name)) {
                return false;
            }

            return TextUtils.equals(path, directory.path);
        }

        return false;
    }

}
