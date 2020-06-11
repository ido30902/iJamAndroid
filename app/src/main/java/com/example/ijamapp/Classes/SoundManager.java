package com.example.ijamapp.Classes;

import android.media.SoundPool;
import android.os.Parcel;
import android.os.Parcelable;

public class SoundManager implements Parcelable
{
    private SoundPool soundPool;
    
    protected SoundManager(Parcel in) {
    }
    
    public static final Creator<SoundManager> CREATOR = new Creator<SoundManager>() {
        @Override
        public SoundManager createFromParcel(Parcel in) {
            return new SoundManager(in);
        }
        
        @Override
        public SoundManager[] newArray(int size) {
            return new SoundManager[size];
        }
    };
    
    public SoundPool getSoundPool() {
        return soundPool;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    
    }
}
