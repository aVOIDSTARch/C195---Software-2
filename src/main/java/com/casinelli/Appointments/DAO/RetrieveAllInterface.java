package com.casinelli.Appointments.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for function to Retrieve ALL data from  the table
 */
public interface RetrieveAllInterface {
    ResultSet getAllRecords() throws SQLException;
}
