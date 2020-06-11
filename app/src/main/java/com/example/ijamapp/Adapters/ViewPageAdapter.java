package com.example.ijamapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ijamapp.Fragments.FindFragment;
import com.example.ijamapp.Fragments.LoopsFragment;
import com.example.ijamapp.Fragments.ProfileFragment;
import com.example.ijamapp.Fragments.iJamLiveFragment;

public class ViewPageAdapter extends FragmentPagerAdapter {
    
    private ProfileFragment profileFragment;
    private LoopsFragment loopsFragment;
    private iJamLiveFragment iJamLiveFragment;
    private FindFragment findFragment;
    
    
   public ViewPageAdapter(FragmentManager fragmentManager)
   {
       super(fragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
       
       profileFragment = new ProfileFragment();
       loopsFragment = new LoopsFragment();
       iJamLiveFragment = new iJamLiveFragment();
       findFragment = new FindFragment();
   }
   
    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position)
       {
           case 0:
               return profileFragment;
           
           case 1:
               return loopsFragment;
    
           case 2:
               return iJamLiveFragment;
    
           case 3:
               return findFragment;
               
       }
       return null;
    }
    
    @Override
    public int getCount() {
        return 4;
    }
    
    
}
