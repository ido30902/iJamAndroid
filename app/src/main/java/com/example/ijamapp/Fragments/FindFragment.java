package com.example.ijamapp.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ijamapp.Activities.GuestProfileActivity;
import com.example.ijamapp.Adapters.FindRecyclerViewAdapter;
import com.example.ijamapp.Classes.User;
import com.example.ijamapp.Classes.Interfaces.IVolleyCallBack;
import com.example.ijamapp.R;
import com.example.ijamapp.Utilities.MySingleton;
import com.example.ijamapp.Utilities.NetworkProperties;
import com.example.ijamapp.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment implements FindRecyclerViewAdapter.OnPressListener
{
    
    private SearchView searchView;
    private RecyclerView recyclerView;
    private FindRecyclerViewAdapter findRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    
    private ArrayList<User> users;
    
    private Bundle default_value_bundle;
    
    public FindFragment()
    {
    
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    
        setVariables();
        
        setViews();
        
        getValues();
        
        
    }
    
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
    }
    
    private void setViews()
    {
        //Search view
        searchView = getView().findViewById(R.id.Find_SearchView);
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //None - Do not fill
                return false;
            }
        
            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery(newText, new IVolleyCallBack() {
                    @Override
                    public void onArrival()
                    {
                        findRecyclerViewAdapter = new FindRecyclerViewAdapter(users, FindFragment.this);
                        recyclerView.setAdapter(findRecyclerViewAdapter);
                        
                    }
                });
                users.clear();
                return false;
            }
        });
    
        setRecyclerView();
    
    }
    
    public void setVariables()
    {
        users = new ArrayList<>();
    }
    
    private void setRecyclerView()
    {
        recyclerView = getView().findViewById(R.id.Find_RecyclerView);
        
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        
        findRecyclerViewAdapter = new FindRecyclerViewAdapter(users, FindFragment.this);
        recyclerView.setAdapter(findRecyclerViewAdapter);
    }
    
    private void searchQuery(final String query, final IVolleyCallBack volleyCallBack)
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
                            
                            if (jsonObject.has("users"))
                            {
                                JSONArray tjsonArray = jsonObject.getJSONArray("users");
                                
                                JSONArray jsonArray = tjsonArray.getJSONArray(0);
                                
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject tempJSONObject = jsonArray.getJSONObject(i);
                                   
                                    User newUser = new User(tempJSONObject.getString("user_id"));
                                    newUser.setUsername(tempJSONObject.getString("username"));
                                    if (!default_value_bundle.getString("user_id").equals(newUser.getUser_id()))
                                        users.add(newUser);
                                }
                                volleyCallBack.onArrival();
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
                        //print not match
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                volleyCallBack.onArrival();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("request_name","SEARCH_USERS");
                params.put("key",query);
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }
    
    private void getValues()
    {
        if(getActivity().getIntent() == null)
            return;
        
        default_value_bundle = getActivity().getIntent().getBundleExtra("default_values_bundle");
    }
    
    
    @Override
    public void onPress(int position)
    {
        Intent intent = new Intent(getActivity(), GuestProfileActivity.class);
        intent.putExtra("user",users.get(position));
        startActivity(intent);
    }
}
