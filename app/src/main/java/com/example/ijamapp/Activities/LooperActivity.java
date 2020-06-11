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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LooperActivity extends AppCompatActivity {
    
    private Post post;
    private ArrayList<AudioTrack> modifiedLayers;
    private User currentUser;
    
    private TextView time;
    private ProgressBar progressBar;
    private ImageView record_button;
    private Button play_pause;
    
    private RecyclerView recyclerView;
    
    private boolean isPlaying, isRecording;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);
        
        getValues();
        
        setViews();
        
        setVariables();
    
        askForPermissions(); // Asks only if necessary
        
    }
    
    
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
    
    private void getValues()
    {
        if(getIntent() == null)
            return;
        
          post = getIntent().getParcelableExtra("post");
          
          // Extract the rest of the details
          
    }
    
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
                
               if (isRecording)
               {
                   intent = new Intent(getApplicationContext(), RecordService.class);
                   stopService(intent);
               }
                
                else
               {
                   intent = new Intent(getApplicationContext(),RecordService.class);
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
    
    private void setVariables()
    {
        modifiedLayers = new ArrayList<>();
        
        isPlaying = false;
        isRecording = false;
        
        
    }
    
    private void setRecyclerView()
    {
    
    
    
    
    
    
    }
    
    private void askForPermissions()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            String[] request = {Manifest.permission.RECORD_AUDIO};
            ActivityCompat.requestPermissions(LooperActivity.this,request,1);
        }
    }
    
    private void managePlayPause()
    {
        isPlaying = !isPlaying;
        if(isPlaying)
            play_pause.setBackgroundResource(R.drawable.ic_pause);
        else
            play_pause.setBackgroundResource(R.drawable.ic_play);
    }
    
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
    
    private void loadThreeSecondsPrepAnimation()
    {
        //set 3
        Utility.waitOneSecond();
        //set 2
        Utility.waitOneSecond();
        //set 1
        Utility.waitOneSecond();
    }
    
}
