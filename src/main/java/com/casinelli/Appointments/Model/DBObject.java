package com.casinelli.Appointments.Model;

import java.time.*;

public abstract class DBObject {
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

}