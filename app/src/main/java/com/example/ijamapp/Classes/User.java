package com.example.ijamapp.Classes;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.ijamapp.Classes.Enums.EInstrument;

import java.util.ArrayList;


public class User implements Parcelable
{
    //Values
    private String user_id, username;
    private Bitmap profilePictuere;
    private ArrayList<User> LISTENSERS, LISTENING;
    private ArrayList<EInstrument> EInstruments;
    
    //Constructor with user id attribute
    public User(String user_id)
    {
        this.user_id = user_id;
    }
    
    //Full constructor
    public User(String user_id, String username, Bitmap profilePictuere, ArrayList<User> LISTENSERS, ArrayList<User> LISTENING)
    {
        this.user_id = user_id;
        this.username = username;
        this.profilePictuere = profilePictuere;
        this.LISTENSERS = LISTENSERS;
        this.LISTENING = LISTENING;
    }
    
    protected User(Parcel in) {
        user_id = in.readString();
        username = in.readString();
        profilePictuere = in.readParcelable(Bitmap.class.getClassLoader());
        LISTENSERS = in.createTypedArrayList(User.CREATOR);
        LISTENING = in.createTypedArrayList(User.CREATOR);
    }
    
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
        
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    
    public String getUser_id()
    {
        return this.user_id;
    }
    
    public void setUser_id(String user_id) {
        
        this.user_id = user_id;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public Bitmap getProfilePicture()
    {
        return this.profilePictuere;
    }
    
    public void setProfilePictuere(Bitmap profilePictuere)
    {
        this.profilePictuere = profilePictuere;
    }
    
    public ArrayList<User> getLISTENSERS()
    {
        return this.LISTENSERS;
    }
    
    public void setLISTENSERS(ArrayList<User> LISTENSERS)
    {
        this.LISTENSERS = LISTENSERS;
    }
    
    public ArrayList<User> getLISTENING()
    {
        return this.LISTENING;
    }
    
    public void setLISTENING(ArrayList<User> LISTENING)
    {
        this.LISTENING = LISTENING;
    }
    
    public int getListensersCount()
    {
        return this.LISTENSERS.size();
    }
    
    public int getListeningCount()
    {
        return this.LISTENING.size();
    }
    
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    
        dest.writeString(user_id);
        dest.writeString(username);
        dest.writeParcelable(profilePictuere, flags);
        dest.writeTypedList(LISTENSERS);
        dest.writeTypedList(LISTENING);
    }
}
