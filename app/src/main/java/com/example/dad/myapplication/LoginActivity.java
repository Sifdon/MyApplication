package com.example.dad.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
//yoyoyo
    private Button registerButton;//Register Button
    private Button signInButton;//Sign in Button
    private EditText editTextEmail;//email field
    private EditText editTextPassword;//password field

    private FirebaseAuth firebaseAuth;//firebase auth reference
    private FirebaseUser user;//firebase user reference


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //if user has logged before, skip current login
        /*
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, ProfessionaList.class);
            startActivity(intent);
        }
        */

        firebaseAuth = FirebaseAuth.getInstance();//initialize firebase auth instance

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        //wire buttons up to ui and set onclick listener
        registerButton = (Button) findViewById(R.id.registerButton);
        signInButton = (Button) findViewById(R.id.signInButton);
        registerButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }




    //method for logging in with email
    private void userLogin(){
        //get text fields
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //make sure user has entered email and password
        if(TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this, "Enter email",
                    Toast.LENGTH_LONG).show();
            return;

        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Enter password",
                    Toast.LENGTH_LONG).show();
            return;
        }else{
            //loading wheel
            final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Intent intent = new Intent(LoginActivity.this, ProfessionaList.class);
                                startActivity(intent);
                                progress.dismiss();

                            }else{
                                //need to get errors to display to user like invalid password,email...
                                Log.d("error",task.toString());
                                progress.dismiss();
                            }
                        }
                    });
        }
    }


    @Override
    public void onClick(View v) {
        if(v==signInButton){
            userLogin();
        }

        if(v==registerButton){
            Intent intent = new Intent(LoginActivity.this, UserOrProfessionalActivity.class);
            startActivity(intent);
            finish();
        }

    }
}

