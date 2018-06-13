package com.hellochengkai.www.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by chengkai on 18-6-12.
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private boolean needCall110;
    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public Date getmDate() {
        return mDate;
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
}
