package com.example.deversity.wevo.Entity;

import java.util.ArrayList;

/**
 * Entity class for event
 * @author Fu, Yunhao
 */
public class Event {
    private String date;
    private ArrayList<Job> JobList;
    private String description;
    private String location;

    /**
     * Full constructor
     * @param date Date of event
     * @param description Description of event
     * @param location Location of event
     * @param JobList Job list of event
     */
    public Event(String date, String description, String location, ArrayList<Job> JobList) {
        this.date=date;
        this.description=description;
        this.location=location;
        this.JobList=JobList;
        this.location=location;
    }

    /**
     * Get description
     * @return Description
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Set description
     * @param newDescription Description
     */
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
}
