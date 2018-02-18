package com.example.deversity.wevo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.deversity.wevo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * JobTab is a boundary class for volunteer view
 * @author John
 */
public class eventTab extends Fragment{
    private View mView;
    private ArrayList<String> VWOArrayList = new ArrayList<>();
    private ListView vwoListView;
    private FirebaseUser USER = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mVWORef = FirebaseDatabase.getInstance().getReference().child("Vol").child("id").child(USER.getUid()).child("Events");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.activity_eventtab, container, false);
        vwoListView = (ListView) mView.findViewById(R.id.vwoList);
        mVWORef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                VWOArrayList = new ArrayList<>();
                for (DataSnapshot VWOSnapshot : dataSnapshot.getChildren()){
                    if (VWOSnapshot.getValue(String.class) != null)
                        VWOArrayList.add(VWOSnapshot.getValue(String.class));
                }
                ListAdapter vwoAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,VWOArrayList);
                vwoListView.setAdapter(vwoAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return mView;
    }
}