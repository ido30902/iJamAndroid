package com.example.ijamapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.ijamapp.Classes.SoundManager;
import com.example.ijamapp.R;

import java.io.InputStream;

/**
 * Play Service
 */
public class PlayService extends Service
{
    
    // Variables
    private SoundManager soundManager;
    private MediaPlayer mediaPlayer;
    
    private int tick_int;
    
    /**
     * onBind
     * @param intent intent
     * @return null
     */
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    /**
     * on create service
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
        
    }
    
    /**
     * when service on being started
     * @param intent intent
     * @param flags boolean
     * @param startId int
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        mediaPlayer = new MediaPlayer();
        
        mediaPlayer.setVolume(100,100);
        
        if (intent.getStringExtra("command").equals("tick"))
        {
            InputStream ins = getResources().openRawResource(R.raw.tickdeepfrozenapps397275646);
            
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.tickdeepfrozenapps397275646);
            mediaPlayer.start();
        }
        
        soundManager = intent.getParcelableExtra("sound_manager");
       
        if (intent.getStringExtra("command").equals("play_all"))
        {
            playAll(soundManager);
        }
        else if(intent.getStringExtra("command").equals("play_single"))
        {
            playSingle(soundManager, intent.getIntExtra("position",0));
        }
        else
        {
            //stop(soundManager);
        }
       
        return START_STICKY;
    }
    
    /**
     * plays all of the audiotracks
     * @param soundManager soundManager
     */
    private void playAll(SoundManager soundManager)
    {
        //soundManager.getSoundPool().play(1); TODO - Finish
    }
    
    /**
     * plays a single sound
     * @param soundManager soundManager
     * @param position int in the array
     */
    private void playSingle(SoundManager soundManager, int position)
    {
        //soundManager.getSoundPool().play(2); TODO - Finsih
    }
    
    /**
     * stop everything that is being played
     * @param soundManager
     */
    private void stop(SoundManager soundManager)
    {
        soundManager.getSoundPool().stop(1); // For all
        soundManager.getSoundPool().stop(2); //For singular
        soundManager.getSoundPool().release();
    }
    
    /**
     * called when sevice is stopped
     * @param name intent
     * @return stop service method
     */
    @Override
    public boolean stopService(Intent name) {
        soundManager = name.getParcelableExtra("sound_manager");
        
        stop(soundManager);
        return super.stopService(name);
    }
    
    /**
     * called when service is destroyed
     */
    @Override
    public void onDestroy()
    {
        if (soundManager != null)
        {
            soundManager.getSoundPool().release();
        }
        super.onDestroy();
    }
}
