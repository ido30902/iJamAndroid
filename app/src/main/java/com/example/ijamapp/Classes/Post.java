package com.example.ijamapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Post implements Parcelable
{
    private ArrayList<User> likes;
    private ArrayList<Comment> comments;
    private String post_id;
    private User admin;
    private ArrayList<User> participants;
    private boolean isPublic;
    private SoundManager soundManager;
    
    public Post(String admin_id, String post_id)
    {
        admin = new User(admin_id);
        this.post_id = post_id;
    }
    
    public Post (String admin_id)
    {
        admin = new User(admin_id);
    }
    
    public Post(ArrayList<User> likes, ArrayList<Comment> comments, String admin_id, String post_id, ArrayList<User> participants)
    {
        this.admin = new User(admin_id);
        this.likes = likes;
        this.comments = comments;
        this.post_id = post_id;
        this.participants = participants;
    }
    
    protected Post(Parcel in) {
        likes = in.createTypedArrayList(User.CREATOR);
        post_id = in.readString();
        admin = in.readParcelable(User.class.getClassLoader());
        participants = in.createTypedArrayList(User.CREATOR);
        isPublic = in.readByte() != 0;
    }
    
    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }
        
        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
    
    public ArrayList<User> getLikes()
    {
        return likes;
    }
    
    public void setLikes(ArrayList<User> likes)
    {
        this.likes = likes;
    }
    
    public ArrayList<Comment> getComments()
    {
        return comments;
    }
    
    public void setComments(ArrayList<Comment> comments)
    {
        this.comments = comments;
    }
    
    public String getAdmin_id()
    {
        return this.admin.getUser_id();
    }
    
    public void setAdmin_id(String admin_id)
    {
        this.admin.setUser_id(admin_id);
    }
    
    public String getPost_id()
    {
        return post_id;
    }
    
    public void setPost_id(String post_id)
    {
        this.post_id = post_id;
    }
    
    public String getAdmin_username()
    {
        return admin.getUsername();
    }
    
    public void setAdmin_username(String admin_username)
    {
        this.admin.setUsername(admin_username);
    }
    
    public ArrayList<User> getParticipants()
    {
        return participants;
    }
    
    public void setParticipants(ArrayList<User> participants)
    {
        this.participants = participants;
    }
    
    public int getCommentCount()
    {
        return this.comments.size();
    }
    
    public int getLikeCount()
    {
        return this.likes.size();
    }
    
    public void setAdmin(User newAdmin)
    {
        this.admin = newAdmin;
    }
    
    public User getAdmin()
    {
        return admin;
    }
    
    public boolean isPublic()
    {
        return isPublic;
    }
    
    public void setPublic(boolean aPublic)
    {
        isPublic = aPublic;
    }
    
    public SoundManager getSoundManager()
    {
        return soundManager;
    }
    
    public void setSoundManager(SoundManager soundManager)
    {
        this.soundManager = soundManager;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    
        dest.writeTypedList(likes);
        dest.writeString(post_id);
        dest.writeParcelable(admin, flags);
        dest.writeTypedList(participants);
        dest.writeByte((byte) (isPublic ? 1 : 0));
    }
}