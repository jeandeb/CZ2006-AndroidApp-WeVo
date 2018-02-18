package com.example.deversity.wevo.mgr;

import com.example.deversity.wevo.Entity.Event;
import com.example.deversity.wevo.Entity.Job;
import com.example.deversity.wevo.Entity.VWO;
import com.example.deversity.wevo.Entity.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by kidfu on 2017/10/28.
 */
public class VWOClientMgr {
    private FirebaseUser USER = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    /**
     * VWO create event
     * @param EventName Name of event
     * @param newEvent Event object
     */
    public void createEvent(String EventName, Event newEvent){
        Map<String, Object> EventData = new HashMap<>();
        EventData.put(EventName, newEvent);
        mRootRef.child("VWO").child("id").child(USER.getUid()).child("Events").updateChildren(EventData);
    }

    /**
     * VWO edit event
     * @param Description New description of event
     */
    public void editDescription(String Description){
        mRootRef.child("VWO").child("id").child(USER.getUid()).child("description").setValue(Description);
    }

    /**
     * VWO add job to event
     * For future version ONLY
     * @param newJob New job object
     * @param EventName Event name
     */
    public void addJobToEvent(Job newJob, String EventName){
        Map<String, Object> JobData = new HashMap<>();
        JobData.put(newJob.getName(), newJob);
        mRootRef.child("VWO").child("id").child(USER.getUid()).child("Events").child(EventName).child("Jobs").updateChildren(JobData);
    }

    /**
     * Log out portal
     */
    public void logOut(){
        FirebaseAuth.getInstance().signOut();
    }
}