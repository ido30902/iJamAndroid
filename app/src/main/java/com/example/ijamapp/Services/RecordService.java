package com.example.ijamapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;

import com.example.ijamapp.Classes.AudioTrack;
import com.example.ijamapp.Classes.Post;

import java.io.File;

public class RecordService extends Service {
    
    private MediaRecorder mediaRecorder;
    private File file = null;
    
    private File dir = null;
    
    private boolean isRecoding;
    
    private Post post;
    
    
    
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        setVariables();
        
        getValues();
        
        initMediaRecorder();
        
        return START_STICKY;
    }
    
    private void getValues()
    {
    
    }
    
    private void setVariables()
    {
        dir = new File(Environment.getExternalStorageDirectory() + post.getPost_id() + "/");
    }
    
    private void initMediaRecorder()
    {
    
        mediaRecorder = new MediaRecorder();
        
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        
        
        //Finish the prep
        
    
    
    
    
    }
    
    private void stopRecording()
    {
        if (mediaRecorder != null)
        {
            mediaRecorder.stop();
            mediaRecorder.release();
        }
    }
    
    @Override
    public void onDestroy() {
        
        if (isRecoding)
            stopRecording();
        
        super.onDestroy();
    }
}
