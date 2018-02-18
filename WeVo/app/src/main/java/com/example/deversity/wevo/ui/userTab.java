package com.example.deversity.wevo.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deversity.wevo.Login.Login;
import com.example.deversity.wevo.R;
import com.example.deversity.wevo.mgr.VolunteerClientMgr;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * UserTab is a boundary class for showing volunteer information
 * @author John;
 */
public class userTab extends Fragment {
    FirebaseUser USER = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = mRootRef.child("Vol").child("id").child(USER.getUid());

    EditText mEditTextName;
    EditText mEditTextBrief;
    Button mButtonNameModify;
    Button mButtonBriefModify;
    Button mButtonLogOut;
    String UserName;
    String UserDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_usertab, container, false);
        mButtonBriefModify = rootView.findViewById(R.id.buttonBriefModify);
        mButtonNameModify = rootView.findViewById(R.id.buttonNameModify);
        mEditTextName = rootView.findViewById(R.id.editTextName);
        mEditTextBrief = rootView.findViewById(R.id.editTextBrief);
        mButtonLogOut = rootView.findViewById(R.id.btnLogout);
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserName = dataSnapshot.child("name").getValue(String.class);
                UserDescription = dataSnapshot.child("description").getValue(String.class);
                mEditTextName.setText(UserName);
                mEditTextBrief.setText(UserDescription);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }
    public void onStart(){
        super.onStart();

        final VolunteerClientMgr userTabMgr= new VolunteerClientMgr();
        mButtonNameModify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                userTabMgr.SetNameUSER(mEditTextName.getText().toString());
            }
        });

        mButtonBriefModify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                userTabMgr.SetDescriptionUSER(mEditTextBrief.getText().toString());
            }
        });

        mButtonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Log you out", Toast.LENGTH_SHORT ).show();
                userTabMgr.LogOut();
                startActivity(new Intent(getContext(), Login.class));
                try {
                    finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}
