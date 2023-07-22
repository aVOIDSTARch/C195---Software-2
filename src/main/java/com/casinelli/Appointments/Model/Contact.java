package com.casinelli.Appointments.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Contact extends DBObject{
    private int id;
    private String name;
    private String email;

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    LocalDate getCreateDate() {
        return null;
    }

    @Override
    String getCreatedBy() {
        return null;
    }

    @Override
    LocalDateTime getLastUpdate() {
        return null;
    }
    @Override
    String getLastUpdatedBy() {
        return null;
    }

    public String getEmail() {
        return email;
    }
};
