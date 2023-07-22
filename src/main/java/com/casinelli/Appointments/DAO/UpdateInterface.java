package com.casinelli.Appointments.DAO;

import com.casinelli.Appointments.Model.DBObject;

public interface UpdateInterface {
    int updateDB(String columnName, Value matchValue, DBObject object);
}
