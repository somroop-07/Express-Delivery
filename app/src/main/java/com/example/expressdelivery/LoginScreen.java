package com.example.expressdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginScreen extends AppCompatActivity {

    private TextInputEditText mobile_login;
    private TextInputEditText password_login;
    private MaterialButton login;
    private MaterialButton login_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setUpButtons();
        login.setOnClickListener(view -> {
              LoginActivity();
        });
        login_signup.setOnClickListener(view -> {
              Intent intent=new Intent(LoginScreen.this,SignUpScreen.class);
              startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void setUpButtons()
    {
        mobile_login=findViewById(R.id.mobile_login);
        password_login=findViewById(R.id.password_login);
        login=findViewById(R.id.login_button);
        login_signup=findViewById(R.id.login_signup);
    }
    public void LoginActivity()
    {
        if(mobile_login.getText().toString().isEmpty())
            Toast.makeText(this,"Please enter Mobile no",Toast.LENGTH_LONG).show();
        else if(password_login.getText().toString().isEmpty())
            Toast.makeText(this,"Please enter Password",Toast.LENGTH_LONG).show();
        else{
            String url = "http://"+localhost.ip+"//ExpressDelivery/Login.php?Mobile="+mobile_login.getText().toString()
                      +"&Password="+password_login.getText().toString();
            RequestQueue rq= Volley.newRequestQueue(this);
            StringRequest sr=new StringRequest(Request.Method.GET, url, response -> {
                if(response.equals("1")) {
                    UserInfo.mobile=mobile_login.getText().toString();
                    Intent intent = new Intent(LoginScreen.this, Home.class);
                    startActivity(intent);
                }
                else if(response.equals("0"))
                    Toast.makeText(this,"This mobile is not registered",Toast.LENGTH_LONG).show();
                else if(response.equals("-1"))
                    Toast.makeText(this,"Incorrect Password",Toast.LENGTH_LONG).show();
            }, error -> Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show());
            rq.add(sr);
        }
    }
}