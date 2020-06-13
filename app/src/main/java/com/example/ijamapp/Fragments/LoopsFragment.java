package com.example.ijamapp.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Switch;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ijamapp.Activities.LooperActivity;
import com.example.ijamapp.Adapters.LoopsRecyclerViewAdapter;
import com.example.ijamapp.Classes.Interfaces.IVolleyCallBack;
import com.example.ijamapp.Classes.Post;
import com.example.ijamapp.R;
import com.example.ijamapp.Utilities.MySingleton;
import com.example.ijamapp.Utilities.NetworkProperties;
import com.example.ijamapp.Utilities.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoopsFragment extends Fragment implements LoopsRecyclerViewAdapter.onPressListener
{
    /*Variables*/
    
    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    
    //default
    private ArrayList<Post> posts;
    
    private Bundle default_value_bundle;
    
    //Views
    private FloatingActionButton floatingActionButton;
    
    //PopupWindow + PopupWindow Views
    private PopupWindow new_loop_setup_window;
    private Button PUW_cancel;
    private Button PUW_jam;
    private Switch PUW_switch;
    private EditText PUW_desc;
    
    // Constructor
    public LoopsFragment()
    {
        posts = new ArrayList<>();
    }
    
    /**
     * creates the view
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loops, container, false);
    }
    
    /**
     * When view created
     * @param view View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        setViews();
        
        getValues();
        
        fetchPosts(default_value_bundle.getString("user_id"), new IVolleyCallBack() {
            @Override
            public void onArrival() {
                adapter = new LoopsRecyclerViewAdapter(posts, LoopsFragment.this);
                recyclerView.setAdapter(adapter);
            }
        });
        
        getUserDetails(default_value_bundle.getString("user_id"));
        
        setRecyclerView();
    }
    
    /**
     * when view restored
     * @param savedInstanceState bundle
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        
        setRecyclerView();
    }
    
    /**
     * sets the fragments views
     */
    private void setViews()
    {
        recyclerView = getView().findViewById(R.id.Loops_RecyclerView);
        
        floatingActionButton = getView().findViewById(R.id.Loops_AddLoop);
        
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add new loop
                startLoopCreationProcess(default_value_bundle.getString("user_id"));
            }
        });
    }
    
    /**
     * sets the recycler view
     */
    private void setRecyclerView()
    {
        //fixed size
        recyclerView.setHasFixedSize(true);
        
        //layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        
        //adapter set up
        adapter = new LoopsRecyclerViewAdapter(posts, LoopsFragment.this);
        recyclerView.setAdapter(adapter);
        
        
    }
    
    /**
     * gets the user details
     * @param user_id
     */
    private void getUserDetails(final String user_id)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetworkProperties.SERVER_MANAGER_URL, new Response.Listener<String>() {
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
                            default_value_bundle.putString("username",Utility.trimStringResponse(jsonObject.getString("username")));
                        }
                        else
                        {
                        
                        }
                    }
                    else
                    {
                    
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
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
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
    
    /**
     * fetches the posts from the server
     * @param user_id String
     * @param iVolleyCallBack CallBack Interface
     */
    private void fetchPosts(final String user_id, final IVolleyCallBack iVolleyCallBack)
    {
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, NetworkProperties.SERVER_MANAGER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
        
                    if(jsonObject.has("isValid"))
                    {
                        String ans = Utility.trimBooleanResponse(jsonObject.getString("isValid"));
            
                        if (ans.equals("true"))
                        {
                
                            if (jsonObject.has("posts"))
                            {
                                JSONArray tjsonArray = jsonObject.getJSONArray("users");
                    
                                JSONArray jsonArray = tjsonArray.getJSONArray(0);
                    
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject tempJSONObject = jsonArray.getJSONObject(i);
                                    
                                    String admin_id = tempJSONObject.getString("admin_id");
                                    String post_id = tempJSONObject.getString("admin_id");
                                    
                                    Post newPost = new Post(admin_id,post_id);
                                    
                                    posts.add(newPost);
                                }
                                iVolleyCallBack.onArrival();
                            }
                            else
                            {
                            
                            }
                        }
                        else
                        {
                
                        }
                    }
                    else
                    {
                        //print no result found
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> params = new HashMap<>();
                params.put("request_name","GET_POSTS_LIST");
                params.put("user_id",user_id);
                return params;
            }
        };
    }
    
    /**
     * uploads the post
     * @param post Post
     */
    private void uploadPost(final Post post)
    {
    
        //TODO - Finish
    
    
    
    }
    
    /**
     * update the post
     * @param post
     */
    private void updatePost(Post post)
    {
    
    }
    
    /**
     * gets the values from the bundle
     */
    private void getValues()
    {
        if(getActivity().getIntent() == null)
            return;
    
        default_value_bundle = getActivity().getIntent().getBundleExtra("default_values_bundle");
    }
    
    /**
     * loop setup window
     * @param user_id String
     */
    private void startLoopCreationProcess(String user_id)
    {
        View v = setupNewLoopWindow();
    
        setupNewLoopViews(v);
    
        new_loop_setup_window.showAtLocation(recyclerView, Gravity.CENTER, 0, 0 - 20);
        
    }
    
    /**
     * sets up the window
     * @return View
     */
    private View setupNewLoopWindow()
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        
        View loop_setup = inflater.inflate(R.layout.loop_setup_window,null);
        
        new_loop_setup_window = new PopupWindow(loop_setup, 800, 1200);
        
        new_loop_setup_window.setElevation(5.0f);
        
        return loop_setup;
    }
    
    /**
     * sets up the window's views
     * @param view
     */
    private void setupNewLoopViews(View view)
    {
        //Buttons
        PUW_cancel = view.findViewById(R.id.loopsetup_cancel);
        PUW_jam = view.findViewById(R.id.loopsetup_jam);
        
        PUW_switch = view.findViewById(R.id.loopsetup_switch);
        
        PUW_desc = view.findViewById(R.id.loopsetup_desc);
        
        PUW_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    PUW_switch.setText(R.string._private);
                    return;
                }
                PUW_switch.setText(R.string._public);
            }
        });
        
        PUW_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_loop_setup_window.dismiss();
            }
        });
        
        PUW_jam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                new_loop_setup_window.dismiss();
                
                //Post creation
                Post post = new Post(default_value_bundle.getString("user_id"));
                
                post.setAdmin_username(default_value_bundle.getString("username"));
                
                if (PUW_switch.isChecked())
                    post.setPublic(false);
                
                post.setDescription(PUW_desc.getText().toString());
                
                // Variables
                Intent intent = new Intent(getContext(), LooperActivity.class);
                intent.putExtra("post",post);
                intent.putExtra("isNew",true);
                
                // Looper Activity started
                startActivityForResult(intent,1);
            }
        });
    }
    
    /**
     * onActivityResult
     * @param requestCode int
     * @param resultCode int
     * @param data Intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == 1) //looper activity
        {
            if (resultCode == getActivity().RESULT_FIRST_USER && data != null)
            {
                if (data.getBooleanExtra("keep_post",false))
                {
                    if (data.getBooleanExtra("isNew",false))
                    {
                        Post post = data.getParcelableExtra("post");
    
                        uploadPost(post);
    
                        posts.add(post);
                    }
                    else
                    {
                        Post post = data.getParcelableExtra("post");
                        
                        int position = data.getIntExtra("position",0);
                        
                        posts.set(position,post);
                        
                        updatePost(post);
                    }
                }
            }
            setRecyclerView();
        }
    }
    
    /**
     * onPress Interface of the adapter
     * @param position int
     */
    @Override
    public void onPress(int position)
    {
        Intent intent = new Intent(getContext(),LooperActivity.class);
        intent.putExtra("post",posts.get(position));
        intent.putExtra("isNew",false);
        intent.putExtra("position",position);
        
        startActivityForResult(intent,1);
    }
}
