package com.example.deversity.wevo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deversity.wevo.Entity.Event;
import com.example.deversity.wevo.Entity.Job;
import com.example.deversity.wevo.Entity.Volunteer;
import com.example.deversity.wevo.R;
import com.example.deversity.wevo.mgr.VWOClientMgr;

import java.util.ArrayList;

/**
 * JobCreator is a boundary class for creating event and showing event
 * For future version ONLY
 * @author Gehan
 */

public class JobCreator extends AppCompatActivity implements View.OnClickListener{

    private Button AddJobButton;
    private Button FinishButton;
    private EditText EditTextJobName;
    private EditText EditTextJobDescription;
    private EditText EditTextJobAmountNeeded;
    VWOClientMgr VWOMgr = new VWOClientMgr();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_creator);
        AddJobButton = (Button) findViewById(R.id.addJobButton);
        FinishButton = (Button) findViewById(R.id.finishButton);
        EditTextJobAmountNeeded = findViewById(R.id.editTextVolunteerNeeded);
        EditTextJobName = findViewById(R.id.editTextJobName);
        EditTextJobDescription = findViewById(R.id.editTextJobDescription);
    }

    @Override
    protected void onStart(){
        super.onStart();
        AddJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Volunteer> blankVolunteerList = new ArrayList<>();
                String JobName = EditTextJobName.getText().toString();
                String JobDescription = EditTextJobDescription.getText().toString();
                if(tryParse(EditTextJobAmountNeeded.getText().toString())) {
                    int AmountNeeded = Integer.parseInt(EditTextJobAmountNeeded.getText().toString());
                    Job newJob = new Job(JobName, JobDescription, blankVolunteerList, AmountNeeded, AmountNeeded);
                    Bundle bundle = getIntent().getExtras();
                    String EventName = bundle.getString("EventName");
                    VWOMgr.addJobToEvent(newJob, EventName);
                    EditTextJobName.setText("");
                    EditTextJobDescription.setText("");
                    EditTextJobAmountNeeded.setText("");
                    Toast.makeText(JobCreator.this, "Job added", Toast.LENGTH_LONG).show();
                }else{
                    EditTextJobAmountNeeded.setText("");
                    Toast.makeText(JobCreator.this, "Wrong amount input", Toast.LENGTH_LONG).show();
                }
            }
        });
        FinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobCreator.this, "Event Created", Toast.LENGTH_LONG).show();
                startActivity(new Intent(JobCreator.this, VWOView.class));
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    private boolean tryParse(String string){
        try {
            Integer.parseInt(string);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
