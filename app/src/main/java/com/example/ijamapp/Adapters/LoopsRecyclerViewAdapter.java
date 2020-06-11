package com.example.ijamapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ijamapp.Classes.Post;
import com.example.ijamapp.R;
import com.example.ijamapp.Utilities.Utility;

import java.util.ArrayList;

public class LoopsRecyclerViewAdapter extends RecyclerView.Adapter
{
    
    ArrayList<Post> posts;
    private onPressListener onPressListener;
    
    public LoopsRecyclerViewAdapter(ArrayList<Post> postArrayList, onPressListener mOnPressListener)
    {
        this.posts = postArrayList;
        this.onPressListener = mOnPressListener;
    }
    
    @Override
    public int getItemViewType(int position) {
        
        if (false) //TODO - Change it later
        {
            return 0;
        }
        return 1;
    }
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        
        if (viewType == 1)
        {
            view = layoutInflater.inflate(R.layout.loop_minimal_layout, parent, false);
            return new MinimalLoopViewHolder(view, onPressListener);
        }
        view = layoutInflater.inflate(R.layout.loop_detailed_layout, parent, false);
        return new DetailedLoopViewHolder(view, onPressListener);
    }
    
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (holder.getItemViewType() == 1)
        {
            //Bind minimal view
            
            MinimalLoopViewHolder minimalLoopViewHolder = (MinimalLoopViewHolder) holder;
            
            //minimalLoopViewHolder.username.setText(posts.get(position).getAdmin_username());
            minimalLoopViewHolder.username.setText("it does work");
            if(posts.get(position).getAdmin().getProfilePicture() == null)
            {
                minimalLoopViewHolder.profileImage.setImageResource(R.drawable.baseline_person_black_18dp);
            }
            else
            {
                minimalLoopViewHolder.profileImage.setImageBitmap(posts.get(position).getAdmin().getProfilePicture());
            }
           
    
            Utility.makeTheImageRound(minimalLoopViewHolder.profileImage);
            
        }
        else
        {
            //Bind detailed view
            DetailedLoopViewHolder detailedLoopViewHolder = (DetailedLoopViewHolder) holder;
            
            
        }
        
    }
    
    @Override
    public int getItemCount() {
        return posts.size();
    }
    
    class MinimalLoopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView profileImage;
        TextView username;
        ProgressBar progressBar;
        Button more_options;
        Button extend_retract;
        Button start_pause;
        
        onPressListener onPressListener;
        
         MinimalLoopViewHolder(@NonNull View itemView, onPressListener mOnPressListener) {
            super(itemView);
            
            // Default views
            profileImage = itemView.findViewById(R.id.minloopitem_imgaeview);
            username = itemView.findViewById(R.id.minloopitem_username);
            progressBar = itemView.findViewById(R.id.minloopitem_progressbar);
            more_options = itemView.findViewById(R.id.minloopitem_moreoptions);
            extend_retract = itemView.findViewById(R.id.minloopitem_extend_retract);
            start_pause = itemView.findViewById(R.id.minloopitem_start_pause);
            
            // On click listener
            onPressListener = mOnPressListener;
            
            // Assigns the click
            itemView.setOnClickListener(this);
        }
    
        @Override
        public void onClick(View v) {
            onPressListener.onPress(getAdapterPosition());
        }
    }
    
    class DetailedLoopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        //TODO - fill views
        onPressListener onPressListener;
        
        DetailedLoopViewHolder(@NonNull View itemView, onPressListener mOnPressListener) {
            super(itemView);
            
            //Other views
            
            
            onPressListener = mOnPressListener;
            
            itemView.setOnClickListener(this);
        }
    
        @Override
        public void onClick(View v)
        {
            onPressListener.onPress(getAdapterPosition());
        }
    }
    
    public interface onPressListener
    {
        void onPress(int position);
    }
    
}
