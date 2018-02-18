package com.example.deversity.wevo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.deversity.wevo.Entity.Event;
import com.example.deversity.wevo.Entity.Job;
import com.example.deversity.wevo.Entity.VWO;
import com.example.deversity.wevo.R;
import com.example.deversity.wevo.mgr.VWOClientMgr;

import java.util.ArrayList;

/**
 * EventCreator is a boundary class for creating event and showing event
 * @author Teo
 */
public class EventCreator extends AppCompatActivity implements View.OnClickListener{

    private Button submitButton;
    private Button discardButton;
    private EditText EditTextEventName;
    private EditText EditTextEventDescription;
    private EditText EditTextEventLocation;
    private EditText EditTextEventDate;
    private EditText EditTextEventTime;
    VWOClientMgr VWOMgr = new VWOClientMgr();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator);
        submitButton = (Button) findViewById(R.id.submitButton);
        discardButton = (Button) findViewById(R.id.discardButton);
        EditTextEventDate = findViewById(R.id.editTextEventDate);
        EditTextEventDescription = findViewById(R.id.editTextEventDescription);
        EditTextEventLocation = findViewById(R.id.editTextEventLocation);
        EditTextEventName = findViewById(R.id.editTextEventName);
        EditTextEventTime = findViewById(R.id.editTextEventTime);
    }

    public void onStart(){
        super.onStart();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Job> newJobList = new ArrayList<>();
                String EventName = EditTextEventName.getText().toString();
                String EventDate = EditTextEventDate.getText().toString();
                EventDate = EventDate +  ";" + EditTextEventTime.getText().toString();
                String EventDescription = EditTextEventDescription.getText().toString();
                String EventLocation = EditTextEventLocation.getText().toString();
                Event newEvent = new Event(EventDate, EventDescription,EventLocation, newJobList);
                VWOMgr.createEvent(EventName, newEvent);

                //TODO FOR FURTHER VERSION IMPLEMENTATION (Coming soon)
                /*
                Intent intent = new Intent(EventCreator.this, VWOView.class);
                intent.putExtra("Mode", "VWO" );
                startActivity(intent);
                */
            }
        });
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}