package com.binish.legenddark.mynotes.Database;

import io.realm.RealmObject;


/**
 * Created by legenddark on 2016/04/04.
 */
public class Note extends RealmObject {
    private String mTitle;
    private String mDescription;
    private int mDay;
    private boolean hasPassword;
    private String mPassword;
    private long mAlramTime;
    private long mTimeAdded;

    public Note(String mTitle, String mDescription, int mDay, long mTimeAdded) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDay = mDay;
        this.mTimeAdded = mTimeAdded;
    }
    public Note(){}



    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmDay() {
        return mDay;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public long getmTimeAdded() {
        return mTimeAdded;
    }

    public void setmTimeAdded(long mTimeAdded) {
        this.mTimeAdded = mTimeAdded;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public long getmAlramTime() {
        return mAlramTime;
    }

    public void setmAlramTime(long mAlramTime) {
        this.mAlramTime = mAlramTime;
    }
}
