package com.example.dad.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserOrProfessionalActivity extends AppCompatActivity implements View.OnClickListener {

    private Button userButton;
    private Button professionalButton;
    private Button backToLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_or_professional);

        //connect buttons to ui and set onclick listeners
        userButton = (Button) findViewById(R.id.userButton);
        professionalButton = (Button) findViewById(R.id.professionalButton);
        backToLoginButton = (Button) findViewById(R.id.backToLogin);

        userButton.setOnClickListener(this);
        professionalButton.setOnClickListener(this);
        backToLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==backToLoginButton){
            Intent intent = new Intent(UserOrProfessionalActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        if(v==professionalButton){
            Intent intent = new Intent(UserOrProfessionalActivity.this, ProfessionalRegister.class);
            startActivity(intent);
            finish();
        }
    }
}
