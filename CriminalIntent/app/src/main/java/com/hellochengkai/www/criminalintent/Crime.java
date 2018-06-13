package com.hellochengkai.www.criminalintent;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by chengkai on 18-6-12.
 */

public class Crime implements Parcelable {
    private UUID mId;
    private String mTitle;
    private String date;
    private boolean mSolved;
    private boolean needCall110;

    public Crime() {
        mId = UUID.randomUUID();
        date = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA).format(new Date());
    }

    protected Crime(Parcel in) {
        mTitle = in.readString();
        date = in.readString();
        mSolved = in.readByte() != 0;
        needCall110 = in.readByte() != 0;
    }

    public static final Creator<Crime> CREATOR = new Creator<Crime>() {
        @Override
        public Crime createFromParcel(Parcel in) {
            return new Crime(in);
        }

        @Override
        public Crime[] newArray(int size) {
            return new Crime[size];
        }
    };

    public String getDate() {
        return date;
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public boolean isNeedCall110() {
        return needCall110;
    }

    public void setNeedCall110(boolean needCall110) {
        this.needCall110 = needCall110;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(date);
        dest.writeByte((byte) (mSolved ? 1 : 0));
        dest.writeByte((byte) (needCall110 ? 1 : 0));
    }
}
