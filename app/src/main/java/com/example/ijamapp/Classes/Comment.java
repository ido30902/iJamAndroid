package com.example.ijamapp.Classes;

public class Comment
{
    private String content, user_id;
    private int likes;
    
    public Comment(String content, String user_id, int likes)
    {
        this.content = content;
        this.user_id = user_id;
        this.likes = likes;
    }
    
    public String getContent()
    {
        return this.content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getUser_id()
    {
        return this.user_id;
    }
    
    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }
    
    public int getLikes()
    {
        return likes;
    }
    
    public void setLikes(int likes)
    {
        this.likes = likes;
    }
    
    public void addLike()
    {
        likes++;
    }
    
    
}
