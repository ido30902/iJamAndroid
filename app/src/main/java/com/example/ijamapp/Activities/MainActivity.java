package com.example.ijamapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.ijamapp.Adapters.ViewPageAdapter;
import com.example.ijamapp.Fragments.ProfileFragment;
import com.example.ijamapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    
    //MainActivity
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    ViewPageAdapter viewPageAdapter;
    Button instant_record;
    
    //Pop up window
    PopupWindow popupWindow;
    Button PUW_closewindow;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setView();
        
    }
    
    private void setView()
    {
        
        bottomNavigationView = findViewById(R.id.Main_Navigation);
        viewPager = findViewById(R.id.Main_ViewPager);
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPageAdapter);
        
        viewPager.setOffscreenPageLimit(5);
        
        instant_record = findViewById(R.id.Main_InstantRecordButton);
        
        instant_record.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createPopUpWindow();
                return false;
            }
        });
        
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                
                switch (item.getItemId())
                {
                    case R.id.Navigator_profile:
                        viewPager.setCurrentItem(0);
                        break;
                    
                    case R.id.Navigator_loops:
                        viewPager.setCurrentItem(1);
                        break;
    
                    case R.id.Navigator_iJamLive:
                        viewPager.setCurrentItem(2);
                        break;
    
                    case R.id.Navigator_search:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
        
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
        
            }
    
            @Override
            public void onPageSelected(int position) {
                
                switch (position)
                {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.Navigator_profile).setChecked(true);
                        break;
    
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.Navigator_loops).setChecked(true);
                        break;
    
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.Navigator_iJamLive).setChecked(true);
                        break;
    
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.Navigator_search).setChecked(true);
                        break;
                }
            }
    
            @Override
            public void onPageScrollStateChanged(int state)
            {
                
            }
        });
    }
    
    private void createPopUpWindow()
    {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        
        View instantRecordView = inflater.inflate(R.layout.instant_record_layout,null);
    
        popupWindow = new PopupWindow(instantRecordView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT - 56);
        
        popupWindow.setElevation(5.0f);
        
        setPopUpWindowViews(instantRecordView);
        
        popupWindow.showAtLocation(viewPager, Gravity.CENTER,0,0);
    }
    
    public void setPopUpWindowViews(View instant_record_view)
    {
        PUW_closewindow = instant_record_view.findViewById(R.id.intantrecord_close);
        
        PUW_closewindow.setBackgroundResource(R.drawable.ic_close_black_24dp);
        
        PUW_closewindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
    
    
}