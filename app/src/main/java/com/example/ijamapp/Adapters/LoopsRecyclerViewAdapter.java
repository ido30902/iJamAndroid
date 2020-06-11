package com.example.ijamapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ijamapp.Classes.Post;
import com.example.ijamapp.R;
import com.example.ijamapp.Utilities.Utility;

import java.util.ArrayList;

public class LoopsRecyclerViewAdapter extends RecyclerView.Adapter<LoopsRecyclerViewAdapter.LoopViewHolder>
{
    
    ArrayList<Post> posts;
    private onPressListener onPressListener;
    
    public LoopsRecyclerViewAdapter(ArrayList<Post> postArrayList, onPressListener mOnPressListener)
    {
        this.posts = postArrayList;
        this.onPressListener = mOnPressListener;
    }
    
    
    @NonNull
    @Override
    public LoopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        
        view = layoutInflater.inflate(R.layout.loop_item_layout, parent, false);
        return new LoopViewHolder(view, onPressListener);
    }
    
    @Override
    public void onBindViewHolder(@NonNull LoopViewHolder holder, int position)
    {
        holder.username.setText(posts.get(position).getAdmin_username());
        if(posts.get(position).getAdmin().getProfilePicture() == null)
        {
            holder.profileImage.setImageResource(R.drawable.baseline_person_black_18dp);
        }
        else
        {
            holder.profileImage.setImageBitmap(posts.get(position).getAdmin().getProfilePicture());
        }
        Utility.makeTheImageRound(holder.profileImage);
        
    }
    
    @Override
    public int getItemCount()
    {
        return posts.size();
    }
    
    class LoopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView profileImage;
        TextView username;
        ProgressBar progressBar;
        Button more_options;
        Button extend_retract_upper, extend_retract_lower;
        Button start_pause;
        LinearLayout hiddenLinearLayout;
        
        onPressListener onPressListener;
        
         LoopViewHolder(@NonNull final View itemView, onPressListener mOnPressListener) {
            super(itemView);
            
            // Default views
            profileImage = itemView.findViewById(R.id.loopitem_imgaeview);
            username = itemView.findViewById(R.id.loopitem_username);
            progressBar = itemView.findViewById(R.id.loopitem_progressbar);
            more_options = itemView.findViewById(R.id.loopitem_moreoptions);
            extend_retract_upper = itemView.findViewById(R.id.loopitem_extend_retract_upper);
            start_pause = itemView.findViewById(R.id.loopitem_start_pause);
            hiddenLinearLayout = itemView.findViewById(R.id.loopitem_hiddenlinearlayout);
            extend_retract_lower = itemView.findViewById(R.id.loopitem_extend_retract_lower);
            
            extend_retract_lower.setVisibility(View.GONE);
            
            // On click listener
            onPressListener = mOnPressListener;
            
            // Assigns the click
            itemView.setOnClickListener(this);
            
            extend_retract_upper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isExpanded = posts.get(getAdapterPosition()).isExpanded();
                    hiddenLinearLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    extend_retract_upper.setVisibility(View.GONE);
                    extend_retract_lower.setVisibility(View.VISIBLE);
                }
            });
    
             extend_retract_lower.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     boolean isExpanded = posts.get(getAdapterPosition()).isExpanded();
                     hiddenLinearLayout.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
                     extend_retract_upper.setVisibility(View.VISIBLE);
                     extend_retract_lower.setVisibility(View.GONE);
                 }
             });
            
            
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
