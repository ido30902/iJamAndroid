package com.example.ijamapp.Utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton
{

    private static MySingleton instance;
    private RequestQueue requestQueue;
    private static Context context;
    
    private MySingleton(Context ctx)
    {
        this.context = ctx;
        requestQueue = this.getRequestQueue();
    }
    
    public static synchronized MySingleton getInstance(Context ctx)
    {
        if (instance == null)
            instance =  new MySingleton(ctx);
        return instance;
    }
    
    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }
    
    public <T> void addToRequestQueue(Request<T> request)
    {
        this.getRequestQueue().add(request);
    }
}
