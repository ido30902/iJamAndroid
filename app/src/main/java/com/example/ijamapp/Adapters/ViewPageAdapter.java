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
    
    // Fragments
    private ProfileFragment profileFragment;
    private LoopsFragment loopsFragment;
    private iJamLiveFragment iJamLiveFragment;
    private FindFragment findFragment;
    
    
    // Constructor
    public ViewPageAdapter(FragmentManager fragmentManager)
    {
       super(fragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
       
       profileFragment = new ProfileFragment();
       loopsFragment = new LoopsFragment();
       iJamLiveFragment = new iJamLiveFragment();
       findFragment = new FindFragment();
    }
    
    /**
     * gets the current item
     * @param position int position
     * @return the fragment
     */
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
    
    /**
     * gets the item count
     * @return 4 fragments
     */
    @Override
    public int getCount() {
        return 4;
    }
    
    
}
