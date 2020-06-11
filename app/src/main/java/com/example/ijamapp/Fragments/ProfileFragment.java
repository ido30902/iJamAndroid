package com.example.ijamapp.Fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ijamapp.Activities.LooperActivity;
import com.example.ijamapp.Activities.MainActivity;
import com.example.ijamapp.R;
import com.example.ijamapp.Utilities.MySingleton;
import com.example.ijamapp.Utilities.NetworkProperties;
import com.example.ijamapp.Utilities.RoundImage;
import com.example.ijamapp.Utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment
{
    
    private ImageView imageView;
    private Button settings, chat;
    private RecyclerView recyclerView;
    private TextView username_textview;
    private Bundle default_value_bundle;
    
    public ProfileFragment() { }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        
        getValues();
        
        setViews();
        
        //fetches user's profile image
        fetchProfileImage();
        
        Utility.makeTheImageRound(imageView);
        
        //fetches user's username
        fetchUsername(default_value_bundle.getString("user_id"));
        
        askForPermissions();
        
    }
    
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        
        getValues();
        
        setViews();
        
        fetchProfileImage();
        
        fetchUsername(default_value_bundle.getString("user_id"));
        
    }
    
    
    private void setViews()
    {
        //ImageView
        imageView = getView().findViewById(R.id.Profile_image);
        
        //TextView
        username_textview = getView().findViewById(R.id.Profile_Username);
        
        //RecyclerView
        recyclerView = getView().findViewById(R.id.Profile_RecyclerView);
        
        //Buttons
        settings = getView().findViewById(R.id.Profile_SettingsButton);
        chat = getView().findViewById(R.id.Profile_ChatButton);
        
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNewImage();
            }
        });
    }
    
    private void getValues()
    {
        if(getActivity().getIntent() == null)
            return;
        
        default_value_bundle = getActivity().getIntent().getBundleExtra("default_values_bundle");
    }
    
    private void fetchProfileImage()
    {
        StringRequest request = new StringRequest(Request.Method.POST, NetworkProperties.SERVER_MANAGER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
            
            
            
            
            
            
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
        
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
    
    private void fetchUsername(final String user_id)
    {
        StringRequest request = new StringRequest(Request.Method.POST, NetworkProperties.SERVER_MANAGER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    
                    if (jsonObject.has("isValid"))
                    {
                        
                        String ans = Utility.trimBooleanResponse(jsonObject.getString("isValid"));
                        
                        if (ans.equals("true"))
                        {
                            
                            if (jsonObject.has("username"))
                            {
                                username_textview.setText(Utility.trimStringResponse(jsonObject.getString("username")));
                            }
                        }
                        else
                        {
                            //extract error
                        }
                    }
                    else
                    {
                    
                    }
                }
                catch (JSONException e)
                {
                
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
        
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("request_name","GET_USER_DETAILS");
                params.put("user_id",user_id);
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
    
    private void selectNewImage()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    
        builder.setTitle("Choose photo");
        builder.setMessage("Choose from camera or from gallery");
        builder.setCancelable(true);
    
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,123);
            }
        });
        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(galleryIntent.createChooser(galleryIntent,"Select image"),555);
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        });
        builder.create();
        builder.show();
    }
    
    
    //TODO - Fix the gallery result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
    
        Uri imagePath;
        Bitmap picBit;
        
        if(requestCode == 123 && resultCode == getActivity().RESULT_OK)
        {
            picBit = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(picBit);
        }
        else if(requestCode == 555 && resultCode == getActivity().RESULT_OK && data.getData() != null)
        {
            imagePath = data.getData();
            try
            {
                picBit = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagePath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            imageView.setImageResource(R.drawable.baseline_person_black_18dp);
        }
    }
    
    private void askForPermissions()
    {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            String[] request = {Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(getActivity(),request,1);
        }
    }
    
    
    
}
