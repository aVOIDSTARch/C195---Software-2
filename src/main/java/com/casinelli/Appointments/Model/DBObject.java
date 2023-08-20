package com.casinelli.Appointments.Model;

import java.time.*;

public abstract class DBObject {
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    protected int id;
    protected String name;
    protected LocalDateTime createDate;
    protected String createdBy;
    protected LocalDateTime lastUpdate;
    protected String lastUpdatedBy;


    abstract int getId();
    abstract String getName();
    abstract LocalDateTime getCreateDate();
    abstract String getCreatedBy();
    abstract LocalDateTime getLastUpdate();
    abstract String getLastUpdatedBy();
    String getIdName(){return this.getId() + " " + this.getName();};

}