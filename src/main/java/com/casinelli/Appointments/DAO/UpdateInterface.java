package com.casinelli.Appointments.DAO;

import com.casinelli.Appointments.Model.DBObject;

import java.sql.SQLException;

/**
 * Interface for function to Update the database table
 */
public interface UpdateInterface {
    int updateDB(DBObject object) throws SQLException;
}
