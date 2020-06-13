package com.example.ijamapp.Classes;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;

/**
 * SoundManger class
 */
public class SoundManager implements Parcelable {
    
    // Variables
    private SoundPool soundPool;
    private ArrayList<AudioTrack> layers;
    private long max_length;
    private Post post;
    
    /**
     * Constructor
     * @param layers ArrayList
     * @param max_length long
     */
    public SoundManager(ArrayList<AudioTrack> layers, long max_length) {
       
        buildSoundpool();
        
        this.layers = new ArrayList<>();
        this.max_length = max_length;
    }
    
    /**
     *  empty constructor
     */
    public SoundManager()
    {
        buildSoundpool();
        
        layers = new ArrayList<>();
    }
    
    /**
     * Parcelable
     * @param in
     */
    protected SoundManager(Parcel in) {
        max_length = in.readLong();
        layers = new ArrayList<>();
    }
    
    /**
     * Parcelable
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(max_length);
    }
    
    /**
     * Parcelable
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }
    
    /**
     * Parcelable
     */
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
    
    /**
     * SoundPool builder
     */
    private void buildSoundpool()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .build();
        else
            this.soundPool = new SoundPool(99, AudioManager.STREAM_MUSIC, 1);
    }
    
    /**
     * gets the soundpool
     * @return SoundPool
     */
    public SoundPool getSoundPool() {
        return soundPool;
    }
    
    /**
     * load the audioTracks
     * @param audioTracks
     */
    public void loadAudioTracks(ArrayList<AudioTrack> audioTracks)
    {
        
        for (int i = 0; i < audioTracks.size(); i++) {
            soundPool.load(audioTracks.get(i).getFile().getPath(), 1);
        }
    }
    
    /**
     * gets the audioTracks
     * @return ArrayList
     */
    public ArrayList<AudioTrack> getAudioTracks()
    {
        return this.layers;
    }
    
    /**
     * Loads a single sound
     * @param file_path String path
     */
    public void loadSound(String file_path)
    {
        layers.add(new AudioTrack(new File(file_path),post.getAdmin(),post));
    }
    
    /**
     * gets the number of layers
     * @return int
     */
    public int getAudioTracksSize()
    {
        return this.layers.size();
    }
    
    public void addAudioTrack(AudioTrack at)
    {
        layers.add(at);
    }
    
}
