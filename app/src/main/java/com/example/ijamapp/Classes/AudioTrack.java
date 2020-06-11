package com.example.ijamapp.Classes;

import java.io.File;

public class AudioTrack
{
    private File file;
    private User creator;
    private Post post;
    private long elpapsed_time;
    private short volume; //1 - 100
    
    public AudioTrack(File file, User creator, Post post)
    {
        this.file = file;
        this.creator = creator;
        this.post = post;
    }
    
    public AudioTrack(File file, User creator, Post post, long elpapsed_time, short volume)
    {
        this.file = file;
        this.creator = creator;
        this.post = post;
        this.elpapsed_time = elpapsed_time;
        this.volume = volume;
    }
    
    public AudioTrack()
    {
    
    }
    
    public File getFile()
    {
        return file;
    }
    
    public void setFile(File file)
    {
        this.file = file;
    }
    
    public User getCreator()
    {
        return creator;
    }
    
    public void setCreator(User creator)
    {
        this.creator = creator;
    }
    
    public Post getPost()
    {
        return post;
    }
    
    public void setPost(Post post)
    {
        this.post = post;
    }
    
    public long getElpapsed_time()
    {
        return elpapsed_time;
    }
    
    public void setElpapsed_time(long elpapsed_time)
    {
        this.elpapsed_time = elpapsed_time;
    }
    
    public short getVolume()
    {
        return volume;
    }
    
    public void setVolume(short volume)
    {
        this.volume = volume;
    }
}
