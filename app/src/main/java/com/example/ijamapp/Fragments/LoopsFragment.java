package com.example.ijamapp.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

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
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    
    private ArrayList<Post> posts;
    
    private Bundle default_value_bundle;
    
    private FloatingActionButton floatingActionButton;
    
    private PopupWindow new_loop_setup_window;
    private Button PUW_cancel;
    private Button PUW_jam;
    private Switch PUW_switch;
    
    // Constructor
    public LoopsFragment()
    {
        posts = new ArrayList<>();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loops, container, false);
    }
    
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
        
        setRecyclerView();
    }
    
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        
        setRecyclerView();
    }
    
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
                                //TODO - PRINT ERROR
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
    
    private void uploadPost(final String post_owner, final Post post)
    {
    
        //TODO - Finish
    
    
    
    }
    
    
    private void getValues()
    {
        if(getActivity().getIntent() == null)
            return;
    
        default_value_bundle = getActivity().getIntent().getBundleExtra("default_values_bundle");
    }
    
    private void startLoopCreationProcess(String user_id)
    {
        View v = setupNewLoopWindow();
    
        setupNewLoopViews(v);
    
        new_loop_setup_window.showAtLocation(recyclerView, Gravity.CENTER, 0, 0 - 20);
        
    }
    
    private View setupNewLoopWindow()
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        
        View loop_setup = inflater.inflate(R.layout.loop_setup_window,null);
        
        new_loop_setup_window = new PopupWindow(loop_setup, 800, 1200);
        
        new_loop_setup_window.setElevation(5.0f);
        
        return loop_setup;
    }
    
    private void setupNewLoopViews(View view)
    {
        //Buttons
        PUW_cancel = view.findViewById(R.id.loopsetup_cancel);
        PUW_jam = view.findViewById(R.id.loopsetup_jam);
        
        PUW_switch = view.findViewById(R.id.loopsetup_switch);
        
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
                
                // Variables
                Intent intent = new Intent(getContext(), LooperActivity.class);
                intent.putExtra("post",post);
                
                // Looper Activity started
                startActivityForResult(intent,1);
            }
        });
    }
    
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
                    Post post = data.getParcelableExtra("post");
        
                    uploadPost(default_value_bundle.getString("user_id"),post);
        
                    posts.add(post);
                }
                else
                {
                
                }
            }
            setRecyclerView();
        }
    }
    
    @Override
    public void onPress(int position)
    {
        Intent intent = new Intent(getContext(),LooperActivity.class);
        intent.putExtra("post",posts.get(position));
        startActivityForResult(intent,1);
    }
}
