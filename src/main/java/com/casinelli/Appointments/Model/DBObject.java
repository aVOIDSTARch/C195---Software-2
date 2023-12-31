package com.casinelli.Appointments.Model;

import java.time.*;

/**
 * Abstract base class for database derived classes
 */
public abstract class DBObject {
    ///// Instance Variables /////
    protected int id;
    protected String name;
    protected LocalDateTime createDate;
    protected String createdBy;
    protected LocalDateTime lastUpdate;
    protected String lastUpdatedBy;


    ///// Getters and Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    abstract int getId();
    abstract String getName();
    abstract LocalDateTime getCreateDate();
    abstract String getCreatedBy();
    abstract LocalDateTime getLastUpdate();
    abstract String getLastUpdatedBy();
    //Special Case Getter for ComboBox Strings
    String getIdName(){return this.getId() + " " + this.getName();}

}