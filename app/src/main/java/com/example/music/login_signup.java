package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class login_signup extends AppCompatActivity {

    Button startpage_login,startpage_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_signup);
        startpage_login = findViewById(R.id.startpage_login);
        startpage_signup = findViewById(R.id.startpage_signup);
    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(login_signup.this,login.class);
        startActivity(intent);
    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(login_signup.this,user_signup.class);
        startActivity(intent);

    }
}