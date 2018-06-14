package com.hellochengkai.www.criminalintent;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by chengkai on 18-6-12.
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    private String date;
    private boolean mSolved;
    private boolean needCall110;
    private int position;

    public Crime() {
        mId = UUID.randomUUID();
        date = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA).format(new Date());
    }

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
