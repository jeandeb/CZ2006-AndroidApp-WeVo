package com.example.deversity.wevo.Entity;

import java.util.ArrayList;

/**
 * Entity class for Volunteer
 * @author Fu, Yunhao
 */
public class Volunteer {
    private String name;
    private String email;
    private String password;
    private String description;
    private ArrayList<String> eventList;

    /**
     * Constructor for volunteer
     * @param name Volunteer name
     * @param email Volunteer email
     * @param password Volunteer password for encryption use
     * @param description Volunteer description
     * @param eventList Events which volunteer will attend
     */
    public Volunteer(String name, String email, String password, String description, ArrayList<String> eventList) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.eventList = eventList;
    }

    /**
     * Get name
     * @return name of volunteer
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set new name
     * @param name name of volunteer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get description
     * @return Description of volunteer
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set new description
     * @param newDescription Description
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
}
