package com.casinelli.Appointments.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for function to Retrieve data from the database table
 */
public interface RetrieveInterface {
    ResultSet getRowsFromDB(Value<?> matchValue) throws SQLException;
}
