package com.casinelli.Appointments.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

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
        this.columnNames.add("User_ID");
        this.columnNames.add("User_Name");
        this.columnNames.add("Password");
        this.columnNames.add("Create_Date");
        this.columnNames.add("Created_By");
        this.columnNames.add("Last_Update");
        this.columnNames.add("Last_Updated_By");
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
