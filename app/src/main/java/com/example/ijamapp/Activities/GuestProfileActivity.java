package com.example.ijamapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ijamapp.Classes.User;
import com.example.ijamapp.R;
import com.example.ijamapp.Utilities.Utility;

public class GuestProfileActivity extends AppCompatActivity {
    
    TextView username, listening, listeners;
    ImageView profilepicture;
    Button listen, send_message;
    
    private User user;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_profile);
    
        getValues();
        
        setViews();
        
    }
    
    private void setViews()
    {
        //Text Views
        username = findViewById(R.id.guestprofile_username);
        listeners = findViewById(R.id.guestprofile_Listeners);
        listening = findViewById(R.id.guestprofile_listening);
        
        //ImageView
        profilepicture = findViewById(R.id.guestprofile_profilepicture);
        
        //Buttons
        listen = findViewById(R.id.guestprofile_listen);
        send_message = findViewById(R.id.guestprofile_sendmessage);
        
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Move to the chat screen and create chat session
            }
        });
        
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add a listener
            }
        });
        
        
        //Value assignment
        username.setText(user.getUsername());
        //listening.setText(user.getListeningCount());
        //listeners.setText(user.getListensersCount());
        
        if (user.getProfilePicture() == null)
        {
            profilepicture.setImageBitmap(user.getProfilePicture());
        }
        else
        {
            profilepicture.setImageResource(R.drawable.baseline_person_black_18dp);
        }
        Utility.makeTheImageRound(profilepicture);
    }
    
    private void getValues()
    {
        if(getIntent() == null)
            return;
        
       user = getIntent().getParcelableExtra("user");
    }
}
