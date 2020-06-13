package com.example.ijamapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Post class
 */
public class Post implements Parcelable
{
    // Variables
    private ArrayList<User> likes;
    private ArrayList<Comment> comments;
    private String post_id, description;
    private User admin;
    private ArrayList<User> participants;
    private boolean isPublic, isExpanded, isPlaying;
    private SoundManager soundManager;
    
    /**
     * Constructor
     * @param admin_id String
     * @param post_id String
     */
    public Post(String admin_id, String post_id)
    {
        admin = new User(admin_id);
        this.post_id = post_id;
        soundManager = new SoundManager();
    }
    
    /**
     * Constructor
     * @param admin_id String
     */
    public Post (String admin_id)
    {
        admin = new User(admin_id);
        soundManager = new SoundManager();
    }
    
    /**
     * Constructor
     * @param likes ArrayList
     * @param comments ArrayList
     * @param admin_id String
     * @param post_id String
     * @param participants ArrayList
     */
    public Post(ArrayList<User> likes, ArrayList<Comment> comments, String admin_id, String post_id, ArrayList<User> participants)
    {
        this.admin = new User(admin_id);
        this.likes = likes;
        this.comments = comments;
        this.post_id = post_id;
        this.participants = participants;
        soundManager = new SoundManager();
    }
    
    /**
     * Parcelable
     * @param in
     */
    protected Post(Parcel in) {
        likes = in.createTypedArrayList(User.CREATOR);
        post_id = in.readString();
        description = in.readString();
        admin = in.readParcelable(User.class.getClassLoader());
        participants = in.createTypedArrayList(User.CREATOR);
        isPublic = in.readByte() != 0;
        isExpanded = in.readByte() != 0;
        isPlaying = in.readByte() != 0;
        soundManager = in.readParcelable(SoundManager.class.getClassLoader());
    }
    
    /**
     * Parcelable
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(likes);
        dest.writeString(post_id);
        dest.writeString(description);
        dest.writeParcelable(admin, flags);
        dest.writeTypedList(participants);
        dest.writeByte((byte) (isPublic ? 1 : 0));
        dest.writeByte((byte) (isExpanded ? 1 : 0));
        dest.writeByte((byte) (isPlaying ? 1 : 0));
        dest.writeParcelable(soundManager, flags);
    }
    
    /**
     * Parcelable
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }
    
    /**
     * Parcelable
     */
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
    
    /*Setters and Getters */
    
    /**
     * gets the likes
     * @return ArrayList
     */
    public ArrayList<User> getLikes()
    {
        return likes;
    }
    
    /**
     * sets the likes
     * @param likes ArrayList
     */
    public void setLikes(ArrayList<User> likes)
    {
        this.likes = likes;
    }
    
    /**
     * gets the comments
     * @return ArrayList
     */
    public ArrayList<Comment> getComments()
    {
        return comments;
    }
    
    /**
     *sets the comment
     * @param comments ArrayList
     */
    public void setComments(ArrayList<Comment> comments)
    {
        this.comments = comments;
    }
    
    /**
     * gets the admin id
     * @return String
     */
    public String getAdmin_id()
    {
        return this.admin.getUser_id();
    }
    
    /**
     *  sets the admin id
     * @param admin_id String
     */
    public void setAdmin_id(String admin_id)
    {
        this.admin.setUser_id(admin_id);
    }
    
    /**
     * gets the post id
     * @return String
     */
    public String getPost_id()
    {
        return post_id;
    }
    
    /**
     * sets the post id
     * @param post_id String
     */
    public void setPost_id(String post_id)
    {
        this.post_id = post_id;
    }
    
    /**
     * gets the admin username
     * @return String
     */
    public String getAdmin_username()
    {
        return admin.getUsername();
    }
    
    /**
     * sets the admin username
     * @param admin_username String
     */
    public void setAdmin_username(String admin_username)
    {
        this.admin.setUsername(admin_username);
    }
    
    /**
     * gets the participants
     * @return ArrayList
     */
    public ArrayList<User> getParticipants()
    {
        return participants;
    }
    
    /**
     * sets the participents
     * @param participants ArrayList
     */
    public void setParticipants(ArrayList<User> participants)
    {
        this.participants = participants;
    }
    
    /**
     * gets the comments amount
     * @return int
     */
    public int getCommentCount()
    {
        return this.comments.size();
    }
    
    /**
     * gets the like count
     * @return int
     */
    public int getLikeCount()
    {
        return this.likes.size();
    }
    
    /**
     * sets the admin
     * @param newAdmin User
     */
    public void setAdmin(User newAdmin)
    {
        this.admin = newAdmin;
    }
    
    /**
     * gets the admin
     * @return user
     */
    public User getAdmin()
    {
        return admin;
    }
    
    /**
     * gets if it's public
     * @return boolean
     */
    public boolean isPublic()
    {
        return isPublic;
    }
    
    /**
     * sets if the post is public
     * @param aPublic boolean
     */
    public void setPublic(boolean aPublic)
    {
        isPublic = aPublic;
    }
    
    /**
     * gets the soundmanager
     * @return SoundManager
     */
    public SoundManager getSoundManager()
    {
        return soundManager;
    }
    
    /**
     * sets the SoundManager
     * @param soundManager SoundManager
     */
    public void setSoundManager(SoundManager soundManager)
    {
        this.soundManager = soundManager;
    }
    
    /**
     * check if the view is expanded
     * @return boolean
     */
    public boolean isExpanded()
    {
        return isExpanded;
    }
    
    /**
     * sets the view expansion to boolean given
     * @param expanded boolean
     */
    public void setExpanded(boolean expanded)
    {
        isExpanded = expanded;
    }
    
    /**
     * gets the description
     * @return String
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * sets the description
     * @param description String
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * check if playing
     * @return boolean
     */
    public boolean isPlaying() {
        return isPlaying;
    }
    
    /**
     * sets the isPlaying boolean
     * @param playing
     */
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
