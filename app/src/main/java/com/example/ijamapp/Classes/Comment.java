package com.example.ijamapp.Classes;

/**
 * class Comment
 */
public class Comment
{
    // Variables
    private String content, user_id;
    private int likes;
    
    /**
     * Constructor
     * @param content String
     * @param user_id String
     * @param likes int count
     */
    public Comment(String content, String user_id, int likes)
    {
        this.content = content;
        this.user_id = user_id;
        this.likes = likes;
    }
    
    /*Setter and Getters*/
    
    /**
     *gets the content of the comment
     * @return String
     */
    public String getContent()
    {
        return this.content;
    }
    
    /**
     * sets the content
     * @param content String
     */
    public void setContent(String content)
    {
        this.content = content;
    }
    
    /**
     * get the user id
     * @return String
     */
    public String getUser_id()
    {
        return this.user_id;
    }
    
    /**
     * sets the user id
     * @param user_id String
     */
    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }
    
    /**
     * gets the likes
     * @return int
     */
    public int getLikes()
    {
        return likes;
    }
    
    /**
     * sets the likes
     * @param likes int
     */
    public void setLikes(int likes)
    {
        this.likes = likes;
    }
    
    /**
     * adds one like
     */
    public void addLike()
    {
        likes++;
    }
    
    
}
