package com.casinelli.Appointments.DAO;

import com.casinelli.Appointments.Model.DBObject;

import java.sql.SQLException;

/**
 * Interface for function to Create data in the database table
 */
public interface CreateInterface {
    int insertIntoDB(DBObject object) throws SQLException;
}
