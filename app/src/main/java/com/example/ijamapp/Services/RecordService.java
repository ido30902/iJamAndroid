package com.example.ijamapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;

public class RecordService extends Service {
    
    private MediaRecorder mediaRecorder;
    
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    
    
    
}
