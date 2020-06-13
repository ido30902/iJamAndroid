package com.example.ijamapp.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import com.example.ijamapp.Classes.Post;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility
{
    /**
     * checks the email format
     * @param email String
     * @return boolean
     */
    public static boolean checkEmailFormat(String email) {
        if (email == null)
            return false;
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
    
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
    
    /**
     * converts bitmap to String
     * @param bitmap Bitmap
     * @return String
     */
    public static String bitmapToString(Bitmap bitmap) {
        if (bitmap == null)
            return "error";
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
    
    /**
     * Coverts String to bitmap
     * @param encodedString String
     * @return Bitmap
     */
    public static Bitmap StringToBitMap(String encodedString) {
        try
        {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * trins the boolean response from json
     * @param str String
     * @return String
     */
    public static String trimBooleanResponse(String str)
    {
        if(str == null)
            return "error";
        return str.substring(1, str.length() - 1);
    }
    
    /**
     * trins the String response from json
     * @param str String
     * @return String
     */
    public static String trimStringResponse(String str)
    {
        if(str == null)
            return "error";
        return str.substring(2, str.length() - 2);
    }
    
    /**
     * Checks if a String contains any illegal chars
     * @param toExamine String
     * @return boolean
     */
    public static boolean containsIllegals(String toExamine)
    {
        Pattern pattern = Pattern.compile("[~#@*+%{}<>\\[\\]|\"\\ ^]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }
    
    /**
     * the the imageview round
     * @param imageView ImageView - View
     */
    public static void makeTheImageRound(ImageView imageView)
    {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        if(drawable == null)
            return;
        
        Bitmap bitmap = drawable.getBitmap();
        if(bitmap == null)
            return;
        
        imageView.setImageDrawable(new RoundImage(bitmap));
    }
    
    /**
     * Waits one second
     */
    public static void waitOneSecond()
    {
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Waits x seconds
     * @param i x
     */
    public static void waitAmountOfSeconds(int i)
    {
        try
        {
            TimeUnit.SECONDS.sleep(i);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * generates file name for audio tracl
     * @param post post object
     * @return String
     */
    public static String generateAudioFileName(Post post)
    {
        return post.getPost_id() + "_" + (post.getSoundManager().getAudioTracksSize() + 1);
    }
    
    
    
    
    
    
    
    
}
