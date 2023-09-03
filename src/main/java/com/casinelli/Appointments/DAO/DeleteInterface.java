package com.casinelli.Appointments.DAO;

import java.sql.SQLException;

/**
 * Interface for function to delete data from the database table
 */
public interface DeleteInterface {
    int deleteFromDB(Value<?> matchValue) throws SQLException;
}
