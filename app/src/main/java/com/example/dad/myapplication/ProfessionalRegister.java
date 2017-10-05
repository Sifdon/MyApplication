package com.example.dad.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Map;

public class ProfessionalRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner stateSpinner;
    private Spinner proffesionSpinner;
    private List<String> states;
    private List<String> professions;
    private Button registerButton;

    //vallues that will be passed to the dataabse
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String addressOne;
    private String addressTwo;
    private String city;
    private String zip;
    private String state;
    private String profession;

    //edit text fields on the screen
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText addressOneEditText;
    private EditText addressTwoEditText;
    private EditText cityEditText;
    private EditText zipEditText;

    private DatabaseReference mDatabase; //firebase database reference
    private FirebaseUser user;// firebase class to reference user
    private FirebaseAuth firebaseAuth;//firebase authentication class reference


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_register);

        //create select state spinner
        states =  new ArrayList<String>();
        addStates();
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
        stateSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        //create select profession spinner
        professions = new ArrayList<String>();
        addProfessions();
        proffesionSpinner = (Spinner) findViewById(R.id.professionSpinner);
        proffesionSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> proffesionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, professions);
        proffesionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proffesionSpinner.setAdapter(proffesionAdapter);

        //connect register button to ui and set onclick listener
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);


        //connect text fields to ui
        emailEditText= (EditText) findViewById(R.id.editText9);
        passwordEditText= (EditText) findViewById(R.id.editText11);
        firstNameEditText= (EditText) findViewById(R.id.editText);
        lastNameEditText= (EditText) findViewById(R.id.editText2);
        addressOneEditText= (EditText) findViewById(R.id.editText4);
        addressTwoEditText= (EditText) findViewById(R.id.editText5);
        cityEditText= (EditText) findViewById(R.id.editText6);
        zipEditText= (EditText) findViewById(R.id.editText7);

        firebaseAuth = FirebaseAuth.getInstance();//initilize firebase auth instance
        mDatabase = FirebaseDatabase.getInstance().getReference();//firebase database reference

    }

    public void onClick(View v){
        if(v==registerButton){
            registerUser();
        }
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(view == proffesionSpinner){

        }
        if(view == stateSpinner){

        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    //add states to spinners arraylist
    public void addStates(){
        states.add("AL");
        states.add("AK");
        states.add("AZ");
        states.add("AR");
        states.add("CA");
        states.add("CO");
        states.add("CT");
        states.add("DE");
        states.add("FL");
        states.add("GA");
        states.add("HI");
        states.add("ID");
        states.add("IL");
        states.add("IN");
        states.add("IA");
        states.add("KS");
        states.add("KY");
        states.add("LA");
        states.add("ME");
        states.add("MD");
        states.add("MA");
        states.add("MI");
        states.add("MN");
        states.add("MS");
        states.add("MO");
        states.add("MT");
        states.add("NE");
        states.add("NV");
        states.add("NH");
        states.add("NJ");
        states.add("NM");
        states.add("NY");
        states.add("NC");
        states.add("ND");
        states.add("OH");
        states.add("OK");
        states.add("OR");
        states.add("PA");
        states.add("RI");
        states.add("SC");
        states.add("SD");
        states.add("TN");
        states.add("TX");
        states.add("UT");
        states.add("VT");
        states.add("VA");
        states.add("WA");
        states.add("WV");
        states.add("WI");
        states.add("WY");
    }

    public void addProfessions(){
        professions.add("Doctor");
        professions.add("Lawyer");
        professions.add("Plumber");
        professions.add("Mechanic");
    }


    //create account, add to database
    private void registerUser(){
        //get text from text fields
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        firstName = firstNameEditText.getText().toString().trim();
        lastName = lastNameEditText.getText().toString().trim();
        addressOne = addressOneEditText.getText().toString().trim();
        addressTwo = addressTwoEditText.getText().toString().trim();
        city = cityEditText.getText().toString().trim();
        zip	= zipEditText.getText().toString().trim();
        state = stateSpinner.getSelectedItem().toString();
        profession = proffesionSpinner.getSelectedItem().toString();

        //make sure user has filled out required fields
        if(TextUtils.isEmpty(firstName)){
            Toast.makeText(ProfessionalRegister.this, "Last name required",
                    Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(lastName)){
            Toast.makeText(ProfessionalRegister.this, "First name required",
                    Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(ProfessionalRegister.this, "Email is required",
                    Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(addressOne)){
            Toast.makeText(ProfessionalRegister.this, "Address one required",
                    Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(city)){
            Toast.makeText(ProfessionalRegister.this, "City required",
                    Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(zip)){
            Toast.makeText(ProfessionalRegister.this, "Zip code is required",
                    Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(ProfessionalRegister.this, "Password is required",
                    Toast.LENGTH_LONG).show();
        }else {
            //if all fields are valid
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                user = FirebaseAuth.getInstance().getCurrentUser();//set user id as node in database
                                //data to add to node
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("firstName", firstName);
                                map.put("lastName", lastName);
                                map.put("address1", addressOne);
                                map.put("address2", addressTwo);
                                map.put("city", city);
                                map.put("profession", profession);
                                map.put("rating", "5");
                                map.put("Sstate", state);
                                map.put("zip", zip);
                                mDatabase.child("professionals").child(user.getUid()).setValue(map);


                                Intent i =new Intent(ProfessionalRegister.this, ProfessionaList.class);
                                startActivity(i);

                                //handle exceptions
                            }else {
                                //email already exists
                                if(task.getException().toString()
                                        .equals("com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account.")){
                                    Toast.makeText(ProfessionalRegister.this, "The email address is already in use by another account",
                                            Toast.LENGTH_LONG).show();
                                }//password is too short
                                if(task.getException().toString()
                                        .equals("com.google.firebase.FirebaseException: An internal error has occurred. [ WEAK_PASSWORD  ]")){
                                    Toast.makeText(ProfessionalRegister.this, "Password must be 6 characters long",
                                            Toast.LENGTH_LONG).show();
                                }//email address is incorrect format
                                if(task.getException().toString()
                                        .equals("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted.")){
                                    Toast.makeText(ProfessionalRegister.this, "Invalid email address",
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
