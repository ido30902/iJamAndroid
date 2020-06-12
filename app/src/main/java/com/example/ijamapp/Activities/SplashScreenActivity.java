package com.example.ijamapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.ijamapp.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity
{
    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    
        EasySplashScreen screen = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(LoginActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(R.color.iJamGreen)
                .withFooterText("Copyright &#169; 2020, iJam.live")
                .withLogo(R.mipmap.ic_ijam1_round);
    
        View SplashScreen = screen.create();
        setContentView(SplashScreen);
        
    }
}
