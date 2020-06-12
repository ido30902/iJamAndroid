package com.example.ijamapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ijamapp.R;
import com.example.ijamapp.Utilities.MySingleton;
import com.example.ijamapp.Utilities.NetworkProperties;
import com.example.ijamapp.Utilities.Utility;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EmailRecoveryActivity extends AppCompatActivity
{
    
    // Variables
    TextView pop_up_message;
    TextInputEditText email_input;
    Button submit;
    
    /**
     * onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_recovery);
        
        setViews();
        
    }
    
    /**
     * sets the views of the activity
     */
    private void setViews()
    {
        //Text view
        pop_up_message = findViewById(R.id.Forgot_ErrorPopUp);
        
        //TextInputEditText
        email_input = findViewById(R.id.Forgot_EmailInput);
        
        //Button
        submit = findViewById(R.id.Forgot_submit_button);
        
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (checkFields(email_input.getText().toString()))
                {
                    submit.setClickable(false);
                    makeForgotPasswordRequest(email_input.getText().toString());
                }
            }
        });
    }
    
    /**
     * send the request to the server
     * @param email
     */
    private void makeForgotPasswordRequest(final String email)
    {
        StringRequest request = new StringRequest(Request.Method.POST, NetworkProperties.SERVER_MANAGER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                submit.setClickable(true);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    
                    if (jsonObject.has("isValid"))
                    {
                        String ans = jsonObject.getString("isValid");
                        ans = ans.substring(1,ans.length() - 1);
        
                        if(ans.equals("true"))
                        {
                            if (jsonObject.has("id"))
                            {
                                //extracts the user's id
                                String user_id = jsonObject.getString("id");
                                user_id = user_id.substring(2,user_id.length() - 2);
                                
                                pop_up_message.setText("Password form has been sent to you email");
                                
                            }
                            else
                            {
                                pop_up_message.setText(R.string.response_error);
                            }
                        }
                        else
                        {
                            if (jsonObject.has("reason"))
                            {
                                String reason = jsonObject.getString("reason");
                
                                extractResponseError(reason.substring(2,reason.length() - 2));
                            }
                            else
                            {
                                pop_up_message.setText(R.string.response_error);
                            }
                        }
                    }
                    else
                    {
                        pop_up_message.setText(R.string.response_error);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    pop_up_message.setText(R.string.response_error);
                }
                
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                submit.setClickable(false);
                pop_up_message.setText(R.string.response_error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("request_name","FORGOT_PASSWORD");
                params.put("email",email);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(request);
    }
    
    /**
     * analyze the error response
     * @param error
     */
    public void extractResponseError(String error)
    {
        if (error == null)
            return;
        
        switch(error)
        {
            case "username":
                pop_up_message.setText(R.string.email_error);
                break;
                
            case "id":
                pop_up_message.setText(R.string.email_error);
                break;
        
            case "email":
                pop_up_message.setText(R.string.email_error);
                break;
                
            default:
                pop_up_message.setText(R.string.unknown_error);
                break;
        }
        
    }
    
    /**
     * check the fields
     * @param email
     * @return fields are empty
     */
    public boolean checkFields(String email)
    {
        if(email == null)
            return false;
        
        if(email.isEmpty())
        {
            pop_up_message.setText(R.string.fill_all_fields);
            return false;
        }
        if(!Utility.checkEmailFormat(email))
        {
            pop_up_message.setText(R.string.email_error);
            return false;
        }
        return true;
    }
    
    
}
