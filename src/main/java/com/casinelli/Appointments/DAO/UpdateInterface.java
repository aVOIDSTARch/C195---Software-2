package com.casinelli.Appointments.DAO;

import com.casinelli.Appointments.Model.DBObject;

import java.sql.SQLException;

public interface UpdateInterface {
    int updateDB(DBObject object) throws SQLException;
}
