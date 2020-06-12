package com.example.ijamapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.IBinder;

import com.example.ijamapp.Classes.SoundManager;
import com.example.ijamapp.R;

public class PlayService extends Service
{
    
    private SoundManager soundManager;
    private MediaPlayer mediaPlayer;
    
    private int tick_int;
    
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        mediaPlayer = new MediaPlayer();
        SoundPool tick;
        
        if (intent.getStringExtra("command").equals("tick"))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                tick = new SoundPool.Builder()
                        .setMaxStreams(99)
                        .build();
            else
                tick = new SoundPool(99, AudioManager.STREAM_MUSIC, 1);
    
            tick_int = tick.load(getApplicationContext(), R.raw.tickdeepfrozenapps397275646,1);
    
            tick.play(tick_int,100,100,1,0,0);
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
    
    private void playAll(SoundManager soundManager)
    {
        //soundManager.getSoundPool().play(1); TODO - Finish
    }
    
    private void playSingle(SoundManager soundManager, int position)
    {
        //soundManager.getSoundPool().play(2); TODO - Finsih
    }
    
    private void stop(SoundManager soundManager)
    {
        soundManager.getSoundPool().stop(1); // For all
        soundManager.getSoundPool().stop(2); //For singular
        soundManager.getSoundPool().release();
    }
    
    @Override
    public boolean stopService(Intent name) {
        soundManager = name.getParcelableExtra("sound_manager");
        
        stop(soundManager);
        return super.stopService(name);
    }
    
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
