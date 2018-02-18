package com.example.deversity.wevo.mgr;

import android.widget.ListView;

import com.example.deversity.wevo.Entity.Event;
import com.example.deversity.wevo.Entity.Job;
import com.example.deversity.wevo.Entity.VWO;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

/**
 * Control class for VWO
 * @author Fu, Yunhao;
 */

public class ShowVWOMgr {
    private String selectedVWOName;
    private String selectedEventName;
    private String selectedJobName;

    public String getSelectedVWOName() {
        return selectedVWOName;
    }

    public void setSelectedVWOName(String selectedVWOName) {
        this.selectedVWOName = selectedVWOName;
    }

    public String getSelectedEventName() {
        return selectedEventName;
    }

    public void setSelectedEventName(String selectedEventName) {
        this.selectedEventName = selectedEventName;
    }

    public String getSelectedJobName() {
        return selectedJobName;
    }

    public void setSelectedJobName(String selectedJobName) {
        this.selectedJobName = selectedJobName;
    }
}