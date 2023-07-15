package com.casinelli.Appointments.Model;

import java.time.*;
public abstract class DBObject {
    protected int id;
    protected String name;
    protected LocalDate createDate;
    protected String createdBy;
    protected LocalTime lastUpdate;
    protected String lastUpdatedBy;

    abstract int getId();
    abstract String getName();
    abstract LocalDate getCreateDate();
    abstract String getCreatedBy();
    abstract LocalTime getLastUpdate();
    abstract String getLastUpdatedBy();
}