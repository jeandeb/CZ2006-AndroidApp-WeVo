package com.example.deversity.wevo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.deversity.wevo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * ListTab is a boundary class for volunteer
 * @author John
 */
public class listTab extends Fragment{
    private View mView;
    private ArrayList<String> VWOArrayList = new ArrayList<>();
    private ListView vwoListView;
    DatabaseReference mVWORef = FirebaseDatabase.getInstance().getReference().child("VWO").child("id");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.activity_listtab, container, false);
        vwoListView = (ListView) mView.findViewById(R.id.vwoList);
        mVWORef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                VWOArrayList = new ArrayList<>();
                for (DataSnapshot VWOSnapshot : dataSnapshot.getChildren()){
                    if (VWOSnapshot.child("name").getValue(String.class) != null)
                        VWOArrayList.add(VWOSnapshot.child("name").getValue(String.class));
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
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vwoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String vwoName = adapterView.getItemAtPosition(i).toString();
                if(vwoName!=null){
                    Toast.makeText(getContext(),"Visit "+vwoName,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), VWOView.class);
                    intent.putExtra("MODE","VOL");
                    intent.putExtra("VWOName",vwoName);
                    startActivity(intent);
                }
            }
        });
    }

}
