package com.example.ijamapp.Classes;

import java.io.File;

/**
 * Audio track class
 */
public class AudioTrack
{
    // Variables
    private File file;
    private User creator;
    private Post post;
    private long elpapsed_time;
    private short volume; //1 - 100
    
    /**
     * Constructor
     * @param file file
     * @param creator user
     * @param post post
     */
    public AudioTrack(File file, User creator, Post post)
    {
        this.file = file;
        this.creator = creator;
        this.post = post;
    }
    
    /**
     * Constructor
     * @param file file
     * @param creator user
     * @param post post
     * @param elpapsed_time long
     * @param volume short
     */
    public AudioTrack(File file, User creator, Post post, long elpapsed_time, short volume)
    {
        this.file = file;
        this.creator = creator;
        this.post = post;
        this.elpapsed_time = elpapsed_time;
        this.volume = volume;
    }
    
    /**
     * empty constructor
     */
    public AudioTrack()
    {
    
    }
    
    /* Setter and getter*/
    
    /**
     * get the file
     * @return file
     */
    public File getFile()
    {
        return file;
    }
    
    /**
     * sets new file
     * @param file file
     */
    public void setFile(File file)
    {
        this.file = file;
    }
    
    /**
     * gets the creator of the layer
     * @return user
     */
    public User getCreator()
    {
        return creator;
    }
    
    /**
     * sets the creator of the layer
     * @param creator user
     */
    public void setCreator(User creator)
    {
        this.creator = creator;
    }
    
    /**
     * gets the post in which the audio track belongs
     * @return
     */
    public Post getPost()
    {
        return post;
    }
    
    /**
     * sets the post in which the audio track belong
     * @param post post
     */
    public void setPost(Post post)
    {
        this.post = post;
    }
    
    /**
     * gets the time
     * @return long times
     */
    public long getElpapsed_time()
    {
        return elpapsed_time;
    }
    
    /**
     * sets the time
     * @param elpapsed_time long
     */
    public void setElpapsed_time(long elpapsed_time)
    {
        this.elpapsed_time = elpapsed_time;
    }
    
    /**
     * gets the volume
     * @return short
     */
    public short getVolume()
    {
        return volume;
    }
    
    /**
     * sets the volume
     * @param volume short
     */
    public void setVolume(short volume)
    {
        this.volume = volume;
    }
    
    
}
