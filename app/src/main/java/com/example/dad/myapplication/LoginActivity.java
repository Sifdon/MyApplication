package com.example.dad.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerButton;//Register Button
    private Button signInButton;//Sign in Button


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = new Intent(LoginActivity.this, ProfessionaList.class);
        startActivity(intent);


        //wire buttons up to ui and set onclick listener
        registerButton = (Button) findViewById(R.id.registerButton);
        signInButton = (Button) findViewById(R.id.signInButton);
        registerButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==registerButton){
            Intent intent = new Intent(LoginActivity.this, UserOrProfessionalActivity.class);
            startActivity(intent);
            finish();
        }

    }
}

