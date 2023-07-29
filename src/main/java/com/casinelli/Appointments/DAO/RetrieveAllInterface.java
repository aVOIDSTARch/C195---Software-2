package com.casinelli.Appointments.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RetrieveAllInterface {
    ResultSet getAllRecords() throws SQLException;
}
