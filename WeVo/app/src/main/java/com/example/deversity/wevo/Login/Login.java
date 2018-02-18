package com.example.deversity.wevo.Login;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deversity.wevo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Combined boundary and control class for login
 */
public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private String UserType = "User";

    private final static int PERMISSION_FINE_LOCATION = 101;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mVWORef = mRootRef.child("VWO").child("id");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user asynchronously -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        //Auto-login if there is a user once logged in before
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null ) {
                    final FirebaseUser USER = firebaseAuth.getCurrentUser();
                    mVWORef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot VWOIDSnapshot: dataSnapshot.getChildren()){
                                if (USER.getUid().equals(VWOIDSnapshot.getKey()))
                                    UserType = "VWO";
                            }
                            if (UserType.equals("VWO")){
                                //Toast.makeText(getApplicationContext(),"Welcome back! VWO!",Toast.LENGTH_LONG).show();
                                Intent intent =new Intent(getApplicationContext(), com.example.deversity.wevo.ui.VWOView.class);
                                intent.putExtra("MODE","VWO");
                                startActivity(intent);
                            }
                            else{
                                //Toast.makeText(getApplicationContext(),"Welcome back! Volunteer!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), com.example.deversity.wevo.ui.VolunteerView.class);
                                intent.putExtra("MODE","VOL");
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        buttonLogin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    /**
     * User login main method
     */
    private void userLogin() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if(password.toCharArray().length<5 && password.toCharArray().length>18){
            //The length of password is wrong
            Toast.makeText(this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;
        }
        if(!email.contains("@")&&!email.contains(".")){
            //The length of password is wrong
            Toast.makeText(this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;
        }
        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT ).show();
            //stopping the execution
            return;

        }
        if(TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT ).show();
            //stopping the execution
            return;
        }
        //if validations are ok
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email,password+"VOL").addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getApplicationContext(),"Bonjour! Volunteer!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), com.example.deversity.wevo.ui.VolunteerView.class);
                intent.putExtra("MODE","VOL");
                startActivity(intent);
                finish();
            }
        });

        firebaseAuth.signInWithEmailAndPassword(email,password+"VWO").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Bonjour! VWO!",Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(getApplicationContext(), com.example.deversity.wevo.ui.VWOView.class);
                    intent.putExtra("MODE","VWO");
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
            userLogin();
        }
        if (view == textViewSignUp ) {
            //will open login activity
            finish();
            startActivity( new Intent(this, SignUp.class ));
        }
    }


    @Override
    public void onBackPressed(){
    }

}