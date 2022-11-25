package com.example.music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class user_signup extends AppCompatActivity {
    //String _phone, _username, _email, _password;
    TextInputLayout sign_username, sign_email, sign_password, sign_phone;
    Button register, login;
    //Firebase instance
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    TextView deliveryaddressTv;
//    private EmailValidator mEmailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_signup);
        sign_username = findViewById(R.id.sign_username);
        progressBar = findViewById(R.id.progressBar);
        sign_email = findViewById(R.id.sign_email);
        sign_password = findViewById(R.id.sign_password);
        deliveryaddressTv = findViewById(R.id.deliveryaddress);
        sign_phone = findViewById(R.id.sign_phone);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
//        mEmailValidator = new EmailValidator();

        //Initialise Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        deliveryaddressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(user_signup.this, "locations", Toast.LENGTH_SHORT).show();
                deliveryDialog();
            }
        });

    }

    private void deliveryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Locations Available")
                .setItems(UserRoleConstants.roles, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String locations = UserRoleConstants.roles[which];
//                       //product_category.setText(category);
                        deliveryaddressTv.setText(locations);
                    }
                })
                .show();
    }

    String _username, _phone, _email, _password,deliveryaddress;

    public void registerAcount(View view) {
        Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show();
        if (!validateUserName() | !validatePhone() | !validateEmail() | !validatePassword()) {
            return;

        }
        inputData();

    }

    private void inputData() {
        _username = sign_username.getEditText().getText().toString();
        _email = sign_email.getEditText().getText().toString();
        _phone = sign_phone.getEditText().getText().toString();
        _password = sign_password.getEditText().getText().toString();
        deliveryaddress = deliveryaddressTv.getText().toString();
        createAccount();

    }

    //method to create user
    private void createAccount() {
        //initialise email and password
        firebaseAuth.createUserWithEmailAndPassword(_email, _password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //account created
                        Toast.makeText(user_signup.this, "Creating account success", Toast.LENGTH_SHORT).show();
                        saveFirebasedata();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //account FAILED created
                        Toast.makeText(user_signup.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    //method to save created user data to DB
    private void saveFirebasedata() {

        //Toast.makeText(this, ""+_phone+" "+_username+" "+_email+" "+_password, Toast.LENGTH_SHORT).show();
//        createAccount();

        String timestamp = "" + System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", "" + firebaseAuth.getUid());
        hashMap.put("username", "" + _username);
        hashMap.put("email", "" + _email);
        hashMap.put("password", "" + _password);
        hashMap.put("phone", "" + _phone);
        hashMap.put("timestamp",""+timestamp);
        hashMap.put("online", "true");
        hashMap.put("accounttype", ""+deliveryaddress);
//        hashMap.put("deliveryaddress", "" + deliveryaddress);

        //save to database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.child(firebaseAuth.getUid()).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(user_signup.this, "Registration Completed", Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.GONE);
                        Intent i = new Intent(user_signup.this, login.class);
                        startActivity(i);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(user_signup.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private boolean validateUserName() {
        String val = sign_username.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            sign_username.setError("Field Cannot Be Empty");
            return false;

        } else if (val.length() > 20) {
            sign_username.setError("UserName Too Long");
            return false;


        } else if (!val.matches(checkSpaces)) {
            sign_username.setError("WhiteSpaces Not Allowed");
            return false;

        } else {
            sign_username.setError(null);
            sign_username.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateEmail() {
        String val = sign_email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            sign_email.setError("Field Cannot Be Empty");
            return false;

        } else if (!val.matches(checkEmail)) {
            sign_email.setError("Invalid Email");
            return false;

        } else {
            sign_email.setError(null);
            sign_email.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {
        String val = sign_password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                //  "(?=.*[A-Z])" +         //at least 1 upper case letter
                // "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=/.,''?{}()])" + //at least 1 special character
                //"(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            sign_password.setError("Field Cannot Be Empty");
            return false;

        } else if (!val.matches(checkPassword)) {
            sign_password.setError("Must Contain atleast 1 digit,1 lowercase, 1 special Character,atleast 4 characters");
            return false;

        } else {
            sign_password.setError(null);
            sign_password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone() {
        String val = sign_phone.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            sign_password.setError("Field Cannot Be Empty");
            return false;

        } else {
            sign_password.setError(null);
            sign_password.setErrorEnabled(false);
            return true;
        }
    }

}