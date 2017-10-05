package com.example.dad.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowProfessionals extends AppCompatActivity {

    private String profession;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_professionals);

        profession = getIntent().getExtras().getString("profession");
        getDataFromFirebase();
    }




    public void getDataFromFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference("professionals");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            //this method will automatically be called everytime there is an update to the database
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    //log the first name for each professional
                    Log.d("professional", childSnapshot.child("firstName").getValue().toString());
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }//end getDataFromFirebase


}
