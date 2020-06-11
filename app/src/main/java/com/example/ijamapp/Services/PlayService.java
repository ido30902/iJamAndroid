package com.example.ijamapp.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.ijamapp.Classes.SoundManager;

public class PlayService extends Service
{
    
    private SoundManager soundManager;
    
    
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
        stop(soundManager);
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
    public void onDestroy()
    {
        if (soundManager != null)
        {
            soundManager.getSoundPool().release();
        }
        super.onDestroy();
    }
    
    
}
