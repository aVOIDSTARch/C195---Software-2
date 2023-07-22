package com.casinelli.Appointments.DAO;

public interface DeleteInterface {
    int deleteFromDB(String columnName, Value matchValue);
}
