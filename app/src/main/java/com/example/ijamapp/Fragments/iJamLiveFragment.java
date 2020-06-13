package com.example.ijamapp.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ijamapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class iJamLiveFragment extends Fragment
{
    
    /**
     * Empty constructor
     */
    public iJamLiveFragment()
    {
        // Required empty public constructor
    }
    
    /**
     * creates the view
     * @param inflater LayoutInflater
     * @param container viewgroup
     * @param savedInstanceState Bundle
     * @return new View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_i_jam_live, container, false);
    }
    
    /**
     * when view created
     * @param view view
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        
        
        
    }
    
    /**
     * when view restored
     * @param savedInstanceState
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        
        
        
        
        
    }
}
