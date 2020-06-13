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

/**
 * Record service
 */
public class RecordService extends Service {
    
    
    // Variables
    private MediaRecorder mediaRecorder;
    private File file = null;
    private String tempFileName;
    
    private File dir = null;
    
    private boolean isRecoding;
    
    private SoundManager soundManager;
    
    private Post post;
    
    /**
     * onBind method
     * @param intent intent
     * @return null
     */
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    /**
     * called when startService() is called
     * @param intent intent
     * @param flags int
     * @param startId int
     * @return START_STICKY
     */
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
    
    /**
     * called when stop service is called
     * @param name intent
     * @return on destroy
     */
    @Override
    public boolean stopService(Intent name) {
        startRecording();
        return super.stopService(name);
    }
    
    /**
     * gets the values from the intent
     * @param values intent
     */
    private void getValues(Intent values)
    {
        post = values.getParcelableExtra("post");
        
        tempFileName = Utility.generateAudioFileName(post);
    }
    
    /**
     * sets the variables
     */
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
    
    /**
     * initialize the media recorder
     */
    private void initMediaRecorder()
    {
        mediaRecorder = new MediaRecorder();
        
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_2_TS);
        
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        
        mediaRecorder.setOutputFile(file);
        
    }
    
    /**
     * starts recording
     */
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
    
    /**
     * stops recording
     */
    private void stopRecording()
    {
        if (mediaRecorder != null)
        {
            mediaRecorder.stop();
            mediaRecorder.release();
        }
    }
    
    /**
     * called when onDestroy is called
     */
    @Override
    public void onDestroy() {
        
        if (isRecoding)
            stopRecording();
        
        super.onDestroy();
    }
}
