package com.michaelfotiadis.wallpaperswitcher.ui.activity.gallery;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class SelectablePhoto implements Parcelable{

    public static final Creator<SelectablePhoto> CREATOR = new Creator<SelectablePhoto>() {
        @Override
        public SelectablePhoto createFromParcel(final Parcel source) {
            return new SelectablePhoto(source);
        }

        @Override
        public SelectablePhoto[] newArray(final int size) {
            return new SelectablePhoto[size];
        }
    };

    private Uri uri;
    private boolean isSelected = false;

    public SelectablePhoto(final Uri uri) {
        this.uri = uri;
    }

    protected SelectablePhoto(final Parcel in) {
        this.uri = in.readParcelable(Uri.class.getClassLoader());
        this.isSelected = in.readByte() != 0;
    }

    public Uri getUri() {
        return uri;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(final boolean selected) {

        isSelected = selected;
        Log.d("ThumbnailAdapter", "Is selected MOdel: " + getUri().getLastPathSegment() + " | " + isSelected());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(this.uri, flags);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }
}