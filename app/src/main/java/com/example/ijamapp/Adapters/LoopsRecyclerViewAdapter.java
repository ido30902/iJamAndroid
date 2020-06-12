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

/**
 * recycler custom adapter class
 */
public class LoopsRecyclerViewAdapter extends RecyclerView.Adapter<LoopsRecyclerViewAdapter.LoopViewHolder>
{
    // Variables
    ArrayList<Post> posts;
    private onPressListener onPressListener;
    
    // Constructor
    public LoopsRecyclerViewAdapter(ArrayList<Post> postArrayList, onPressListener mOnPressListener)
    {
        this.posts = postArrayList;
        this.onPressListener = mOnPressListener;
    }
    
    /**
     * creates the view holder
     * @param parent parent layout
     * @param viewType  view type
     * @return new View holder
     */
    @NonNull
    @Override
    public LoopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        
        view = layoutInflater.inflate(R.layout.loop_item_layout, parent, false);
        return new LoopViewHolder(view, onPressListener);
    }
    
    /**
     * binds the view holder
     * @param holder viewholder
     * @param position int adapter position
     */
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
    
    /**
     * gets the item count
     * @returnm int item count
     */
    @Override
    public int getItemCount()
    {
        return posts.size();
    }
    
    /**
     * view holder class
     */
    class LoopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // Views
        ImageView profileImage;
        TextView username;
        ProgressBar progressBar;
        Button more_options;
        Button extend_retract_upper, extend_retract_lower;
        Button start_pause;
        LinearLayout hiddenLinearLayout;
        
        onPressListener onPressListener;
        
        // Constructor
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
            hiddenLinearLayout.setVisibility(View.GONE);
            
            more_options.setBackgroundResource(R.drawable.ic_more_loop_gray_24dp);
            
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
             
             start_pause.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                    boolean isPlaying = posts.get(getAdapterPosition()).isPlaying();
                     if (isPlaying)
                    {
                        start_pause.setBackgroundResource(R.drawable.ic_pause);
                        //posts.get(getAdapterPosition()).getSoundManager().getSoundPool().play(1); //TODO - Finish
                    }
                    else
                    {
                        start_pause.setBackgroundResource(R.drawable.ic_play);
                        //posts.get(getAdapterPosition()).getSoundManager().getSoundPool().stop(1); TODO - Finish
                    }
                    posts.get(getAdapterPosition()).setPlaying(!isPlaying);
                 }
             });
            
        }
    
        /**
         * onClick
         * @param v view
         */
        @Override
        public void onClick(View v)
        {
            onPressListener.onPress(getAdapterPosition());
        }
    }
    
    /**
     * on press interface
     */
    public interface onPressListener
    {
        void onPress(int position);
    }
    
}
