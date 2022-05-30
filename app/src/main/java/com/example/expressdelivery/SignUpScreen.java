package com.example.expressdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpScreen extends AppCompatActivity {

    private TextInputEditText Name_signup;
    private TextInputEditText Mobile_signup;
    private TextInputEditText Address_signup;
    private TextInputEditText Password_signup;
    private TextInputEditText Confirm_Password_signup;
    private MaterialButton Signup_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        setupViews();
        Signup_Button.setOnClickListener(view -> Register());
    }
    public void setupViews()
    {
        Name_signup=findViewById(R.id.name_signup);
        Mobile_signup=findViewById(R.id.mobile_signup);
        Address_signup=findViewById(R.id.address_signup);
        Password_signup=findViewById(R.id.password_signup);
        Confirm_Password_signup=findViewById(R.id.confirm_password_signup);
        Signup_Button=findViewById(R.id.signup_button);
    }
    public void Register()
    {
        if(Name_signup.getText().toString().isEmpty())
            Toast.makeText(this,"Please enter Name",Toast.LENGTH_LONG).show();
        else if(Mobile_signup.getText().toString().isEmpty())
            Toast.makeText(this,"Please enter Phone no",Toast.LENGTH_LONG).show();
        else if(Address_signup.getText().toString().isEmpty())
            Toast.makeText(this,"Please enter Address",Toast.LENGTH_LONG).show();
        else if(Password_signup.getText().toString().isEmpty())
            Toast.makeText(this,"Please enter Password",Toast.LENGTH_LONG).show();
        else if(Confirm_Password_signup.getText().toString().isEmpty())
            Toast.makeText(this,"Please confirm Password",Toast.LENGTH_LONG).show();
        else if(!Confirm_Password_signup.getText().toString().equals(Password_signup.getText().toString())) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show();
        }
        else{
            String url = "http://"+localhost.ip+"/ExpressDelivery/add_users.php?Name=" + Name_signup.getText().toString() +
                    "&Mobile=" + Mobile_signup.getText().toString() + "&Address=" + Address_signup.getText().toString() +
                    "&Password=" + Password_signup.getText().toString();
            RequestQueue rq= Volley.newRequestQueue(this);
            StringRequest sr=new StringRequest(Request.Method.GET, url, response -> {
                if(response.equals("1")) {
                    Toast.makeText(this, "Account Created", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
                    startActivity(intent);
                }
                else if(response.equals("0"))
                    Toast.makeText(this,"This phone no has been taken",Toast.LENGTH_LONG).show();
            }, error -> Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show());
            rq.add(sr);
            }

    }
}