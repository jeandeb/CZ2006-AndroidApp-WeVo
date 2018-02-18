package com.example.deversity.wevo.mgr;

import android.widget.ListView;

import com.example.deversity.wevo.Entity.Event;
import com.example.deversity.wevo.Entity.Volunteer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Control class for Volunteer Client
 * @author Fu, Yunhao
 */

public class VolunteerClientMgr {
    private FirebaseUser USER = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private static List<MarkerOptions> VWOMarkerList = new ArrayList<>();
    private String pushId;
    private final ShowVWOMgr ShowVWOMgr = new ShowVWOMgr();
    /**
     * Add VWO marker method, to add new marker in the map
     * @param Latitude Latitude of VWO
     * @param Longitude Longitude of VWO
     * @param title Title of VWO
     */
    public void addVWOMarker(double Latitude, double Longitude,String title){

        if(VWOMarkerList!=null){
            VWOMarkerList.add(new MarkerOptions().position(new LatLng(Latitude,Longitude)).title(title));
        }
    }

    /**
     * Attend Event
     * @param EventName Name of event
     */
    public void createEvent(String EventName){
        Map<String, Object> EventData = new HashMap<>();
        EventData.put(EventName, EventName);
        pushId = mRootRef.child("Vol").child("id").child(USER.getUid()).child("Events").push().getKey();
        mRootRef.child("Vol").child("id").child(USER.getUid()).child("Events").child(pushId).setValue(EventName);
    }

    /**
     * Cancel Event
     * @param EventName Name of event
     */
    public void deleteEvent(String EventName){
        mRootRef.child("Vol").child("id").child(USER.getUid()).child("Events").child(pushId).setValue(null);
    }

    /**
     * get vwo marker list
     * @return
     */
    public static List<MarkerOptions> getVWOMarkerList() {
        return VWOMarkerList;
    }

    /**
     * Set user description
     * @param newDescription Description
     */
    public void SetDescriptionUSER(String newDescription){
        mRootRef.child("Vol").child("id").child(USER.getUid()).child("description").setValue(newDescription);
    }

    /**
     * Set user name
     * @param newName Name
     */
    public void SetNameUSER(String newName) {
        mRootRef.child("Vol").child("id").child(USER.getUid()).child("name").setValue(newName);
    }

    /**
     * Log out portal
     */
    public void LogOut(){
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * Get show vwo mgr for map use
     * @return ShowVWOMgr
     */
    public ShowVWOMgr getShowVWOMgr() {
        return ShowVWOMgr;
    }
}