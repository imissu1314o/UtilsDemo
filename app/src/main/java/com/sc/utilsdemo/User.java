package com.sc.utilsdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 2019/6/19.
 */

public class User implements Parcelable {
    private int id;
    private String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this.id );
        dest.writeString( this.name );
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User( source );
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
