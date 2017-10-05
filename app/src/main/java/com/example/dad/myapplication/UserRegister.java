package com.example.dad.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRegister extends AppCompatActivity implements View.OnClickListener{

    //text fields on screen
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    //buttons on screen
    private Button register;
    private Button goToLogin;

    //variable to go to database to hold data from screen
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private DatabaseReference mDatabase; //firebase database reference
    private FirebaseUser user;// firebase class to reference user
    private FirebaseAuth firebaseAuth;//firebase authentication class reference



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        //connect text fields to ui
        firstNameEditText = (EditText) findViewById(R.id.firstname);
        lastNameEditText = (EditText) findViewById(R.id.lastname);
        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);

        //connect buttons to ui
        register = (Button) findViewById(R.id.createAccount);
        goToLogin = (Button) findViewById(R.id.backToLogin);

        register.setOnClickListener(this);
        goToLogin.setOnClickListener(this);


        firebaseAuth = FirebaseAuth.getInstance();//initilize firebase auth instance
        mDatabase = FirebaseDatabase.getInstance().getReference();//firebase database reference
    }




    //on click for buttons
    @Override
    public void onClick(View view) {
        if(view == register){
            Log.d("works","here");
            registerUser();
        }

    }


    //create account, add to database
    private void registerUser(){
        //get text from text fields
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        firstName = firstNameEditText.getText().toString().trim();
        lastName = lastNameEditText.getText().toString().trim();

        //validate all text fields
        if(TextUtils.isEmpty(firstName)){
            Toast.makeText(UserRegister.this, "Last name required",
                    Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(lastName)){
            Toast.makeText(UserRegister.this, "First name required",
                    Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(UserRegister.this, "Email is required",
                    Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(UserRegister.this, "Password is required",
                    Toast.LENGTH_LONG).show();
        }else {
            //if all fields are valid
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){


                                user = FirebaseAuth.getInstance().getCurrentUser();//get user id to create child in user database

                                //Hash map to push to database
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("firstName", firstName);
                                map.put("lastName", lastName);
                                mDatabase.child("users").child(user.getUid()).setValue(map);


                                Intent i =new Intent(UserRegister.this, ProfessionaList.class);
                                startActivity(i);

                                //handle exceptions
                            }else {
                                //email already exists
                                if(task.getException().toString()
                                        .equals("com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account.")){
                                    Toast.makeText(UserRegister.this, "The email address is already in use by another account",
                                            Toast.LENGTH_LONG).show();
                                }//password is too short
                                if(task.getException().toString()
                                        .equals("com.google.firebase.FirebaseException: An internal error has occurred. [ WEAK_PASSWORD  ]")){
                                    Toast.makeText(UserRegister.this, "Password must be 6 characters long",
                                            Toast.LENGTH_LONG).show();
                                }//email address is incorrect format
                                if(task.getException().toString()
                                        .equals("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted.")){
                                    Toast.makeText(UserRegister.this, "Invalid email address",
                                            Toast.LENGTH_LONG).show();
                                }
                                //log any other errors
                                Log.v("Heres the erro", task.getException().toString());

                            }
                        }
                    });
        }

    }

}
