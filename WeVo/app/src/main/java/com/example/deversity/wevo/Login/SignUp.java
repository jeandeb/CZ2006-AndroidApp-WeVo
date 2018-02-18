package com.example.deversity.wevo.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deversity.wevo.Entity.Event;
import com.example.deversity.wevo.Entity.Job;
import com.example.deversity.wevo.Entity.VWO;
import com.example.deversity.wevo.Entity.Volunteer;
import com.example.deversity.wevo.ui.VWOView;
import com.example.deversity.wevo.ui.VolunteerView;
import com.example.deversity.wevo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Combined boundary and control class for sign up
 */
public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignUp;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private ProgressBar progressBar;
    private EditText editTextName;
    private EditText editTextDescription;
    private Switch switchUserType;
    private FirebaseAuth firebaseAuth;
    private EditText editTextLocation;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null ){
            //profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), VolunteerView.class));

        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
        switchUserType = (Switch) findViewById(R.id.switchUserType);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextLocation = (EditText) findViewById(R.id.editTextLocation);
        buttonSignUp.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
        switchUserType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editTextLocation.setVisibility(isChecked? View.INVISIBLE : View.VISIBLE);
            }
        });

    }

    /**
     * Register one user main method
     */
    private void registerUser() {

        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String description = editTextDescription.getText().toString().trim();
        final String location = editTextLocation.getText().toString().trim();

        if(!(validate(email))){
            //The length of password is wrong
            Toast.makeText(this, "Please input right email address", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;
        }

        if(password.length()<5 || password.length()>18){
            //The length of password is wrong
            Toast.makeText(this, "Please input 5 to 18 characters for password", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;
        }

        if(name.matches("[a-zA-Z]")){
            //The length of password is wrong
            Toast.makeText(this, "Please only input English characters", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;
        }


        if(description.toCharArray().length>128){
            //The length of password is wrong
            Toast.makeText(this, "Description exceeds 128 characters", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;
        }

        if(location.toCharArray().length>40){
            //The length of password is wrong
            Toast.makeText(this, "Location exceeds 40 characters", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;
        }

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;

        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;
        }

        if (TextUtils.isEmpty(name)) {
            //name is empty
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;
        }

        if (TextUtils.isEmpty(description)) {
            //description is empty
            Toast.makeText(this, "Please Enter Description", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;
        }

        if (!switchUserType.isChecked()){
            if (TextUtils.isEmpty(location)) {
                //name is empty
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                //stopping the execution
                return;
            }
        }


        //if validations are ok
        progressBar.setVisibility(View.VISIBLE);


        if (switchUserType.isChecked()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password + "VOL")
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful()) {
                                //user is successfully registered and logged in
                                if (firebaseAuth.getCurrentUser() != null) {
                                    //profile activity
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), VolunteerView.class));
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        // TODO: Change between VWO and Volunteer, add Name, remove Password, add description
                                        ArrayList<String> startingJob = new ArrayList<>();
                                        Volunteer newVolunteer = new Volunteer(name, user.getEmail(), "password", description, startingJob);
                                        Map<String, Object> volunteerData = new HashMap<>();
                                        volunteerData.put(user.getUid(), newVolunteer);
                                        mRootRef.child("Vol").child("id").updateChildren(volunteerData);
                                    }
                                }
                            } else {
                                FirebaseException e = (FirebaseException) task.getException();
                                Log.e("LoginActivity", "Failed Registration", e);
                                //Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password + "VWO")
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressBar.setVisibility(View.INVISIBLE);

                            if (task.isSuccessful()) {
                                //user is successfully registered and logged in
                                if (firebaseAuth.getCurrentUser() != null) {
                                    //profile activity
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), VWOView.class);
                                    intent.putExtra("MODE", "VWO" );
                                    startActivity( intent );
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        ArrayList<Event> emptyEvent = new ArrayList<>();
                                        VWO newVWO = new VWO(name, user.getEmail(), "password", location, description, emptyEvent);
                                        Map<String, Object> VWOData = new HashMap<>();
                                        VWOData.put(user.getUid(), newVWO);
                                        mRootRef.child("VWO").child("id").updateChildren(VWOData);
                                    }

                                }
                            } else {
                                FirebaseException e = (FirebaseException) task.getException();
                                Log.e("LoginActivity", "Failed Registration", e);
                                //Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignUp) {
            registerUser();

        }
        if (view == textViewSignIn ) {
            //will open login activity
            finish();
            startActivity( new Intent(this, Login.class ));
        }
    }
}
