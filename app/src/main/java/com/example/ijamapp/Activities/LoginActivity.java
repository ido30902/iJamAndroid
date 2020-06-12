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


public class LoginActivity extends AppCompatActivity
{
    TextInputEditText email_input, password_input;
    TextView pop_up_message, click_here, signup;
    Button login_button;
    Switch remember_me;
    
    /**
     * This is the login screen and the main screen in which the user's enter first
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        if (!checkReturnFromSignout())
        {
            checkDefaultLogin();
        }
        
        setContentView(R.layout.activity_login);
        
        setViews();
        
    }
    
    private void setViews()
    {
        //Text Views
        pop_up_message = findViewById(R.id.Login_ErrorPopUp);
        click_here = findViewById(R.id.Login_ClickHere);
        signup = findViewById(R.id.Login_ToSignup);
        
        //EditText
        email_input = findViewById(R.id.Login_EmailInput);
        password_input = findViewById(R.id.Login_PasswordInput);
        
        //Switch
        remember_me = findViewById(R.id.Login_RememberMe);
        
        //Button
        login_button = findViewById(R.id.Login_LoginButton);
    
        //When clicked
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                pop_up_message.setText(R.string.blank_string);
                
                if (checkFields(email_input.getText().toString(),password_input.getText().toString()))
                {
                    pop_up_message.setText(R.string.blank_string);
                    
                    makeLoginRequest(email_input.getText().toString(), password_input.getText().toString());
                    login_button.setClickable(false);
                }
            
            }
        });
        
        //When to Sign up is clicked
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });
        
        //When click me on forgot password is pressed
        click_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EmailRecoveryActivity.class));
            }
        });
    }
    
    private boolean checkFields(String email, String password)
    {
        if (email.isEmpty() || password.isEmpty())
        {
            pop_up_message.setText(R.string.fill_all_fields);
            return false;
        }
        if (!Utility.checkEmailFormat(email))
        {
            pop_up_message.setText("Email syntax incorrect");
            return false;
        }
        return true;
    }
    
    private void makeLoginRequest(final String email, final String password)
    {
        StringRequest request = new StringRequest(Request.Method.POST, NetworkProperties.SERVER_MANAGER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
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
                                
                                //saves the login details in the shared preferences
                                if (remember_me.isChecked())
                                {
                                    saveLoginInfo(email, password);
                                }
                                
                                //intent initialize
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                
                                //puts the bundle in the intent
                                intent.putExtra("default_values_bundle",default_values_bundle);
                
                                //start the activity
                                startActivity(intent);
                                
                                //kill the activity
                                LoginActivity.this.finish();
                            }
                            else
                            {
                                pop_up_message.setText(R.string.unknown_error);
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
                                pop_up_message.setText(R.string.unknown_error);
                            }
                        }
                    }
                    else
                    {
                        pop_up_message.setText(R.string.unknown_error);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    pop_up_message.setText(R.string.unknown_error);
                }
                login_button.setClickable(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
                error.printStackTrace();
                pop_up_message.setText(R.string.unknown_error);
                login_button.setClickable(true);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("request_name","LOGIN");
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(request);
        
        //sets the button to clickable again
        
    }
    
    private void checkDefaultLogin()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN",MODE_PRIVATE);
        
        if (sharedPreferences == null)
            return;
        
        if (!sharedPreferences.getBoolean("isLoginSaved",false))
            return;
        
        if (sharedPreferences.contains("email") && sharedPreferences.contains("password"))
        {
            String email = sharedPreferences.getString("email","");
            String password = sharedPreferences.getString("password","");
    
    
            if (!email.isEmpty() && !password.isEmpty())
                makeLoginRequest(email,password);
        }
    }
    
    private void extractResponseError(String error)
    {
        
        switch(error)
        {
            case "email":
                pop_up_message.setText(R.string.email_error);
                break;
    
            case "password":
                pop_up_message.setText(R.string.password_error);
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
        
        if(editor == null || sharedPreferences == null)
            return;
        
        editor.putBoolean("isLoginSaved",true);
        editor.putString("email",email);
        editor.putString("password",password);
        
        editor.apply();
    }
    
    private boolean checkReturnFromSignout()
    {
        if (getIntent() == null)
            return false;
        
        return getIntent().hasExtra("returned_from_signout");
    }
    
    
    
}
