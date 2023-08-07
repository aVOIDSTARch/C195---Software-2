package com.casinelli.Appointments.DAO;

import com.casinelli.Appointments.Model.DBObject;

import java.sql.SQLException;

public interface CreateInterface {
    int insertIntoDB(DBObject object) throws SQLException;
}
