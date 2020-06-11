package com.example.ijamapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.ProgressBar;

public class PlayService extends Service
{
    
    private MediaPlayer mediaPlayer;
    
    
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
        
        
        
        
        
        
        return START_STICKY;
    }
    
    @Override
    public void onDestroy()
    {
        //stop
        
        
        super.onDestroy();
    }
    
    
}
