package com.example.deversity.wevo.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for Job
 * @author Fu, Yunhao
 */
public class Job {
    private String name;
    private String description;
    private ArrayList<Volunteer> volunteerList;
    private int amountNeed;
    private int vacancy;

    /**
     * Job full constructor
     * @param name Name of job
     * @param description Description of job
     * @param volunteerList Volunteer list for that job
     * @param amountNeed How many volunteer needed in that job
     * @param vacancy How many vacancy left
     */
    public Job(String name, String description, ArrayList<Volunteer> volunteerList, int amountNeed, int vacancy){
        this.name = name;
        this.description = description;
        this.volunteerList = volunteerList;
        this.amountNeed = amountNeed;
        this.vacancy = vacancy;
    }

    /**
     * Set name
     * @param name Name of job
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set description
     * @param Description Description
     */
    public void setDescription(String Description) {
        this.description=Description;
    }

    /**
     * Get name
     * @return Name of job
     */
    public String getName() {
        return name;
    }

    /**
     * Get description
     * @return Description
     */
    public String getDescription() {
        return description;
    }

}
