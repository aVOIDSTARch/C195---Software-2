package com.casinelli.Appointments.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Division extends DBObject {

    private int countryId;

    public Division(int id, String name, LocalDate createDate, String createdBy, LocalTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.countryId = countryId;
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryId() {
        return countryId;
    }

    @Override
    int getId() {
        return this.id;
    }

    @Override
    String getName() {
        return this.name;
    }

    @Override
    LocalDate getCreateDate() {
        return this.createDate;
    }

    @Override
    String getCreatedBy() {
        return this.createdBy;
    }

    @Override
    LocalTime getLastUpdate() {
        return this.lastUpdate;
    }

    @Override
    String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
}