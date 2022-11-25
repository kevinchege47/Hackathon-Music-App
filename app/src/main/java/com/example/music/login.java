package com.example.music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {
    TextInputLayout login_email, login_password;
    Button login_button, signup_button, forgetpassword_button;
    //FIREBASE INSTANCE
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        signup_button = findViewById(R.id.signup_button);
        forgetpassword_button = findViewById(R.id.forgetpassword_button);
        //Initialise firebase instance
        firebaseAuth = FirebaseAuth.getInstance();

    }


    public void callSignUp(View view) {
        Intent intent = new Intent(login.this, user_signup.class);
        startActivity(intent);
    }


    public void letUserLogIn(View view) {
        if (!validateEmail() | !validatePassword()) {
            return;
        }
        loginUser();

    }

    String _email, _password;
    //method to allow user to login
    private void loginUser() {
        String _email = login_email.getEditText().getText().toString();
        String _password = login_password.getEditText().getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();

            return;
        }
        firebaseAuth.signInWithEmailAndPassword(_email, _password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(login.this, "Sign in reached", Toast.LENGTH_SHORT).show();
                        checkUsertype();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //check if user is buyer or seller to know the screen to navigate to
    private void checkUsertype() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for (DataSnapshot ds : datasnapshot.getChildren()) {
                            String accounttype = "" + ds.child("accounttype").getValue();
                                if (accounttype.equals("Artist")) {
                                Intent intent = new Intent(login.this, FirstBuyerDashboard.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(login.this, seller_dashboard.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(login.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private boolean validateEmail() {
        String val = login_email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            login_email.setError("Field Cannot Be Empty");
            return false;

        } else if (!val.matches(checkEmail)) {
            login_email.setError("Invalid Email");
            return false;

        } else {
            login_email.setError(null);
            login_email.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {
        String val = login_password.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            login_password.setError("Field Cannot Be Empty");
            return false;
        } else {
            login_password.setError(null);
            login_password.setErrorEnabled(false);
            return true;
        }
    }
}
