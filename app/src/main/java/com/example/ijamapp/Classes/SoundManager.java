package com.example.ijamapp.Classes;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;

public class SoundManager implements Parcelable {
    private SoundPool soundPool;
    private ArrayList<AudioTrack> layers;
    private long max_length;
    private Post post;
    
    
    public SoundManager(ArrayList<AudioTrack> layers, long max_length) {
       
        buildSoundpool();
        
        this.layers = new ArrayList<>();
        this.max_length = max_length;
    }
    
    public SoundManager()
    {
        buildSoundpool();
        
        layers = new ArrayList<>();
    }
    
    
    protected SoundManager(Parcel in) {
        max_length = in.readLong();
        layers = new ArrayList<>();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(max_length);
    }
    
    @Override
    public int describeContents() {
        return 0;
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
    
    private void buildSoundpool()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .build();
        else
            this.soundPool = new SoundPool(99, AudioManager.STREAM_MUSIC, 1);
    }
    
    
    public SoundPool getSoundPool() {
        return soundPool;
    }
    
    public void loadAudioTracks(ArrayList<AudioTrack> audioTracks) {
        
        for (int i = 0; i < audioTracks.size(); i++) {
            soundPool.load(audioTracks.get(i).getFile().getPath(), 1);
        }
    }
    
    public ArrayList<AudioTrack> getAudioTracks()
    {
        return this.layers;
    }
    
    public void loadSound(String file_name)
    {
        layers.add(new AudioTrack(new File(file_name),post.getAdmin(),post));
    }
    
    public int getAudioTracksSize()
    {
        return this.layers.size();
    }
    
    
    
}
