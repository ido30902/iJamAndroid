package com.example.ijamapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;

import com.example.ijamapp.Classes.Post;
import com.example.ijamapp.Classes.SoundManager;
import com.example.ijamapp.Utilities.Utility;

import java.io.File;
import java.io.IOException;

public class RecordService extends Service {
    
    private MediaRecorder mediaRecorder;
    private File file = null;
    private String tempFileName;
    
    private File dir = null;
    
    private boolean isRecoding;
    
    private SoundManager soundManager;
    
    private Post post;
    
    
    
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        getValues(intent);
        
        setVariables();
        
        if (intent.getStringExtra("command").equals("record"))
        {
            initMediaRecorder();
            startRecording();
        }
        else
            stopRecording();
        
        return START_STICKY;
    }
    
    @Override
    public boolean stopService(Intent name) {
        startRecording();
        return super.stopService(name);
    }
    
    private void getValues(Intent values)
    {
        post = values.getParcelableExtra("post");
        
        tempFileName = Utility.generateAudioFileName(post);
    }
    
    private void setVariables()
    {
        dir = new File(Environment.getExternalStorageDirectory() + "/" +  post.getPost_id() + "/");
        
        if (!dir.exists())
        {
            dir.mkdir();
        }
        
        file = new File(dir.getAbsolutePath() + tempFileName);
        
        if (!file.exists())
        {
            file.setWritable(true);
            file.setReadable(true);
        }
    }
    
    private void initMediaRecorder()
    {
        mediaRecorder = new MediaRecorder();
        
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_2_TS);
        
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        
        mediaRecorder.setOutputFile(file);
        
    }
    
    private void startRecording()
    {
        try
        {
            mediaRecorder.prepare();
            mediaRecorder.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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
