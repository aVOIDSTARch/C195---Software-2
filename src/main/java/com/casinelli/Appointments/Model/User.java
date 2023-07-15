package com.casinelli.Appointments.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class User extends DBObject{

    private String password;

    public User(int id, String name,String password, LocalDate createDate, String createdBy,
                LocalTime lastUpdate, String lastUpdatedBy) {
        this.password = password;
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;

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

    public String getPassword() {
        return password;
    }
}
