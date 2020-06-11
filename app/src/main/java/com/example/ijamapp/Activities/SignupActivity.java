package com.example.ijamapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
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

public class SignupActivity extends AppCompatActivity
{
    
    TextInputEditText email_input, password_input, username_input, password_confirmation_input;
    TextView pop_up_message, signup, toLogin;
    Button signup_button;
    Switch remember_me;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        
        
        setViews();
        
    }
    
    private void setViews()
    {
        //input init
        email_input = findViewById(R.id.Signup_EmailInput);
        username_input = findViewById(R.id.Signup_UsernameInput);
        password_input = findViewById(R.id.Signup_PasswordInput);
        password_confirmation_input = findViewById(R.id.Signup_PasswordConfirmationInput);
        
        //TextViews
        pop_up_message = findViewById(R.id.Signup_ErrorPopUp);
        signup = findViewById(R.id.Signup_SignupText);
        toLogin = findViewById(R.id.Signup_ToLogin);
        
        //Switch
        remember_me = findViewById(R.id.Signup_RememberMe);
        
        //Button
        signup_button = findViewById(R.id.Signup_SignupButton);
        
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                pop_up_message.setText(R.string.blank_string);
                
                if(checkFields(email_input.getText().toString(),username_input.getText().toString(),password_input.getText().toString(),password_confirmation_input.getText().toString()))
                {
                    makeSignupRequest(email_input.getText().toString(), username_input.getText().toString(), password_input.getText().toString());
                    signup_button.setClickable(false);
                }
                
            }
        });
        
    }
    
    private boolean checkFields(String email, String username, String password, String password_confirmation)
    {
        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || password_confirmation.isEmpty())
        {
            pop_up_message.setText(R.string.fill_all_fields);
            return false;
        }
        
        if (!Utility.checkEmailFormat(email))
        {
            pop_up_message.setText("Email syntax incorrect");
            return false;
        }
    
        if (Utility.containsIllegals(username))
        {
            pop_up_message.setText(R.string.illegal_characters);
            return false;
        }
        
        if (!password.equals(password_confirmation))
        {
            pop_up_message.setText("Passwords don't match");
            return false;
        }
        return true;
    }
    
    private void makeSignupRequest(final String email, final String username, final String password)
    {
        StringRequest request = new StringRequest(Request.Method.POST, NetworkProperties.SERVER_MANAGER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    
                    if (jsonObject.has("isValid"))
                    {
                        String ans = Utility.trimBooleanResponse(jsonObject.getString("isValid"));
                        
                        if(ans.equals("true"))
                        {
                            if (jsonObject.has("id"))
                            {
                                //extracts the user's id
                                String user_id = Utility.trimStringResponse(jsonObject.getString("id"));
                                
                                //bundle init and values insertion
                                Bundle default_values_bundle = new Bundle();
                                default_values_bundle.putString("user_id",user_id);
                                default_values_bundle.putString("email",email);
                                default_values_bundle.putString("username",username);
    
                                //saves the login details in the sharedpreferences
                                if(remember_me.isChecked())
                                {
                                    saveLoginInfo(email,password);
                                }
                                
                                //intent init
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                
                                //puts the bundle in the intent
                                intent.putExtra("default_values_bundle",default_values_bundle);
                                
                                //start the activity
                                startActivity(intent);
                                
                                SignupActivity.this.finish();
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
                                extractResponseError(Utility.trimStringResponse(jsonObject.getString("reason")));
                            }
                            else
                            {
                                pop_up_message.setText(R.string.response_error);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                pop_up_message.setText(R.string.response_error);
            }
        })
            //Parameters
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("request_name","SIGN_UP");
                params.put("email",email);
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(request);
    }
    
    private void extractResponseError(String error)
    {
        switch(error)
        {
            case "username":
                pop_up_message.setText(R.string.username_taken);
                break;
                
            case "email":
                pop_up_message.setText(R.string.email_taken);
                break;
                
            default:
                pop_up_message.setText(R.string.unknown_error);
                break;
        }
    }
    
    
    private void saveLoginInfo(String email, String password)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN",MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
    
        editor.putBoolean("isLoginSaved",true);
        editor.putString("email",email);
        editor.putString("password",password);
    
        editor.apply();
    }
    
    private void checkPasswordStrength(String passeword)
    {
    
        
        
    }
    
    
    
    
    
}
