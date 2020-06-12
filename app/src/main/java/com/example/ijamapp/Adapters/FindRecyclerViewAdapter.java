package com.example.ijamapp.Adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ijamapp.Classes.User;
import com.example.ijamapp.R;
import com.example.ijamapp.Utilities.RoundImage;
import com.example.ijamapp.Utilities.Utility;

import java.util.ArrayList;

public class FindRecyclerViewAdapter extends RecyclerView.Adapter<FindRecyclerViewAdapter.FindViewHolder> {
    
    //Variables
    ArrayList<User> users;
    private OnPressListener onPressListener;
    
    /**
     * View holder class
     */
    class FindViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        
        // Views
        ImageView profilePicture;
        TextView username;
        OnPressListener onPressListener;
        
        // Constructor
        FindViewHolder(View itemView, OnPressListener onPressListener)
        {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.find_recyclerview_item_imageview);
            username = itemView.findViewById(R.id.find_recyclerview_item_textView);
            
            this.onPressListener = onPressListener;
            
            itemView.setOnClickListener(this);
        }
    
        @Override
        public void onClick(View v) {
            onPressListener.onPress(getAdapterPosition());
        }
    }
    
    // Constructor
    public FindRecyclerViewAdapter(ArrayList<User> usr, OnPressListener monPressListener)
    {
        users = usr;
        this.onPressListener = monPressListener;
    }
    
    /**
     *
     * @param parent parent layout
     * @param viewType viewtype
     * @return new ViewHolder
     */
    @NonNull
    @Override
    public FindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_recyclerview_person_item, parent,false);
        
        return new FindViewHolder(view, onPressListener);
    }
    
    /**
     * binds the viewholder to the views
     * @param holder viewholder
     * @param position adapter position
     */
    @Override
    public void onBindViewHolder(@NonNull FindViewHolder holder, int position) {
        
        //sets the username
        holder.username.setText(users.get(position).getUsername());
        
        //sets the profile picture
        if(users.get(position).getProfilePicture() == null)
        {
            holder.profilePicture.setImageResource(R.drawable.baseline_person_black_18dp);
            
        }
        else
        {
            holder.profilePicture.setImageBitmap(users.get(position).getProfilePicture());
        }
        Utility.makeTheImageRound(holder.profilePicture);
    }
    
    /**
     * gets the item count
     * @return int item count
     */
    @Override
    public int getItemCount() {
        return users.size();
    }
    
    /**
     * on press listener
     */
    public interface OnPressListener
    {
        void onPress(int position);
    }
    
}
