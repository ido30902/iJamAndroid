package com.example.ijamapp.Utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton Object
 */
public class MySingleton
{
    
    // Variables
    private static MySingleton instance;
    private RequestQueue requestQueue;
    private static Context context;
    
    /**
     * constructors
     * @param ctx context
     */
    private MySingleton(Context ctx)
    {
        this.context = ctx;
        requestQueue = this.getRequestQueue();
    }
    
    /**
     * gets the instance
     * @param ctx context
     * @return instance - Singleton object
     */
    public static synchronized MySingleton getInstance(Context ctx)
    {
        if (instance == null)
            instance =  new MySingleton(ctx);
        return instance;
    }
    
    /**
     * gets the volley request queue
     * @return
     */
    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }
    
    /**
     * adds to the volley request queue
     * @param request request object
     * @param <T> generic type
     */
    public <T> void addToRequestQueue(Request<T> request)
    {
        this.getRequestQueue().add(request);
    }
}
