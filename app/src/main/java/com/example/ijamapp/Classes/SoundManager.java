package com.example.ijamapp.Classes;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SoundManager implements Parcelable
{
    private SoundPool soundPool;
    ArrayList<AudioTrack> layers;
    private long max_length;
    
    public SoundManager(ArrayList<AudioTrack> layers, long max_length)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(99)
                    .build();
        else
            this.soundPool = new SoundPool(99, AudioManager.STREAM_MUSIC, 1);
            
        this.layers = layers;
        this.max_length = max_length;
    }
    
    protected SoundManager(Parcel in) {
        max_length = in.readLong();
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
    
    public SoundPool getSoundPool()
    {
        return soundPool;
    }
    
    public void loadAudioTracks(ArrayList<AudioTrack> audioTracks)
    {
        
        for(int i = 0; i < audioTracks.size(); i++)
        {
            soundPool.load(audioTracks.get(i).getFile().getPath(),1);
        }
    
    
    
    
    }
    
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    
        dest.writeLong(max_length);
    }
}
