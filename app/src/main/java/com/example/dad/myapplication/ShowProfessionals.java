package com.example.dad.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowProfessionals extends AppCompatActivity {

    private String profession;//profession that will come from previous view
    private DatabaseReference mDatabase;//database reference
    private TextView professionTextView;//textview to show profession
    private ListView listOfProffesionals;//list view to show list of professionals
    final ArrayList<String> professionals = new ArrayList<String>();//arraylist for listview


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_professionals);

        listOfProffesionals = (ListView) findViewById(R.id.professional_list);//connect listview to ui
        profession = getIntent().getExtras().getString("profession");//get the type of professional from the previous activity
        professionTextView = (TextView) findViewById(R.id.profession);//connect textview to ui
        professionTextView.setText(profession);//set textview as profession title selected from previous view
        getDataFromFirebase();//load data from database

    }



    public void getDataFromFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference("professionals");//get the professionals node in the database

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            //this method will automatically be called everytime there is an update to the database
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                    if(childSnapshot.child("profession").getValue().toString().equals(profession)){
                        //log the first name for each professional
                        Log.d("professional", childSnapshot.child("firstName").getValue().toString());
                        professionals.add(childSnapshot.child("firstName").getValue().toString()
                                +" "+ childSnapshot.child("lastName").getValue().toString());
                    }

                }

                //add items to listview
                String[] items = new String[professionals.size()];
                for(int i = 0; i < professionals.size(); i++){
                    items[i] = professionals.get(i);
                }
                ArrayAdapter adapter = new ArrayAdapter(ShowProfessionals.this, android.R.layout.simple_list_item_1, items);
                listOfProffesionals.setAdapter(adapter);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }//end getDataFromFirebase


}
