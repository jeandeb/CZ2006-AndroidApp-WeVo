package com.example.deversity.wevo.Entity;

import java.util.ArrayList;

/**
 * Entity class for VWO
 *@author Fu, Yunhao
 */
public class VWO {
    private  String name;
    private String email;
    private String password;
    private String location;
    private String description;
    private ArrayList<Event> eventList;

    /**
     * VWO entity class
     * @param name Name of VWO
     * @param email Email of VWO
     * @param password Password for VWO
     * @param location Location of VWO
     * @param description Description of VWO
     * @param eventList Events in VWO
     */
    public VWO(String name, String email, String password, String location, String description, ArrayList<Event> eventList) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.location = location;
        this.description = description;
        this.eventList = eventList;
    }

    /**
     * Get name of VWO
     * @return Name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Set new name of VWO
     * @param name Name of VWO
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Get description
     * @return Description of VWO
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set new description of VWO
     * @param newDescription Description of VWO
     */
    public void setDescription(String newDescription){
        this.description = newDescription;
    }

}
