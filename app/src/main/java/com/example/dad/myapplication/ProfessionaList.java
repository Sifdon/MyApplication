package com.example.dad.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfessionaList extends AppCompatActivity implements View.OnClickListener{


    private Button mechanicButton;
    private Button plumberButton;
    private Button lawyerButton;
    private Button doctorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professiona_list);

        //connect buttons to ui, need to change names in ui
        mechanicButton = (Button) findViewById(R.id.button5);
        plumberButton = (Button) findViewById(R.id.button6);
        lawyerButton = (Button) findViewById(R.id.button7);
        doctorButton = (Button) findViewById(R.id.button);

        //set onclick listeners to button
        mechanicButton.setOnClickListener(this);
        plumberButton.setOnClickListener(this);
        lawyerButton.setOnClickListener(this);
        doctorButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v == mechanicButton){
            showProfessionals("mechanic");
        }
        if(v == plumberButton){
            showProfessionals("plumber");
        }
        if(v == lawyerButton){
            showProfessionals("lawyer");
        }
        if(v == doctorButton){
            showProfessionals("doctor");
        }
    }

    public void showProfessionals(String profession){
        Intent intent = new Intent(ProfessionaList.this, ShowProfessionals.class);
        intent.putExtra("profession", profession);
        startActivity(intent);
        finish();

    }
}
