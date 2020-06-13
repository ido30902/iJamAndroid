package com.example.ijamapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ijamapp.Classes.AudioTrack;
import com.example.ijamapp.Classes.Post;
import com.example.ijamapp.Classes.User;
import com.example.ijamapp.R;
import com.example.ijamapp.Services.PlayService;
import com.example.ijamapp.Services.RecordService;
import com.example.ijamapp.Utilities.Utility;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LooperActivity extends AppCompatActivity {
    
    
    /* Variables */
    
    // Current User and post
    private Post post;
    private ArrayList<AudioTrack> modifiedLayers;
    private User currentUser;
    
    // Views
    private TextView time;
    private ProgressBar progressBar;
    private ImageView record_button;
    private Button play_pause;
    
    // RecyclerView properties
    private RecyclerView recyclerView;
    
    // Default variables
    private boolean isPlaying, isRecording, isNew;
    private int position; //optional
    
    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);
        
        getValues();
        
        setViews();
        
        setVariables();
    
        askForPermissions(); // Asks only if necessary
    }
    
    /**
     * Called when back button pressed
     */
    @Override
    public void onBackPressed()
    {
        if (modifiedLayers.size() > 0)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Exit?")
                    .setCancelable(true)
                    .setMessage("Are you sure you want to exit?")
                    .setNeutralButton("Cancel", null)
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setResult(RESULT_CANCELED);
                            LooperActivity.this.finish();
                        }
                    })
                    .setPositiveButton("Save and quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
    
                            Intent intent = new Intent();
                            intent.putExtra("post",post);
                            intent.putExtra("keep_post",true);
                            intent.putExtra("isNew",isNew);
                            intent.putExtra("position",position);
                            
                            setResult(RESULT_FIRST_USER,intent);
                            LooperActivity.this.finish();
                        }
                    }).create().show();
        }
        else
        {
            setResult(RESULT_CANCELED);
            this.finish();
        }
    }
    
    /**
     * Extracts the intent values to a bundle
     */
    private void getValues()
    {
        if(getIntent() == null)
            return;
        
          post = getIntent().getParcelableExtra("post");
          isNew = getIntent().getBooleanExtra("isNew",false);
          
          if (!isNew)
              position = getIntent().getIntExtra("position",0);
          // Extract the rest of the details
          
    }
    
    /**
     * sets the activity views
     */
    private void setViews()
    {
        // Progress Bar
        progressBar = findViewById(R.id.Looper_ProgressBar);
        
        // Text views
        time = findViewById(R.id.Looper_Time);
        
        //Image views
        record_button = findViewById(R.id.Looper_ImageViewRecordButton);
        
        // Buttons
        play_pause = findViewById(R.id.Looper_play_pause);
        play_pause.setBackgroundResource(R.drawable.ic_play);
        //play_pause.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.iJamGreen,null));
        
        record_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageRecordState();
                Intent intent;
                
               if (!isRecording)
               {
                   intent = new Intent(getApplicationContext(), RecordService.class);
                   intent.putExtra("command","stop");
                   intent.putExtra("post",post);
                   stopService(intent);
                   
                   addAudioToLoop();
               }
                
                else
               {
                   intent = new Intent(getApplicationContext(), RecordService.class);
                   intent.putExtra("command","record");
                   intent.putExtra("post",post);
                   startService(intent);
               }
            }
        });
        
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managePlayPause();
                Intent intent;
                
                if(!isPlaying)
                {
                    intent = new Intent(getApplicationContext(), PlayService.class);
                    intent.putExtra("sound_manager",post.getSoundManager());
                    stopService(intent);
                    post.getSoundManager().loadSound(Utility.generateAudioFileName(post));
                }
                else
                {
                    intent = new Intent(getApplicationContext(), PlayService.class);
                    intent.putExtra("sound_manager",post.getSoundManager());
                    
                    startService(intent);
                }
            }
        });
        
        setRecyclerView();
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    
    /**
     * assigning the default variables
     */
    private void setVariables()
    {
        modifiedLayers = new ArrayList<>();
        
        isPlaying = false;
        isRecording = false;
        
        
    }
    
    /**
     * sets the recyclerview
     */
    private void setRecyclerView()
    {
    
    
    
    
    
    
    }
    
    /**
     * Asks for the needed permissions
     */
    private void askForPermissions()
    {
        String[] request = new String[3];
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            request[0] = Manifest.permission.RECORD_AUDIO;
            request[1] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            request[2] = Manifest.permission.READ_EXTERNAL_STORAGE;
            ActivityCompat.requestPermissions(LooperActivity.this,request,1);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            request[0] = Manifest.permission.RECORD_AUDIO;
            ActivityCompat.requestPermissions(LooperActivity.this,request,1);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            request[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            ActivityCompat.requestPermissions(LooperActivity.this,request,1);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            request[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
            ActivityCompat.requestPermissions(LooperActivity.this,request,1);
        }
    }
    
    /**
     * manages isplaying state + images
     */
    private void managePlayPause()
    {
        isPlaying = !isPlaying;
        if(isPlaying)
            play_pause.setBackgroundResource(R.drawable.ic_pause);
        else
            play_pause.setBackgroundResource(R.drawable.ic_play);
    }
    
    /**
     * manages the record state + images
     */
    private void manageRecordState()
    {
        isRecording = !isRecording;
        if (isRecording)
        {
            loadThreeSecondsPrepAnimation();
            record_button.setImageResource(R.drawable.ic_stop_record);
        }
        else
            record_button.setImageResource(R.drawable.ic_record);
    }
    
    /**
     * loads preparation animation
     */
    private void loadThreeSecondsPrepAnimation()
    {
        Intent intent = new Intent(getApplicationContext(),PlayService.class);
        intent.putExtra("command","tick");
        //set 3
        startService(intent);
        Utility.waitOneSecond();
        
        //set 2
        startService(intent);
        Utility.waitOneSecond();
        
        //set 1
        startService(intent);
        Utility.waitOneSecond();
        
    }
    
    /**
     * Adds the audioTrack to the post loop
     */
    private void addAudioToLoop()
    {
        File file = new File(Environment.getExternalStorageDirectory() + "/" +  post.getPost_id() + "/" + Utility.generateAudioFileName(post));
        
        AudioTrack audioTrack = new AudioTrack(file,post.getAdmin(),post);
        
        post.getSoundManager().addAudioTrack(audioTrack);
        
        modifiedLayers.add(audioTrack);
        
    }
    
    
}
